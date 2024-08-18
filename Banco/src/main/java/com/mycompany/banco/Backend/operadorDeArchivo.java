/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;
import com.mycompany.banco.Frontend.EntradaArchivo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JTextArea;

/**
 *
 * @author herson
 */
public class operadorDeArchivo {

    private JTextArea logArea;

    public operadorDeArchivo(JTextArea logArea) {
        this.logArea = logArea;
    }

        public void ejecutarLinea(String linea) {
        if (linea.startsWith("SOLICITUD(")) {
            ejecutarSolicitud(linea);
        } else if (linea.startsWith("MOVIMIENTO(")) {
            ejecutarMovimiento(linea);
        } else if (linea.startsWith("CONSULTAR_TARJETA(")) {
            ejecutarConsultarTarjeta(linea);
        } else if (linea.startsWith("AUTORIZACION_TARJETA(")) {
            ejecutarAutorizacionTarjeta(linea);
        } else if (linea.startsWith("CANCELACION_TARJETA(")) {
            ejecutarCancelacionTarjeta(linea);
        } else if (linea.startsWith("ESTADO_CUENTA(")) {
            ejecutarEstadoCuenta(linea);
        } else if (linea.startsWith("LISTADO_TARJETAS(")) {
            ejecutarListadoTarjetas(linea);
        } else if (linea.startsWith("LISTADO_SOLICITUDES(")) {
            ejecutarListadoSolicitudes(linea);
        } else {
            logArea.append("Línea no válida: " + linea + "\n");
        }
    }
        
        private void ejecutarSolicitud(String linea) {
        try {
            // descomponer la línea para extraer los parámetros de la solicitud
            String[] parts = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')')).split(",");
            int numeroSolicitud = Solicitud.obtenerUltimoNumeroSolicitud() + 1;

            // Limpiar las comillas de la fecha
            String fechaStr = parts[1].replace("\"", "").replace("”", "").replace("“", "");
            LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String tipo = parts[2];
            String nombre = parts[3].replace("\"", "").replace("”", "").replace("“", "");
            double salario = Double.parseDouble(parts[4]);
            String direccion = parts[5].replace("\"", "").replace("”", "").replace("“", "");
            String estado = "PENDIENTE"; // Estado inicial

            // Crear una nueva instancia de Solicitud
            Solicitud solicitud = new Solicitud(numeroSolicitud, fecha, tipo, nombre, salario, direccion, estado);
            solicitud.guardarEnBaseDeDatos();

