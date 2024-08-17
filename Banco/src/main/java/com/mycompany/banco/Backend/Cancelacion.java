/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author herson
 */
public class Cancelacion {
    private String numeroTarjeta;

    public Cancelacion(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public void cancelar() throws SQLException {
        try (Connection connection = ConexionMySQL.getConnection()) {
            // Verificar si la tarjeta está activa
            if (!tarjetaActiva(connection)) {
                throw new IllegalArgumentException("La tarjeta ya está cancelada o no existe.");
            }

            // Verificar si la tarjeta tiene saldo pendiente
            EstadoDeCuenta estadoDeCuenta = new EstadoDeCuenta(numeroTarjeta);
            if (estadoDeCuenta.getSaldoTotal() > 0) {
                throw new IllegalArgumentException("No se puede cancelar la tarjeta hasta que no tenga saldos pendientes.");
            }

            // Cancelar la tarjeta
            actualizarEstadoTarjeta(connection);
            guardarCancelacionEnBaseDeDatos(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private boolean tarjetaActiva(Connection connection) throws SQLException {
        boolean activa = false;
        String sql = "SELECT estado_tarjeta FROM Tarjeta WHERE numero_tarjeta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, numeroTarjeta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    activa = "ACTIVA".equals(rs.getString("estado_tarjeta"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return activa;
    }

    private void actualizarEstadoTarjeta(Connection connection) throws SQLException {
        String sql = "UPDATE Tarjeta SET estado_tarjeta = 'CANCELADA' WHERE numero_tarjeta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, numeroTarjeta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void guardarCancelacionEnBaseDeDatos(Connection connection) throws SQLException {
        String sql = "INSERT INTO Cancelacion (numero_tarjeta, fecha_cancelacion, motivo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, numeroTarjeta);
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setString(3, "Cancelación solicitada por el cliente");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}