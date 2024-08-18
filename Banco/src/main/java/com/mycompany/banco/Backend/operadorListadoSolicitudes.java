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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JTextArea;

/**
 *
 * @author herson
 */
public class operadorListadoSolicitudes {

    private JTextArea logArea;

    public operadorListadoSolicitudes(JTextArea logArea) {
        this.logArea = logArea;
    }

    public void ejecutarListadoSolicitudes(String linea) {
        try {
            // descomponer la línea para extraer los filtros
            String[] parts = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')')).split(",");
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
            logArea.append("Error al procesar LISTADO_SOLICITUDES: " + linea + "\n");
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

    private java.util.Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(dateStr);
    }
}