            logArea.append("Ejecutando SOLICITUD: " + linea + "\n");
        } catch (Exception e) {
            logArea.append("Error al procesar SOLICITUD: " + linea + "\n");
            e.printStackTrace();
        }
    }
        
        private void ejecutarMovimiento(String line) {
        try {
            // descomponer la línea para extraer los parámetros del movimiento
            String[] parts = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).split(",");
            String numeroTarjeta = parts[0].trim(); // Mantener los espacios en el número de tarjeta
            String fechaStr = parts[1].replace("\"", "").replace("”", "").replace("“", "");
            Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
            String tipoMovimiento = parts[2].trim();
            String descripcion = parts[3].replace("\"", "").replace("”", "").replace("“", "").trim();
            String establecimiento = parts[4].replace("\"", "").replace("”", "").replace("“", "").trim();
            double monto = Double.parseDouble(parts[5].trim());

            // Validar la tarjeta antes de crear el movimiento
            if (!tarjetaValida(numeroTarjeta)) {
                logArea.append("Error: La tarjeta no existe o no está activa - " + line + "\n");
                return;
            }

            // Crear una nueva instancia de Movimiento
            Movimiento movimiento = new Movimiento(numeroTarjeta, fecha, tipoMovimiento, descripcion, establecimiento, monto);

            // Guardar el movimiento en la base de datos
            movimiento.guardarEnBaseDeDatos();
            logArea.append("Ejecutando MOVIMIENTO: " + line + "\n");

        } catch (Exception e) {
            logArea.append("Error al procesar MOVIMIENTO: " + line + "\n");
            e.printStackTrace();
        }
    }

    private boolean tarjetaValida(String numeroTarjeta) throws SQLException {
        boolean valida = false;
        String sql = "SELECT estado_tarjeta FROM Tarjeta WHERE numero_tarjeta = '" + numeroTarjeta + "'";
        try (Connection connection = ConexionMySQL.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String estado = rs.getString("estado_tarjeta");
                logArea.append("Estado de la tarjeta: " + estado + "\n");
                valida = "ACTIVA".equals(estado.trim());
            } else {
                logArea.append("Tarjeta no encontrada: " + numeroTarjeta + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return valida;
    }
    private void ejecutarConsultarTarjeta(String line) {
        try {
            // descomponer la línea para extraer el número de tarjeta
            String numeroTarjeta = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).trim();

            // Consultar la base de datos para obtener los datos de la tarjeta
            String sql = "SELECT numero_tarjeta, tipo_tarjeta, limite, nombre_cliente, direccion_cliente, estado_tarjeta FROM Tarjeta WHERE numero_tarjeta = ?";
            try (Connection connection = ConexionMySQL.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, numeroTarjeta);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Generar el archivo HTML con los datos de la tarjeta
                        generarArchivoHTML1(
                            rs.getString("numero_tarjeta"),
                            rs.getString("tipo_tarjeta"),
                            rs.getDouble("limite"),
                            rs.getString("nombre_cliente"),
                            rs.getString("direccion_cliente"),
                            rs.getString("estado_tarjeta")
                        );
                        logArea.append("Ejecutando CONSULTAR_TARJETA: " + line + "\n");
                    } else {
                        logArea.append("Error: Tarjeta no encontrada - " + line + "\n");
                    }
                }
            }
        } catch (Exception e) {
            logArea.append("Error al procesar CONSULTAR_TARJETA: " + line + "\n");
            e.printStackTrace();
        }
    }

    private void generarArchivoHTML1(String numeroTarjeta, String tipoTarjeta, double limite, String nombreCliente, String direccionCliente, String estadoTarjeta) throws IOException {
        String htmlContent = "<html>\n" +
                "<head>\n" +
                "<title>Datos de la Tarjeta</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Datos de la Tarjeta</h1>\n" +
                "<p><strong>No. Tarjeta:</strong> " + numeroTarjeta + "</p>\n" +
                "<p><strong>Tipo Tarjeta:</strong> " + tipoTarjeta + "</p>\n" +
                "<p><strong>Límite:</strong> " + limite + "</p>\n" +
                "<p><strong>Nombre:</strong> " + nombreCliente + "</p>\n" +
                "<p><strong>Dirección:</strong> " + direccionCliente + "</p>\n" +
                "<p><strong>Estado:</strong> " + estadoTarjeta + "</p>\n" +
                "</body>\n" +
                "</html>";

        // Obtener la ruta seleccionada desde la clase EntradaArchivo
        String rutaArchivo = EntradaArchivo.rutaSeleccionada + "/Consulta_tarjeta_" + numeroTarjeta + ".html";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(htmlContent);
        }
    }
    
    private void ejecutarAutorizacionTarjeta(String line) {
        try {
            // descomponer la línea para extraer el número de solicitud
            int numeroSolicitud = Integer.parseInt(line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).trim());

            // Crear una instancia de Autorizacion y autorizar la tarjeta
            Autorizacion autorizacion = new Autorizacion(numeroSolicitud);
            try {
                Tarjeta tarjeta = autorizacion.autorizar();
                logArea.append("Autorización exitosa para la solicitud: " + numeroSolicitud + "\n");
                logArea.append("Tarjeta generada: " + tarjeta.getNumeroTarjeta() + "\n");
            } catch (IllegalArgumentException e) {
                logArea.append("Error en la autorización: " + e.getMessage() + " - Solicitud: " + numeroSolicitud + "\n");
            }

        } catch (Exception e) {
            logArea.append("Error al procesar AUTORIZACION_TARJETA: " + line + "\n");
            e.printStackTrace();
        }
    }
    
    private void ejecutarCancelacionTarjeta(String line) {
        try {
            // descomponer la línea para extraer el número de tarjeta
            String numeroTarjeta = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).trim();

            // Crear una instancia de Cancelacion y cancelar la tarjeta
            Cancelacion cancelacion = new Cancelacion(numeroTarjeta);
            try {
                cancelacion.cancelar();
                logArea.append("Cancelación exitosa para la tarjeta: " + numeroTarjeta + "\n");
            } catch (IllegalArgumentException e) {
                logArea.append("Error en la cancelación: " + e.getMessage() + " - Tarjeta: " + numeroTarjeta + "\n");
            }

        } catch (Exception e) {
            logArea.append("Error al procesar CANCELACION_TARJETA: " + line + "\n");
            e.printStackTrace();
        }
    }
    private void ejecutarEstadoCuenta(String line) {
        try {
            // descomponer la línea para extraer los filtros
            String[] parts = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).split(",");
            String numeroTarjeta = parts.length > 0 ? parts[0].trim() : "";
            String tipoTarjeta = parts.length > 1 ? parts[1].trim() : "";
            double saldoMayorA = parts.length > 2 && !parts[2].trim().isEmpty() ? Double.parseDouble(parts[2].trim()) : -1;
            double interesesMayorA = parts.length > 3 && !parts[3].trim().isEmpty() ? Double.parseDouble(parts[3].trim()) : -1;

            // Obtener los estados de cuenta aplicando los filtros
            List<EstadoDeCuenta> estadosDeCuenta = obtenerEstadosDeCuentaFiltrados(numeroTarjeta, tipoTarjeta, saldoMayorA, interesesMayorA);



            // Generar el archivo HTML con los estados de cuenta
            generarArchivoHTML2(estadosDeCuenta, numeroTarjeta, tipoTarjeta, saldoMayorA, interesesMayorA);

            logArea.append("Ejecutando ESTADO_CUENTA: " + line + "\n");
        } catch (Exception e) {
            logArea.append("Error al procesar ESTADO_CUENTA: " + line + "\n");
            e.printStackTrace();
        }
    }
    private List<EstadoDeCuenta> obtenerEstadosDeCuentaFiltrados(String numeroTarjeta, String tipoTarjeta, double saldoMayorA, double interesesMayorA) throws SQLException {
        List<EstadoDeCuenta> resultados = EstadoDeCuenta.obtenerTodasLasTarjetasActivas();

        if (!numeroTarjeta.isEmpty()) {
            resultados = resultados.stream()
                .filter(estado -> estado.getNumeroTarjeta().contains(numeroTarjeta))
                .collect(Collectors.toList());
        }

        if (!tipoTarjeta.isEmpty()) {
            resultados = resultados.stream()
                .filter(estado -> estado.getTipoTarjeta().equals(tipoTarjeta))
                .collect(Collectors.toList());
        }

        if (saldoMayorA > -1) {
            resultados = resultados.stream()
                .filter(estado -> estado.getSaldoTotal() > saldoMayorA)
                .collect(Collectors.toList());
        }

        if (interesesMayorA > -1) {
            resultados = resultados.stream()
                .filter(estado -> estado.getIntereses() > interesesMayorA)
                .collect(Collectors.toList());
        }

        return resultados;
    }

    private void generarArchivoHTML2(List<EstadoDeCuenta> estadosDeCuenta, String numeroTarjeta, String tipoTarjeta, double saldoMayorA, double interesesMayorA) throws IOException {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html>\n<head>\n<title>Estado de Cuenta</title>\n<style>\n")
                   .append("table { width: 100%; border-collapse: collapse; }\n")
                   .append("th, td { border: 1px solid black; padding: 8px; text-align: left; }\n")
                   .append("th { background-color: #f2f2f2; }\n")
                   .append("</style>\n</head>\n<body>\n");

        for (EstadoDeCuenta estado : estadosDeCuenta) {
            htmlContent.append("<h1>Estado de Cuenta</h1>\n")
                       .append("<p><strong>Numero Tarjeta:</strong> ").append(estado.getNumeroTarjeta()).append("</p>\n")
                       .append("<p><strong>Tipo Tarjeta:</strong> ").append(estado.getTipoTarjeta()).append("</p>\n")
                       .append("<p><strong>Nombre Cliente:</strong> ").append(estado.getNombreCliente()).append("</p>\n")
                       .append("<p><strong>Direccion Cliente:</strong> ").append(estado.getDireccionCliente()).append("</p>\n")
                       .append("<table>\n<tr>\n<th>Fecha</th>\n<th>Tipo de Movimiento</th>\n<th>Descripcion</th>\n<th>Establecimiento</th>\n<th>Monto</th>\n</tr>\n");

            for (Movimiento movimiento : estado.getMovimientos()) {
                htmlContent.append("<tr>\n")
                           .append("<td>").append(movimiento.getFecha()).append("</td>\n")
                           .append("<td>").append(movimiento.getTipoMovimiento()).append("</td>\n")
                           .append("<td>").append(movimiento.getDescripcion()).append("</td>\n")
                           .append("<td>").append(movimiento.getEstablecimiento()).append("</td>\n")
                           .append("<td>").append(movimiento.getMonto()).append("</td>\n")
                           .append("</tr>\n");
            }

            htmlContent.append("</table>\n")
                       .append("<p><strong>Monto Total:</strong> ").append(estado.getMontoTotal()).append("</p>\n")
                       .append("<p><strong>Intereses:</strong> ").append(estado.getIntereses()).append("</p>\n")
                       .append("<p><strong>Saldo Total:</strong> ").append(estado.getSaldoTotal()).append("</p>\n");
        }

        htmlContent.append("</body>\n</html>");

        // Generar el nombre del archivo basado en los filtros
        String nombreArchivo = "estado_cuenta";
        if (!numeroTarjeta.isEmpty()) {
            nombreArchivo += "_tarjeta_" + numeroTarjeta;
        }
        if (!tipoTarjeta.isEmpty()) {
            nombreArchivo += "_tipo_" + tipoTarjeta;
        }
        if (saldoMayorA > -1) {
            nombreArchivo += "_saldo_mayor_a_" + saldoMayorA;
        }
        if (interesesMayorA > -1) {
            nombreArchivo += "_intereses_mayor_a_" + interesesMayorA;
        }
        nombreArchivo += ".html";

        // Obtener la ruta seleccionada desde la clase EntradaArchivo
        String rutaArchivo = EntradaArchivo.rutaSeleccionada + "/" + nombreArchivo;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(htmlContent.toString());
        }
    }
    
    private void ejecutarListadoTarjetas(String line) {
        try {
            // descomponer la línea para extraer los filtros
            String[] parts = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).split(",");
            String tipo = parts.length > 0 ? parts[0].trim() : "";
            String limiteStr = parts.length > 1 ? parts[1].trim() : "";
            String fechaInicialStr = parts.length > 2 ? parts[2].trim() : "";
            String fechaFinalStr = parts.length > 3 ? parts[3].trim() : "";
            String estado = parts.length > 4 ? parts[4].trim() : "";

            // Construir la consulta SQL con los filtros
            StringBuilder query = new StringBuilder("SELECT * FROM Tarjeta WHERE 1=1");
            if (!tipo.isEmpty()) query.append(" AND tipo_tarjeta = ?");
            if (!limiteStr.isEmpty()) query.append(" AND limite > ?");
            if (!fechaInicialStr.isEmpty()) query.append(" AND fecha_ultimo_estado >= ?");
            if (!fechaFinalStr.isEmpty()) query.append(" AND fecha_ultimo_estado <= ?");
            if (!estado.isEmpty()) query.append(" AND estado_tarjeta = ?");

            // Obtener la conexión y preparar la consulta
            Connection connection = ConexionMySQL.getConnection();
            try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
                int index = 1;
                if (!tipo.isEmpty()) stmt.setString(index++, tipo);
                if (!limiteStr.isEmpty()) stmt.setBigDecimal(index++, new BigDecimal(limiteStr));
                if (!fechaInicialStr.isEmpty()) stmt.setDate(index++, new java.sql.Date(parseDate(fechaInicialStr).getTime()));
                if (!fechaFinalStr.isEmpty()) stmt.setDate(index++, new java.sql.Date(parseDate(fechaFinalStr).getTime()));
                if (!estado.isEmpty()) stmt.setString(index++, estado);

                ResultSet rs = stmt.executeQuery();
                StringBuilder html = new StringBuilder();
                html.append("<html><head><title>Listado de Tarjetas</title><style>")
                    .append("table { width: 100%; border-collapse: collapse; }")
                    .append("th, td { border: 1px solid black; padding: 8px; text-align: left; }")
                    .append("th { background-color: #f2f2f2; }")
                    .append("</style></head><body><h1>Listado de Tarjetas</h1><table>")
                    .append("<tr><th>Numero Tarjeta</th><th>Tipo</th><th>Limite</th><th>Nombre</th><th>Direccion</th><th>Fecha</th><th>Estado</th></tr>");

                while (rs.next()) {
                    html.append("<tr>")
                        .append("<td>").append(rs.getString("numero_tarjeta")).append("</td>")
                        .append("<td>").append(rs.getString("tipo_tarjeta")).append("</td>")
                        .append("<td>").append(rs.getBigDecimal("limite")).append("</td>")
                        .append("<td>").append(rs.getString("nombre_cliente")).append("</td>")
                        .append("<td>").append(rs.getString("direccion_cliente")).append("</td>")
                        .append("<td>").append(rs.getDate("fecha_ultimo_estado")).append("</td>")
                        .append("<td>").append(rs.getString("estado_tarjeta")).append("</td>")
                        .append("</tr>");
                }

                html.append("</table></body></html>");
                generarArchivoHTML3(html.toString(), tipo, limiteStr, fechaInicialStr, fechaFinalStr, estado);

            } catch (SQLException e) {
                logArea.append("Error al ejecutar la consulta: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        } catch (Exception e) {
            logArea.append("Error al procesar LISTADO_TARJETAS: " + line + "\n");
            e.printStackTrace();
        }
    }

    private Date parseDate(String dateStr) throws ParseException {
        // Eliminar comillas especiales
        dateStr = dateStr.replace("“", "").replace("”", "").replace("\"", "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(dateStr);
    }

    private void generarArchivoHTML3(String htmlContent, String tipo, String limiteStr, String fechaInicialStr, String fechaFinalStr, String estado) {
        try {
            // Limpiar comillas especiales de las fechas
            fechaInicialStr = fechaInicialStr.replace("“", "").replace("”", "").replace("\"", "").replace("/", "_");
            fechaFinalStr = fechaFinalStr.replace("“", "").replace("”", "").replace("\"", "").replace("/", "_");

            String nombreArchivo = "listado_tarjetas";
            if (!tipo.isEmpty()) nombreArchivo += "_tipo_" + tipo;
            if (!limiteStr.isEmpty()) nombreArchivo += "_limite_mayor_a_" + limiteStr;
            if (!fechaInicialStr.isEmpty()) nombreArchivo += "_fecha_inicial_" + fechaInicialStr;
            if (!fechaFinalStr.isEmpty()) nombreArchivo += "_fecha_final_" + fechaFinalStr;
            if (!estado.isEmpty()) nombreArchivo += "_estado_" + estado;
            nombreArchivo += ".html";

            String rutaArchivo = EntradaArchivo.rutaSeleccionada + "/" + nombreArchivo;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
                writer.write(htmlContent);
                logArea.append("Reporte generado: " + rutaArchivo + "\n");
            }
        } catch (IOException e) {
            logArea.append("Error al escribir el archivo HTML: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    
    private void ejecutarListadoSolicitudes(String line) {
        try {
            // descomponer la línea para extraer los filtros
            String[] parts = line.substring(line.indexOf('(') + 1, line.lastIndexOf(')')).split(",");
            String fechaInicioStr = parts.length > 0 ? parts[0].trim() : "";
            String fechaFinStr = parts.length > 1 ? parts[1].trim() : "";
            String tipo = parts.length > 2 ? parts[2].trim() : "";
            String montoStr = parts.length > 3 ? parts[3].trim() : "";
            String estado = parts.length > 4 ? parts[4].trim() : "";

            // Construir la consulta SQL con los filtros
            StringBuilder query = new StringBuilder("SELECT * FROM Solicitud WHERE 1=1");
            if (!fechaInicioStr.isEmpty()) query.append(" AND fecha >= ?");
            if (!fechaFinStr.isEmpty()) query.append(" AND fecha <= ?");
            if (!tipo.isEmpty()) query.append(" AND tipo = ?");
            if (!montoStr.isEmpty()) query.append(" AND salario > ?");
            if (!estado.isEmpty()) query.append(" AND estado = ?");

            // Obtener la conexión y preparar la consulta
            Connection connection = ConexionMySQL.getConnection();
            try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
                int index = 1;
                if (!fechaInicioStr.isEmpty()) stmt.setDate(index++, new java.sql.Date(parseDate(fechaInicioStr).getTime()));
                if (!fechaFinStr.isEmpty()) stmt.setDate(index++, new java.sql.Date(parseDate(fechaFinStr).getTime()));
                if (!tipo.isEmpty()) stmt.setString(index++, tipo);
                if (!montoStr.isEmpty()) stmt.setBigDecimal(index++, new BigDecimal(montoStr));
                if (!estado.isEmpty()) stmt.setString(index++, estado);

                ResultSet rs = stmt.executeQuery();
                StringBuilder html = new StringBuilder();
                html.append("<html><head><title>Listado de Solicitudes</title><style>")
                    .append("table { width: 100%; border-collapse: collapse; }")
                    .append("th, td { border: 1px solid black; padding: 8px; text-align: left; }")
                    .append("th { background-color: #f2f2f2; }")
                    .append("</style></head><body><h1>Listado de Solicitudes</h1><table>")
                    .append("<tr><th>Numero Solicitud</th><th>Fecha</th><th>Tipo</th><th>Nombre</th><th>Salario</th><th>Direccion</th><th>Estado</th><th>Fecha Estado</th><th>Motivo Rechazo</th></tr>");

                while (rs.next()) {
                    html.append("<tr>")
                        .append("<td>").append(rs.getInt("numero_solicitud")).append("</td>")
                        .append("<td>").append(rs.getDate("fecha")).append("</td>")
                        .append("<td>").append(rs.getString("tipo")).append("</td>")
                        .append("<td>").append(rs.getString("nombre")).append("</td>")
                        .append("<td>").append(rs.getBigDecimal("salario")).append("</td>")
                        .append("<td>").append(rs.getString("direccion")).append("</td>")
                        .append("<td>").append(rs.getString("estado")).append("</td>")
                        .append("<td>").append(rs.getDate("fecha_estado")).append("</td>")
                        .append("<td>").append(rs.getString("motivo_rechazo")).append("</td>")
                        .append("</tr>");
                }

                html.append("</table></body></html>");
                generarArchivoHTML4(html.toString(), fechaInicioStr, fechaFinStr, tipo, montoStr, estado);

            } catch (SQLException e) {
                logArea.append("Error al ejecutar la consulta: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        } catch (Exception e) {
            logArea.append("Error al procesar LISTADO_SOLICITUDES: " + line + "\n");
            e.printStackTrace();
        }
    }

    private void generarArchivoHTML4(String htmlContent, String fechaInicioStr, String fechaFinStr, String tipo, String montoStr, String estado) {
        try {
            // Limpiar comillas especiales de las fechas
            fechaInicioStr = fechaInicioStr.replace("“", "").replace("”", "").replace("\"", "").replace("/", "_");
            fechaFinStr = fechaFinStr.replace("“", "").replace("”", "").replace("\"", "").replace("/", "_");

            String nombreArchivo = "listado_solicitudes";
            if (!fechaInicioStr.isEmpty()) nombreArchivo += "_fecha_inicio_" + fechaInicioStr;
            if (!fechaFinStr.isEmpty()) nombreArchivo += "_fecha_fin_" + fechaFinStr;
            if (!tipo.isEmpty()) nombreArchivo += "_tipo_" + tipo;
            if (!montoStr.isEmpty()) nombreArchivo += "_salario_mayor_a_" + montoStr;
            if (!estado.isEmpty()) nombreArchivo += "_estado_" + estado;
            nombreArchivo += ".html";

            String rutaArchivo = EntradaArchivo.rutaSeleccionada + "/" + nombreArchivo;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
                writer.write(htmlContent);
                logArea.append("Reporte generado: " + rutaArchivo + "\n");
            }
        } catch (IOException e) {
            logArea.append("Error al escribir el archivo HTML: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
}
