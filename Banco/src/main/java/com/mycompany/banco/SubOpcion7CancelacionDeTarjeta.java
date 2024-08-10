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
public class SubOpcion7CancelacionDeTarjeta extends JInternalFrame {
    
    protected JTextField numeroTarjeta;
    
    public SubOpcion7CancelacionDeTarjeta(){
        setTitle("Cancenalacion de Tarjtas");
        setSize(500,400);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);
        setVisible(true);
        
           //configuracion del panel 
    JPanel cancelacion = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    
    //elementos del formulario 
    
    gbc.gridx = 0;
    gbc.gridy = 2;
    cancelacion.add(new JLabel("Ingrese el numero de tarjeta:"), gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 2;
    numeroTarjeta = new JTextField(20);
    cancelacion.add(numeroTarjeta, gbc);
    
    //boton consulta
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 2;
    JButton cancelar = new JButton("Cancelar Tarjeta");
    cancelar.addActionListener (e -> validarCampos());
    cancelacion.add(cancelar, gbc);
    
    add(cancelacion , BorderLayout.CENTER);
    
    }

    protected void validarCampos() {
        if (numeroTarjeta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
}