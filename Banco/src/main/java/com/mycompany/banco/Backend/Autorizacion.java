/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author herson
 */
public class Autorizacion {
    private int idAutorizacion;
    private String numeroTarjeta;
    private int numeroSolicitud;
    private LocalDate fechaAutorizacion;

    public Autorizacion(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
        this.fechaAutorizacion = LocalDate.now();
    }

    public Tarjeta autorizar() throws SQLException {
        try (Connection connection = ConexionMySQL.getConnection()) {
            Solicitud solicitud = obtenerSolicitud(connection, numeroSolicitud);
            if (solicitud == null) {
                throw new IllegalArgumentException("Solicitud no encontrada");
            }

            // Verificar si la solicitud ya está aprobada
            if ("APROBADA".equals(solicitud.getEstado())) {
                throw new IllegalArgumentException("La solicitud ya está aprobada y no puede ser autorizada nuevamente");
            }

            double salario = solicitud.getSalario();
            double limite = calcularLimiteCredito(salario);
            String tipoSolicitud = solicitud.getTipo();
            boolean autorizada = verificarAutorizacion(limite, tipoSolicitud);
            String motivoRechazo = "";

            if (!autorizada) {
                motivoRechazo = obtenerMotivoRechazo(tipoSolicitud, limite);
            }

            if (autorizada) {

                ConexionMySQL conexion = new ConexionMySQL();
                GeneradorNumeroTarjeta generador = new GeneradorNumeroTarjeta(conexion);

                String numeroTarjeta;
                do {
                    numeroTarjeta = generador.generarNumeroSecuencial(TipoTarjeta.valueOf(tipoSolicitud));
                } while (numeroTarjetaExiste(connection, numeroTarjeta));

                Tarjeta tarjeta = new Tarjeta(numeroTarjeta, TipoTarjeta.valueOf(tipoSolicitud), limite, solicitud.getNombre(), solicitud.getDireccion(), "ACTIVA", numeroSolicitud);
                tarjeta.guardarEnBaseDeDatos(connection);

                // Actualizar la fecha de autorización en la tabla Tarjeta
                actualizarFechaUltimoEstado(connection, numeroTarjeta, LocalDate.now());

                guardarAutorizacionEnBaseDeDatos(connection, numeroTarjeta, numeroSolicitud, LocalDate.now());
                actualizarEstadoSolicitud(connection, numeroSolicitud, "APROBADA", null);
                return tarjeta;
            } else {
                actualizarEstadoSolicitud(connection, numeroSolicitud, "RECHAZADA", motivoRechazo);
                throw new IllegalArgumentException(motivoRechazo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private double calcularLimiteCredito(double salario) {
        return salario * 0.6;
    }

    private boolean verificarAutorizacion(double limite, String tipoSolicitud) {
        switch (tipoSolicitud) {
            case "NACIONAL":
                return limite >= 5000;
            case "REGIONAL":
                return limite >= 10000;
            case "INTERNACIONAL":
                return limite >= 20000;
            default:
                throw new IllegalArgumentException("Tipo de solicitud desconocido");
        }
    }

    private String obtenerMotivoRechazo(String tipoSolicitud, double limite) {
        switch (tipoSolicitud) {
            case "NACIONAL":
                return "Salario insuficiente para tarjeta NACIONAL";
            case "REGIONAL":
                return "Salario insuficiente para tarjeta REGIONAL";
            case "INTERNACIONAL":
                return "Salario insuficiente para tarjeta INTERNACIONAL";
            default:
                throw new IllegalArgumentException("Tipo de solicitud desconocido");
        }
    }

    private boolean numeroTarjetaExiste(Connection connection, String numeroTarjeta) throws SQLException {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM Tarjeta WHERE numero_tarjeta = '" + numeroTarjeta + "'";
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    existe = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return existe;
    }

    public Solicitud obtenerSolicitud(Connection connection, int numeroSolicitud) throws SQLException {
        Solicitud solicitud = null;
        String sql = "SELECT * FROM Solicitud WHERE numero_solicitud = " + numeroSolicitud;
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    LocalDate fecha = rs.getDate("fecha").toLocalDate();
                    solicitud = new Solicitud(
                        rs.getInt("numero_solicitud"),
                        fecha,
                        rs.getString("tipo"),
                        rs.getString("nombre"),
                        rs.getDouble("salario"),
                        rs.getString("direccion"),
                        rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return solicitud;
    }

    private void guardarAutorizacionEnBaseDeDatos(Connection connection, String numeroTarjeta, int numeroSolicitud, LocalDate fechaAutorizacion) throws SQLException {
        String sql = "INSERT INTO Autorizacion (numero_tarjeta, numero_solicitud, fecha_autorizacion) VALUES ('" + numeroTarjeta + "', " + numeroSolicitud + ", '" + java.sql.Date.valueOf(fechaAutorizacion) + "')";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void actualizarEstadoSolicitud(Connection connection, int numeroSolicitud, String estado, String motivoRechazo) throws SQLException {
        String sql = "UPDATE Solicitud SET estado = '" + estado + "', fecha_estado = '" + java.sql.Date.valueOf(LocalDate.now()) + "', motivo_rechazo = '" + motivoRechazo + "' WHERE numero_solicitud = " + numeroSolicitud;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void actualizarFechaUltimoEstado(Connection connection, String numeroTarjeta, LocalDate fechaUltimoEstado) throws SQLException {
        String sql = "UPDATE Tarjeta SET fecha_ultimo_estado = '" + java.sql.Date.valueOf(fechaUltimoEstado) + "' WHERE numero_tarjeta = '" + numeroTarjeta + "'";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}