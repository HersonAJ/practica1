/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;


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
}

