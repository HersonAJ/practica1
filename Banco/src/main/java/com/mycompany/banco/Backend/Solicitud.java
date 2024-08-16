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
import java.util.Date;

/**
 *
 * @author herson
 */
public class Solicitud {
    
    protected int numeroSolicitud;
    protected LocalDate fecha;
    protected String tipo;
    protected String nombre;
    protected Double salario;
    protected String direccion;
    protected String estado; 

    public Solicitud(int numeroSolicitud, LocalDate fecha, String tipo, String nombre, double salario, String direccion, String estado) {
        this.numeroSolicitud = numeroSolicitud;
        this.fecha = fecha;
        this.tipo = tipo;
        this.nombre = nombre;
        this.salario = salario;
        this.direccion = direccion;
        this.estado = estado; 
    }

    // Getters y Setters
    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getSalario() {
        return salario;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEstado() {
        return estado; 
    }

    public void setNumeroSolicitud(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEstado(String estado) {
        this.estado = estado; 
    }

    @Override
    public String toString() {
        return "SOLICITUD(" + numeroSolicitud + ",\"" + fecha + "\"," + tipo + ",\"" + nombre + "\"," + salario + ",\"" + direccion + "\",\"" + estado + "\");";
    }

    public void guardarEnBaseDeDatos() throws SQLException {
        String sql = "INSERT INTO Solicitud (fecha, tipo, nombre, salario, direccion, estado) VALUES ('" + fecha + "', '" + tipo + "', '" + nombre + "', " + salario + ", '" + direccion + "', 'PENDIENTE')";
        try (Connection connection = ConexionMySQL.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Solicitud guardada en la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static int obtenerUltimoNumeroSolicitud() throws SQLException {
        int ultimoNumeroSolicitud = 0;
        String sql = "SELECT MAX(numero_solicitud) FROM Solicitud";
        try (Connection connection = ConexionMySQL.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                ultimoNumeroSolicitud = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return ultimoNumeroSolicitud;
    }


}