/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import javax.swing.JTextArea;

/**
 *
 * @author herson
 */
public class operadorAutorizacionTarjeta {
    
    private JTextArea logArea;

    public operadorAutorizacionTarjeta(JTextArea logArea) {
        this.logArea = logArea;
    }
    
    public void ejecutarAutorizacionTarjeta(String linea) {
    try {
        // descomponer la línea para extraer el número de solicitud
        int numeroSolicitud = Integer.parseInt(linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')')).trim());

        // Crear una instancia de Autorizacion y autorizar la tarjeta
        Autorizacion autorizacion = new Autorizacion(numeroSolicitud);
        try {
            Tarjeta tarjeta = autorizacion.autorizar();
            logArea.append("Autorización exitosa para la solicitud: " + numeroSolicitud + "\n");
            logArea.append("Tarjeta generada: " + tarjeta.getNumeroTarjeta() + "\n");
        } catch (IllegalArgumentException e) {
            logArea.append("Error en la autorización: " + e.getMessage() + " - Solicitud: " + numeroSolicitud + "\n");
        }

    } catch (Exception e) {
        logArea.append("Error al procesar AUTORIZACION_TARJETA: " + linea + "\n");
        e.printStackTrace();
    }
}
    
}
