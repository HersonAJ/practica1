/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.util.Date;

/**
 *
 * @author herson
 */
public class Autorizacion {
    private int idAutorizacion;
    private String numeroTarjeta;
    private int numeroSolicitud;
    private Date fechaAutorizacion;

    public Autorizacion(int numeroSolicitud) {
        
        
        this.numeroSolicitud = numeroSolicitud;
        this.fechaAutorizacion = new Date();
    }


}
