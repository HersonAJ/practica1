/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

        // Configuración del panel
        JPanel autorizacion = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Elementos del formulario
        gbc.gridx = 0;
        gbc.gridy = 0;
        autorizacion.add(new JLabel("Número de Solicitud"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        numeroSolicitud = new JTextField(20);
        autorizacion.add(numeroSolicitud, gbc);

        // Botón consulta
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JButton autorizar = new JButton("Autorizar Tarjeta");
        autorizar.addActionListener(e -> validarCampos());
        autorizacion.add(autorizar, gbc);

        // Botón de recarga
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton recargar = new JButton("Recargar Solicitudes");
        autorizacion.add(recargar, gbc);

        add(autorizacion, BorderLayout.CENTER);
    }
    
    protected void validarCampos() {
        if (numeroSolicitud.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El número de solicitud no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
