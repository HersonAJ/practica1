/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import com.mycompany.banco.Backend.Autorizacion;
import com.mycompany.banco.Backend.ConexionMySQL;
import com.mycompany.banco.Backend.Solicitud;
import com.mycompany.banco.Backend.Tarjeta;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author herson
 */
public class SubOpcion2AutorizacionDeTarjeta extends JInternalFrame {

    protected JTextField numeroSolicitud;

    public SubOpcion2AutorizacionDeTarjeta() {
        setTitle("Autorización de tarjetas");
        setSize(500, 400);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);

        JPanel autorizacion = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        autorizacion.add(new JLabel("Número de Solicitud"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        numeroSolicitud = new JTextField(20);
        autorizacion.add(numeroSolicitud, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JButton autorizar = new JButton("Autorizar Tarjeta");
        autorizar.addActionListener(e -> validarCampos());
        autorizacion.add(autorizar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton cancelar = new JButton("Cancelar Solicitud");
        cancelar.addActionListener(e -> cancelarSolicitud());
        autorizacion.add(cancelar, gbc);

        add(autorizacion, BorderLayout.CENTER);
    }

    protected void validarCampos() {
        if (numeroSolicitud.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El número de solicitud no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                int numSolicitud = Integer.parseInt(numeroSolicitud.getText());
                Autorizacion autorizacion = new Autorizacion(numSolicitud);
                Tarjeta tarjeta = autorizacion.autorizar();
                JOptionPane.showMessageDialog(this, "Tarjeta autorizada y generada exitosamente: " + tarjeta.getNumeroTarjeta(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al autorizar la tarjeta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void cancelarSolicitud() {
        if (numeroSolicitud.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El número de solicitud no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                int numSolicitud = Integer.parseInt(numeroSolicitud.getText());
                try (Connection connection = ConexionMySQL.getConnection()) {
                    Autorizacion autorizacion = new Autorizacion(numSolicitud);
                    Solicitud solicitud = autorizacion.obtenerSolicitud(connection, numSolicitud);
                    if (solicitud == null) {
                        throw new IllegalArgumentException("Solicitud no encontrada");
                    }

                    String estadoActual = solicitud.getEstado();
                    if ("APROBADA".equals(estadoActual) || "RECHAZADA".equals(estadoActual)) {
                        JOptionPane.showMessageDialog(this, "No se puede cancelar una solicitud que ya está " + estadoActual.toLowerCase(), "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        autorizacion.actualizarEstadoSolicitud(connection, numSolicitud, "RECHAZADA", "El usuario canceló la solicitud");
                        JOptionPane.showMessageDialog(this, "Solicitud cancelada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cancelar la solicitud: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}