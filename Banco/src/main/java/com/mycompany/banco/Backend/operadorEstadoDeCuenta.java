/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import com.mycompany.banco.Frontend.EntradaArchivo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JTextArea;

/**
 *
 * @author herson
 */
public class operadorEstadoDeCuenta {
    
    private JTextArea logArea;

    public operadorEstadoDeCuenta(JTextArea logArea) {
        this.logArea = logArea;
        
    }
    
    
    public void ejecutarEstadoCuenta(String linea) {
            try {
                // descomponer la línea para extraer los filtros
                String[] parts = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')')).split(",");
                String numeroTarjeta = parts.length > 0 ? parts[0].trim() : "";
                String tipoTarjeta = parts.length > 1 ? parts[1].trim() : "";
                double saldoMayorA = parts.length > 2 && !parts[2].trim().isEmpty() ? Double.parseDouble(parts[2].trim()) : -1;
                double interesesMayorA = parts.length > 3 && !parts[3].trim().isEmpty() ? Double.parseDouble(parts[3].trim()) : -1;

                // Obtener los estados de cuenta aplicando los filtros
                List<EstadoDeCuenta> estadosDeCuenta = obtenerEstadosDeCuentaFiltrados(numeroTarjeta, tipoTarjeta, saldoMayorA, interesesMayorA);



                // Generar el archivo HTML con los estados de cuenta
                generarArchivoHTML2(estadosDeCuenta, numeroTarjeta, tipoTarjeta, saldoMayorA, interesesMayorA);

                logArea.append("Ejecutando ESTADO_CUENTA: " + linea + "\n");
            } catch (Exception e) {
                logArea.append("Error al procesar ESTADO_CUENTA: " + linea + "\n");
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

        // Obtener la ruta seleccionada desde la clase CargarArchivoManual
        String rutaArchivo = EntradaArchivo.rutaSeleccionada + "/" + nombreArchivo;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(htmlContent.toString());
        }
    }
    
}
