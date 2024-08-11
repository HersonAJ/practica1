/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author herson
 */
public class Solicitud {
    
    protected int numeroSolicitud;
    protected Date fecha;
    protected String tipo;
    protected String nombre;
    protected Double salario;
    protected String direccion;
    
    public Solicitud(int numeroSolicitud, Date fecha, String tipo, String nombre, double salario, String direccion){
        this.numeroSolicitud = numeroSolicitud;
        this.fecha = fecha;
        this.tipo = tipo;
        this.nombre = nombre;
        this.salario = salario;
        this.direccion = direccion;
    }

    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public Date getFecha() {
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

    public void setNumeroSolicitud(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public void setFecha(Date fecha) {
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
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "SOLICITUD(" + numeroSolicitud + ",\"" + sdf.format(fecha) + "\"," + tipo + ",\"" + nombre + "\"," + salario + ",\"" + direccion + "\");";
    } 
}
