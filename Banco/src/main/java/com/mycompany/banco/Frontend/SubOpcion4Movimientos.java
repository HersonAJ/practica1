/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import com.mycompany.banco.Backend.Movimiento;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
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
    
    
    protected void validarCampos() {
        if (numeroTarjeta.getText().isEmpty() ||
            fechaOperacion.getText().isEmpty() ||
            tipoMovimiento.getSelectedItem() == null ||
            descripcion.getText().isEmpty() ||
            codigoEstablecimiento.getText().isEmpty() ||
            monto.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String numTarjeta = numeroTarjeta.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = sdf.parse(fechaOperacion.getText());
                String tipoMov = (String) tipoMovimiento.getSelectedItem();
                String desc = descripcion.getText();
                String estab = codigoEstablecimiento.getText();
                double montoOperacion = Double.parseDouble(monto.getText());

                Movimiento movimiento = new Movimiento(numTarjeta, fecha, tipoMov, desc, estab, montoOperacion);
                movimiento.guardarEnBaseDeDatos();
                JOptionPane.showMessageDialog(this, "Movimiento registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al registrar el movimiento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}