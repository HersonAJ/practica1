/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author herson
 */
public class Movimiento {
    private int idMovimiento;
    private String numeroTarjeta;
    private Date fecha;
    private String tipoMovimiento;
    private String descripcion;
    private String establecimiento;
    private double monto;

    public Movimiento(String numeroTarjeta, Date fecha, String tipoMovimiento, String descripcion, String establecimiento, double monto) {
        this.numeroTarjeta = numeroTarjeta;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.descripcion = descripcion;
        this.establecimiento = establecimiento;
        this.monto = monto;
    }

    public void guardarEnBaseDeDatos() throws SQLException {
        if (!tarjetaValida()) {
            throw new IllegalArgumentException("La tarjeta no existe o no está activa");
        }

        String sql = "INSERT INTO Movimiento (numero_tarjeta, fecha, tipo_movimiento, descripcion, establecimiento, monto) VALUES ('" 
                     + numeroTarjeta + "', '" 
                     + new java.sql.Date(fecha.getTime()) + "', '" 
                     + tipoMovimiento + "', '" 
                     + descripcion + "', '" 
                     + establecimiento + "', " 
                     + monto + ")";
        try (Connection connection = ConexionMySQL.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Movimiento guardado en la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private boolean tarjetaValida() throws SQLException {
        boolean valida = false;
        String sql = "SELECT estado_tarjeta FROM Tarjeta WHERE numero_tarjeta = '" + numeroTarjeta + "'";
        try (Connection connection = ConexionMySQL.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String estado = rs.getString("estado_tarjeta");
                valida = "ACTIVA".equals(estado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return valida;
    }

    public double getMonto() {
        return monto;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }
    
}
