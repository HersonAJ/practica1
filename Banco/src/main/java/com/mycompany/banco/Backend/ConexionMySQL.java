/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author herson
 */
public class ConexionMySQL {
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/banco";
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    
    private static Connection connection;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || isClosed(connection)) {
            try {
                connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
                System.out.println("Conectado a la base de datos.");
                System.out.println("Esquema: " + connection.getCatalog());
            } catch (SQLException e) {
                System.out.println("Error al conectar a la DB");
                e.printStackTrace();
            }
    
        }
        //return connection;
        return DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
    }

    private static boolean isClosed(Connection conn) {
        try {
            return conn == null || conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión a la DB.");
            e.printStackTrace();
        }
    }
    
    public ResultSet obtenerListadoSolicitudes(String fechaInicio, String fechaFin, String tipo, double salario, String estado) throws SQLException {
        ResultSet rs = null;
        try {
            Connection connection = getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM Solicitud WHERE 1=1");

            if (fechaInicio != null && !fechaInicio.isEmpty() && fechaFin != null && !fechaFin.isEmpty()) {
                sql.append(" AND fecha BETWEEN ? AND ?");
            }
            if (tipo != null && !tipo.isEmpty()) {
                sql.append(" AND tipo = ?");
            }
            if (salario > 0) {
                sql.append(" AND salario > ?");
            }
            if (estado != null && !estado.isEmpty()) {
                sql.append(" AND estado = ?");
            }

            PreparedStatement stmt = connection.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (fechaInicio != null && !fechaInicio.isEmpty() && fechaFin != null && !fechaFin.isEmpty()) {
                stmt.setString(paramIndex++, fechaInicio);
                stmt.setString(paramIndex++, fechaFin);
            }
            if (tipo != null && !tipo.isEmpty()) {
                stmt.setString(paramIndex++, tipo);
            }
            if (salario > 0) {
                stmt.setDouble(paramIndex++, salario);
            }
            if (estado != null && !estado.isEmpty()) {
                stmt.setString(paramIndex++, estado);
            }

            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return rs;
    }
    
        public ResultSet obtenerListadoTarjetas(String tipo, String nombre, double limite, String fechaInicio, String fechaFin, String estado) throws SQLException {
            ResultSet rs = null;
            try {
                Connection connection = getConnection();
                StringBuilder sql = new StringBuilder("SELECT numero_tarjeta, tipo_tarjeta, limite, nombre_cliente, direccion_cliente, fecha_ultimo_estado, estado_tarjeta FROM Tarjeta WHERE 1=1");

                if (tipo != null && !tipo.isEmpty()) {
                    sql.append(" AND tipo_tarjeta = ?");
                }
                if (nombre != null && !nombre.isEmpty()) {
                    sql.append(" AND nombre_cliente LIKE ?");
                }
                if (limite > 0) {
                    sql.append(" AND limite > ?");
                }
                if (fechaInicio != null && !fechaInicio.isEmpty() && fechaFin != null && !fechaFin.isEmpty()) {
                    sql.append(" AND fecha_ultimo_estado BETWEEN ? AND ?");
                }
                if (estado != null && !estado.isEmpty()) {
                    sql.append(" AND estado_tarjeta = ?");
                }

                PreparedStatement stmt = connection.prepareStatement(sql.toString());
                int paramIndex = 1;

                if (tipo != null && !tipo.isEmpty()) {
                    stmt.setString(paramIndex++, tipo);
                }
                if (nombre != null && !nombre.isEmpty()) {
                    stmt.setString(paramIndex++, "%" + nombre + "%");
                }
                if (limite > 0) {
                    stmt.setDouble(paramIndex++, limite);
                }
                if (fechaInicio != null && !fechaInicio.isEmpty() && fechaFin != null && !fechaFin.isEmpty()) {
                    stmt.setString(paramIndex++, fechaInicio);
                    stmt.setString(paramIndex++, fechaFin);
                }
                if (estado != null && !estado.isEmpty()) {
                    stmt.setString(paramIndex++, estado);
                }

                rs = stmt.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
            return rs;
        }

    
}