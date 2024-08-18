/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;
import com.mycompany.banco.Frontend.EntradaArchivo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JTextArea;

/**
 *
 * @author herson
 */
public class operadorDeArchivo {

    private JTextArea logArea;

    public operadorDeArchivo(JTextArea logArea) {
        this.logArea = logArea;
    }

    public void ejecutarLinea(String linea) {
        if (linea.startsWith("SOLICITUD(")) {
            operadorSolicitud ejecutor = new operadorSolicitud(logArea);
            ejecutor.ejecutarSolicitud(linea);
        } else if (linea.startsWith("MOVIMIENTO(")) {
            operadorMovimiento ejecutor = new operadorMovimiento(logArea);
            ejecutor.ejecutarMovimiento(linea);
        } else if (linea.startsWith("CONSULTAR_TARJETA(")) {
            operadorConsultarTarjeta ejecutor = new operadorConsultarTarjeta(logArea);
            ejecutor.ejecutarConsultarTarjeta(linea);
        } else if (linea.startsWith("AUTORIZACION_TARJETA(")) {
            operadorAutorizacionTarjeta ejecutor = new operadorAutorizacionTarjeta(logArea);
            ejecutor.ejecutarAutorizacionTarjeta(linea);
        } else if (linea.startsWith("CANCELACION_TARJETA(")) {
            operadorCancelacionTarjeta ejecutor = new operadorCancelacionTarjeta(logArea);
            ejecutor.ejecutarCancelacionTarjeta(linea);
        } else if (linea.startsWith("ESTADO_CUENTA(")) {
            operadorEstadoDeCuenta ejecutor = new operadorEstadoDeCuenta(logArea);
            ejecutor.ejecutarEstadoCuenta(linea);
        } else if (linea.startsWith("LISTADO_TARJETAS(")) {
            operadorListadoTarjetas ejecutor = new operadorListadoTarjetas(logArea);
            ejecutor.ejecutarListadoTarjetas(linea);
        } else if (linea.startsWith("LISTADO_SOLICITUDES(")) {
            operadorListadoSolicitudes ejecutor = new operadorListadoSolicitudes(logArea);
            ejecutor.ejecutarListadoSolicitudes(linea);
        } else {
            logArea.append("Línea no válida: " + linea + "\n");
        }
    }
        
}
