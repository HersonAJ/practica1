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

}
