/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public EstadoDeCuenta(String numeroTarjeta) throws SQLException {
        this.numeroTarjeta = numeroTarjeta;
        cargarDatosTarjeta();
        cargarMovimientos();
        calcularTotales();
    }

    private void cargarDatosTarjeta() throws SQLException {
        String sql = "SELECT tipo_tarjeta, nombre_cliente, direccion_cliente FROM Tarjeta WHERE numero_tarjeta = ? AND estado_tarjeta = 'ACTIVA'";
        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, numeroTarjeta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.tipoTarjeta = rs.getString("tipo_tarjeta");
                    this.nombreCliente = rs.getString("nombre_cliente");
                    this.direccionCliente = rs.getString("direccion_cliente");
                } else {
                    throw new IllegalArgumentException("Tarjeta no encontrada o no está activa");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void cargarMovimientos() throws SQLException {
        movimientos = new ArrayList<>();
        String sql = "SELECT fecha, tipo_movimiento, descripcion, establecimiento, monto FROM Movimiento WHERE numero_tarjeta = ?";
        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, numeroTarjeta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movimiento movimiento = new Movimiento(
                        numeroTarjeta,
                        rs.getDate("fecha"),
                        rs.getString("tipo_movimiento"),
                        rs.getString("descripcion"),
                        rs.getString("establecimiento"),
                        rs.getDouble("monto")
                    );
                    movimientos.add(movimiento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    private void calcularTotales() {
        double totalCargos = movimientos.stream()
            .filter(mov -> mov.getTipoMovimiento().equals("CARGO"))
            .mapToDouble(Movimiento::getMonto)
            .sum();

        double totalAbonos = movimientos.stream()
            .filter(mov -> mov.getTipoMovimiento().equals("ABONO"))
            .mapToDouble(Movimiento::getMonto)
            .sum();

        montoTotal = totalCargos - totalAbonos;
        intereses = calcularIntereses(montoTotal, tipoTarjeta);
        saldoTotal = montoTotal + intereses;
    }

    private double calcularIntereses(double monto, String tipoTarjeta) {
        switch (tipoTarjeta) {
            case "NACIONAL":
                return monto * 0.012;
            case "REGIONAL":
                return monto * 0.023;
            case "INTERNACIONAL":
                return monto * 0.0375;
            default:
                throw new IllegalArgumentException("Tipo de tarjeta desconocido");
        }
    }

    public static List<EstadoDeCuenta> filtrarPorNumeroTarjeta(String numeroTarjeta) throws SQLException {
        List<EstadoDeCuenta> resultados = new ArrayList<>();
        String sql = "SELECT numero_tarjeta FROM Tarjeta WHERE numero_tarjeta LIKE ? AND estado_tarjeta = 'ACTIVA'";
        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + numeroTarjeta + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new EstadoDeCuenta(rs.getString("numero_tarjeta")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return resultados;
    }

    public static List<EstadoDeCuenta> filtrarPorTipoTarjeta(String tipoTarjeta) throws SQLException {
        List<EstadoDeCuenta> resultados = new ArrayList<>();
        String sql = "SELECT numero_tarjeta FROM Tarjeta WHERE tipo_tarjeta = ? AND estado_tarjeta = 'ACTIVA'";
        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoTarjeta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new EstadoDeCuenta(rs.getString("numero_tarjeta")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return resultados;
    }

    public static List<EstadoDeCuenta> obtenerTodasLasTarjetasActivas() throws SQLException {
        List<EstadoDeCuenta> resultados = new ArrayList<>();
        String sql = "SELECT numero_tarjeta FROM Tarjeta WHERE estado_tarjeta = 'ACTIVA'";
        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultados.add(new EstadoDeCuenta(rs.getString("numero_tarjeta")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return resultados;
    }

    public static List<EstadoDeCuenta> filtrarPorSaldoMayorA(double monto) throws SQLException {
        List<EstadoDeCuenta> resultados = new ArrayList<>();
        String sql = "SELECT numero_tarjeta FROM Tarjeta WHERE estado_tarjeta = 'ACTIVA'";
        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EstadoDeCuenta estado = new EstadoDeCuenta(rs.getString("numero_tarjeta"));
                if (estado.getSaldoTotal() > monto) {
                    resultados.add(estado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return resultados;
    }

    public static List<EstadoDeCuenta> filtrarPorInteresesMayorA(double intereses) throws SQLException {
        List<EstadoDeCuenta> resultados = new ArrayList<>();
        String sql = "SELECT numero_tarjeta FROM Tarjeta WHERE estado_tarjeta = 'ACTIVA'";
        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EstadoDeCuenta estado = new EstadoDeCuenta(rs.getString("numero_tarjeta"));
                if (estado.getIntereses() > intereses) {
                    resultados.add(estado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return resultados;
    }


    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public double getIntereses() {
        return intereses;
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }
}
