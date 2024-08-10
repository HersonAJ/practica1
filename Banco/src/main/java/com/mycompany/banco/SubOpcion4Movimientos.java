/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author herson
 */
public class SubOpcion4Movimientos extends JInternalFrame {
    
    protected JTextField numeroTarjeta;
    protected JFormattedTextField fechaOperacion;
    protected JComboBox tipoMovimiento;
    protected JTextArea descripcion;
    protected JTextField codigoEstablecimiento;
    protected JTextField monto;
   

    public SubOpcion4Movimientos() {
        setTitle("Movimientos");
        setSize(400, 500);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);
        
        //configurando el panel 
        JPanel formularioMovimiento = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        //elementos de la primera fila 
        gbc.gridx = 0;
        gbc.gridy = 0;
        formularioMovimiento.add(new JLabel("Numero de Tarjeta:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        numeroTarjeta = new JTextField(20);
        formularioMovimiento.add(numeroTarjeta, gbc);
        
        
        //elementos de la segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        formularioMovimiento.add(new JLabel("Fecha de Movimiento: dd/mm/aaaa"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        fechaOperacion = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        formularioMovimiento.add(fechaOperacion, gbc);
        
        //elementos de la tercera fila
        gbc.gridx = 0;
        gbc.gridy = 2;
        formularioMovimiento.add(new JLabel("Tipo de Movimiento:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        tipoMovimiento = new JComboBox<>(new String[]{"CARGO", "ABONO"});
        formularioMovimiento.add(tipoMovimiento, gbc);
        
        //elementos de la cuarta fila 
        gbc.gridx = 0;
        gbc.gridy = 3;
        formularioMovimiento.add(new JLabel("Descripcion:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        descripcion = new JTextArea();
        formularioMovimiento.add(descripcion, gbc);
        
        //elementos de la quinta fila 
        gbc.gridx = 0;
        gbc.gridy = 4;
        formularioMovimiento.add(new JLabel("Codigo del Establecimiento:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        codigoEstablecimiento = new JTextField(20);
        formularioMovimiento.add(codigoEstablecimiento, gbc);
        
        //elementos de la sexta fila
        gbc.gridx = 0;
        gbc.gridy = 5;
        formularioMovimiento.add(new JLabel("Monto:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        monto = new JTextField(20);
        formularioMovimiento.add(monto, gbc);
        
        //agregando boton para registrar movimiento 
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JButton registrar = new JButton("Registrar Movimiento");
        registrar.addActionListener(e -> validarCampos());
        formularioMovimiento.add(registrar, gbc);
        
        add(formularioMovimiento, BorderLayout.CENTER);
    }
    
    //validacion sin espacios vacios 
    protected void validarCampos() {
        if (numeroTarjeta.getText().isEmpty() ||
            fechaOperacion.getText().isEmpty() ||
            tipoMovimiento.getSelectedItem() == null ||
            descripcion.getText().isEmpty() ||
            codigoEstablecimiento.getText().isEmpty() ||
            monto.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
}