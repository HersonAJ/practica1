/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author herson
 */
public class Tarjeta {
    private String numeroTarjeta;
    private TipoTarjeta tipoTarjeta;
    private double limite;
    private String nombreCliente;
    private String direccionCliente;
    private String estadoTarjeta;
    private int numeroSolicitud;

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public double getLimite() {
        return limite;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public String getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public Tarjeta(String numeroTarjeta, TipoTarjeta tipoTarjeta, double limite, String nombreCliente, String direccionCliente, String estadoTarjeta, int numeroSolicitud) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.limite = limite;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.estadoTarjeta = estadoTarjeta;
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void guardarEnBaseDeDatos(Connection connection) throws SQLException {
        String sql = "INSERT INTO Tarjeta (numero_tarjeta, tipo_tarjeta, limite, nombre_cliente, direccion_cliente, estado_tarjeta, numero_solicitud) VALUES ('" 
                     + numeroTarjeta + "', '" 
                     + tipoTarjeta.name() + "', " 
                     + limite + ", '" 
                     + nombreCliente + "', '" 
                     + direccionCliente + "', '" 
                     + estadoTarjeta + "', " 
                     + numeroSolicitud + ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}

