/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
            //metodos para consulta por medio del archivo
        } else if (linea.startsWith("AUTORIZACION_TARJETA(")) {
            //metodos para autorizacion por medio del archivo
        } else if (linea.startsWith("CANCELACION_TARJETA(")) {
            //metodos para cancelacion por medio del archivo
        } else if (linea.startsWith("ESTADO_CUENTA(")) {
            //metodos para estado de cuenta por medio del archivo
        } else if (linea.startsWith("LISTADO_TARJETAS(")) {
            //metodos para listado de tarjeta por medio del archivo
        } else if (linea.startsWith("LISTADO_SOLICITUDES(")) {
            //metodos para listado de solicitud por medio del archivo
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
}
