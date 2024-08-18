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
import java.util.Date;
import javax.swing.JTextArea;

/**
 *
 * @author herson
 */
public class operadorMovimiento {
    
    private JTextArea logArea;

    public operadorMovimiento(JTextArea logArea) {
        this.logArea = logArea;
    }

    public void ejecutarMovimiento(String linea) {
    try {
        // descomponer la línea para extraer los parámetros del movimiento
        String[] parts = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')')).split(",");
        String numeroTarjeta = parts[0].trim(); // Mantener los espacios en el número de tarjeta
        String fechaStr = parts[1].replace("\"", "").replace("”", "").replace("“", "");
        Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
        String tipoMovimiento = parts[2].trim();
        String descripcion = parts[3].replace("\"", "").replace("”", "").replace("“", "").trim();
        String establecimiento = parts[4].replace("\"", "").replace("”", "").replace("“", "").trim();
        double monto = Double.parseDouble(parts[5].trim());

        // Validar la tarjeta antes de crear el movimiento
        if (!tarjetaValida(numeroTarjeta)) {
            logArea.append("Error: La tarjeta no existe o no está activa - " + linea + "\n");
            return;
        }

        // Crear una nueva instancia de Movimiento
        Movimiento movimiento = new Movimiento(numeroTarjeta, fecha, tipoMovimiento, descripcion, establecimiento, monto);
        
        // Guardar el movimiento en la base de datos
        movimiento.guardarEnBaseDeDatos();
        logArea.append("Ejecutando MOVIMIENTO: " + linea + "\n");

    } catch (Exception e) {
        logArea.append("Error al procesar MOVIMIENTO: " + linea + "\n");
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
