/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

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
public class SubOpcion3ConsultarTarjeta extends JInternalFrame {
    
    protected JTextField numeroTarjeta;
    protected JLabel infoTarjeta;

    public SubOpcion3ConsultarTarjeta() {
        setTitle("Consultar Tarjetas");
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);

        // Configuración del panel
        JPanel consultaTarjeta = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Elementos del formulario
        gbc.gridx = 0;
        gbc.gridy = 2;
        consultaTarjeta.add(new JLabel("Ingrese el número de tarjeta:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        numeroTarjeta = new JTextField(20);
        consultaTarjeta.add(numeroTarjeta, gbc);

        // Botón consulta
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton consultar = new JButton("Consultar");
        consultar.addActionListener(e -> validarCampos());
        consultaTarjeta.add(consultar, gbc);

        // Información de la tarjeta
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        infoTarjeta = new JLabel();
        consultaTarjeta.add(infoTarjeta, gbc);

        add(consultaTarjeta, BorderLayout.CENTER);
    }

    protected void validarCampos() {
        if (numeroTarjeta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }


}



