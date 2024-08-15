/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.sql.Connection;
import java.sql.DriverManager;
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
}