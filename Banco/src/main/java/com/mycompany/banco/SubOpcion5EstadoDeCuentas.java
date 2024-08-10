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
public class SubOpcion5EstadoDeCuentas extends JInternalFrame {
    
    protected JTextField nombreCliente;
    protected JTextField numeroTarjeta;
    
    public SubOpcion5EstadoDeCuentas(){
    setTitle("Estado de Cuentas");
    setSize(600,600);
    setClosable(true);
    setMaximizable(true);
    setIconifiable(true);
    setResizable(true);
    setVisible(true);
    setVisible(true);
    
    //configuracion del panel 
    JPanel consultaEstado = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    
    //elementos del formulario 
    
    //primera fila
    
    gbc.gridx = 0;
    gbc.gridy = 0;
    consultaEstado.add(new JLabel("Ingrese el numero de tarjeta:"), gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 0;
    numeroTarjeta = new JTextField(20);
    consultaEstado.add(numeroTarjeta, gbc);
    
    //segunda fila 
    
        gbc.gridx = 0;
        gbc.gridy = 1;
    consultaEstado.add(new JLabel("Ingrese el Nombre del Cliente"), gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 1;
    nombreCliente = new JTextField(50);
    consultaEstado.add(nombreCliente , gbc);
    
    
    //boton consulta
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 2;
    JButton consultar = new JButton("Consultar");
    consultar.addActionListener (e -> validarCampos());
    consultaEstado.add(consultar, gbc);
    
    add(consultaEstado , BorderLayout.CENTER);
    
    }

    
    protected void validarCampos() {
        if (numeroTarjeta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error",  JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
    