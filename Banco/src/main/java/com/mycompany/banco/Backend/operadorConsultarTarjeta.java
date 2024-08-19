/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import com.mycompany.banco.Frontend.EntradaArchivo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTextArea;
/**
 *
 * @author herson
 */
public class operadorConsultarTarjeta {
    
    private JTextArea logArea;

    public operadorConsultarTarjeta(JTextArea logArea) {
        this.logArea = logArea;
    }
    
    public void ejecutarConsultarTarjeta(String linea) {
        try {
            // descomponer la línea para extraer el número de tarjeta
            String numeroTarjeta = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')')).trim();

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
                        logArea.append("Ejecutando CONSULTAR_TARJETA: " + linea + "\n");
                    } else {
                        logArea.append("Error: Tarjeta no encontrada - " + linea + "\n");
                    }
                }
            }
        } catch (Exception e) {
            logArea.append("Error al procesar CONSULTAR_TARJETA: " + linea + "\n");
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
    
}
