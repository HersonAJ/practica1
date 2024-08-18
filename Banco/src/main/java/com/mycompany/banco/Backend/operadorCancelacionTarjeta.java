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
public class operadorCancelacionTarjeta {
    
        private JTextArea logArea;

    public operadorCancelacionTarjeta(JTextArea logArea) {
        this.logArea = logArea;
    }
        
    public void ejecutarCancelacionTarjeta(String linea) {
    try {
        // descomponer la línea para extraer el número de tarjeta
        String numeroTarjeta = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')')).trim();

        // Crear una instancia de Cancelacion y cancelar la tarjeta
        Cancelacion cancelacion = new Cancelacion(numeroTarjeta);
        try {
            cancelacion.cancelar();
            logArea.append("Cancelación exitosa para la tarjeta: " + numeroTarjeta + "\n");
        } catch (IllegalArgumentException e) {
            logArea.append("Error en la cancelación: " + e.getMessage() + " - Tarjeta: " + numeroTarjeta + "\n");
        }

    } catch (Exception e) {
        logArea.append("Error al procesar CANCELACION_TARJETA: " + linea + "\n");
        e.printStackTrace();
    }
}    
    
}
