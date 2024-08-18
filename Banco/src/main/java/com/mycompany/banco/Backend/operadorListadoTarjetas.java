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
import java.util.Date;
import javax.swing.JTextArea;

/**
 *
 * @author herson
 */
public class operadorListadoTarjetas {
        private JTextArea logArea;
    
        public operadorListadoTarjetas(JTextArea logArea) {
        this.logArea = logArea;
    
        }
    
    
    public void ejecutarListadoTarjetas(String linea) {
    try {
        // descomponer la línea para extraer los filtros
        String[] parts = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')')).split(",");
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
        logArea.append("Error al procesar LISTADO_TARJETAS: " + linea + "\n");
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
    
}
