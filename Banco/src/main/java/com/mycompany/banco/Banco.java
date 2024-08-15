/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.banco;

import com.mycompany.banco.Backend.ConexionMySQL;
import com.mycompany.banco.Frontend.InterfazGrafica;

/**
 *
 * @author herson
 */
public class Banco {

    public static void main(String[] args) {
        ConexionMySQL coneccion = new ConexionMySQL();
        InterfazGrafica ventana = new InterfazGrafica();
    }
}
