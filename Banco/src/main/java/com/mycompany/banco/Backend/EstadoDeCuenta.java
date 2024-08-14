/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

/**
 *
 * @author herson
 */
public class EstadoDeCuenta {
    private String numeroTarjeta;
    private String tipoTarjeta;
    private String nombreCliente;
    private String direccionCliente;
    private List<Movimiento> movimientos;
    private double montoTotal;
    private double intereses;
    private double saldoTotal;

public EstadoDeCuenta(String numeroTarjeta)  {
    this.numeroTarjeta = numeroTarjeta;

}
}