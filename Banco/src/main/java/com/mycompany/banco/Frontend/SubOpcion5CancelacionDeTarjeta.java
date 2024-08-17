/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import com.mycompany.banco.Backend.Cancelacion;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
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
public class SubOpcion5CancelacionDeTarjeta extends JInternalFrame {
    
    protected JTextField numeroTarjeta;
    protected JLabel infoTarjeta;
    protected JButton confirmarCancelacion;

    public SubOpcion5CancelacionDeTarjeta() {
        setTitle("Cancelación de Tarjetas");
        setSize(500, 400);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);

        // Configuración del panel
        JPanel cancelacion = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Elementos del formulario
        gbc.gridx = 0;
        gbc.gridy = 2;
        cancelacion.add(new JLabel("Ingrese el número de tarjeta:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        numeroTarjeta = new JTextField(20);
        cancelacion.add(numeroTarjeta, gbc);

        // Botón consulta
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton consultar = new JButton("Consultar Tarjeta");
        consultar.addActionListener(e -> validarCampos());
        cancelacion.add(consultar, gbc);

        // Información de la tarjeta
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        infoTarjeta = new JLabel();
        cancelacion.add(infoTarjeta, gbc);

        // Botón confirmar cancelación
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        confirmarCancelacion = new JButton("Confirmar Cancelación");
        confirmarCancelacion.setVisible(false);
        confirmarCancelacion.addActionListener(e -> confirmarCancelacion());
        cancelacion.add(confirmarCancelacion, gbc);

        add(cancelacion, BorderLayout.CENTER);
    }

    protected void validarCampos() {
        if (numeroTarjeta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String numTarjeta = numeroTarjeta.getText();
                EstadoDeCuenta estadoDeCuenta = new EstadoDeCuenta(numTarjeta);
                mostrarInformacionTarjeta(estadoDeCuenta);
                confirmarCancelacion.setVisible(true);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al consultar la tarjeta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void mostrarInformacionTarjeta(EstadoDeCuenta estadoDeCuenta) {
        String info = "<html>Tarjeta: " + estadoDeCuenta.getNumeroTarjeta() + "<br>" +
                      "Tipo: " + estadoDeCuenta.getTipoTarjeta() + "<br>" +
                      "Cliente: " + estadoDeCuenta.getNombreCliente() + "<br>" +
                      "Dirección: " + estadoDeCuenta.getDireccionCliente() + "<br>" +
                      "Saldo Total: " + estadoDeCuenta.getSaldoTotal() + "</html>";
        infoTarjeta.setText(info);
    }

    private void confirmarCancelacion() {
        try {
            String numTarjeta = numeroTarjeta.getText();
            Cancelacion cancelacion = new Cancelacion(numTarjeta);
            cancelacion.cancelar();
            JOptionPane.showMessageDialog(this, "Tarjeta cancelada exitosamente: " + numTarjeta, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            confirmarCancelacion.setVisible(false);
            infoTarjeta.setText("");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cancelar la tarjeta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
