/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;

/**
 *
 * @author herson
 */
public class operadorSolicitud {
    
    private JTextArea logArea;

    public operadorSolicitud(JTextArea logArea) {
        this.logArea = logArea;
    }
    
    public void ejecutarSolicitud(String linea) {
        try {
            // descomponer la línea para extraer los parámetros de la solicitud
            String[] parts = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')')).split(",");
            int numeroSolicitud = Solicitud.obtenerUltimoNumeroSolicitud() + 1;

            // Limpiar las comillas de la fecha
            String fechaStr = parts[1].replace("\"", "").replace("”", "").replace("“", "");
            LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String tipo = parts[2];
            String nombre = parts[3].replace("\"", "").replace("”", "").replace("“", "");
            double salario = Double.parseDouble(parts[4]);
            String direccion = parts[5].replace("\"", "").replace("”", "").replace("“", "");
            String estado = "PENDIENTE"; // Estado inicial

            // Crear una nueva instancia de Solicitud
            Solicitud solicitud = new Solicitud(numeroSolicitud, fecha, tipo, nombre, salario, direccion, estado);
            solicitud.guardarEnBaseDeDatos();

            logArea.append("Ejecutando SOLICITUD: " + linea + "\n");
        } catch (Exception e) {
            logArea.append("Error al procesar SOLICITUD: " + linea + "\n");
            e.printStackTrace();
        }
    }
    
}
