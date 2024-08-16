/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author herson
 */
public class GeneradorNumeroTarjeta {

    private int contadorNacional;
    private int contadorRegional;
    private int contadorInternacional;

    // Constructor que inicializa los contadores desde la base de datos
    public GeneradorNumeroTarjeta(ConexionMySQL conexion) throws SQLException {
        inicializarContadores(conexion);
    }

    // Método para inicializar los contadores desde la base de datos
    private void inicializarContadores(ConexionMySQL conexion) throws SQLException {
        String sql = "SELECT tipo_tarjeta, COUNT(*) FROM Tarjeta GROUP BY tipo_tarjeta";
        try (Connection connection = conexion.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                String tipo = rs.getString(1);
                int count = rs.getInt(2);
                switch (tipo) {
                    case "NACIONAL":
                        contadorNacional = count;
                        break;
                    case "REGIONAL":
                        contadorRegional = count;
                        break;
                    case "INTERNACIONAL":
                        contadorInternacional = count;
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de tarjeta desconocido");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Método para generar el número secuencial basado en el tipo de tarjeta
    public String generarNumeroSecuencial(TipoTarjeta tipo) {
        String secuencia;
        switch (tipo) {
            case NACIONAL:
                secuencia = String.format("%05d", ++contadorNacional);
                return "4256 3102 654" + secuencia.substring(0, 1) + " " + secuencia.substring(1);
            case REGIONAL:
                secuencia = String.format("%05d", ++contadorRegional);
                return "4256 3102 656" + secuencia.substring(0, 1) + " " + secuencia.substring(1);
            case INTERNACIONAL:
                secuencia = String.format("%05d", ++contadorInternacional);
                return "4256 3102 658" + secuencia.substring(0, 1) + " " + secuencia.substring(1);
            default:
                throw new IllegalArgumentException("Tipo de tarjeta desconocido");
        }
    }
}

