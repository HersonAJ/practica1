/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author herson
 */
public class SubOpcion1Solicitud  extends JInternalFrame {
    
    private JTextField numeroSolicitudCampo;
    private JFormattedTextField fechaCampo;
    private JComboBox<String> tipoCombo;
    private JTextField nombreCompletoCampo;
    private JTextField salarioCampo;
    private JTextField direccionCampo;

    public SubOpcion1Solicitud(){
        setTitle("Solicitudes");
        setSize(600, 600);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);
        
        //configurando el panel
        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        //elementos de la primera fila 
         gbc.gridx = 0;
        gbc.gridy = 0;
        formulario.add(new JLabel("Numero de solicitud"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        numeroSolicitudCampo = new JTextField(20);
        numeroSolicitudCampo.setEditable(true); 
        formulario.add(numeroSolicitudCampo, gbc);

  
        // Elementos de la segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        formulario.add(new JLabel("Fecha: dd/mm/aaaa"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        fechaCampo = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        fechaCampo.setColumns(10);
        formulario.add(fechaCampo, gbc);

        // Elementos de la tercera fila
        gbc.gridx = 0;
        gbc.gridy = 2;
        formulario.add(new JLabel("Tipo de Tarjeta"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        tipoCombo = new JComboBox<>(new String[]{"Nacional", "Regional", "Internacional"});
        formulario.add(tipoCombo, gbc);

        // Elementos de la cuarta fila
        gbc.gridx = 0;
        gbc.gridy = 3;
        formulario.add(new JLabel("Nombre Completo"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        nombreCompletoCampo = new JTextField(20);
        formulario.add(nombreCompletoCampo, gbc);

        // Elementos de la quinta fila
        gbc.gridx = 0;
        gbc.gridy = 4;
        formulario.add(new JLabel("Salario"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        salarioCampo = new JTextField(20);
        formulario.add(salarioCampo, gbc);

        // Elementos de la sexta fila
        gbc.gridx = 0;
        gbc.gridy = 5;
        formulario.add(new JLabel("Direccion"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        direccionCampo = new JTextField(20);
        formulario.add(direccionCampo, gbc);

        // Botón de enviar
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(e -> validarCampos());
        formulario.add(enviarButton, gbc);

        add(formulario, BorderLayout.CENTER);       
        
    }
    
    private void validarCampos() {
    if (numeroSolicitudCampo.getText().isEmpty() ||
        fechaCampo.getText().isEmpty() ||
        tipoCombo.getSelectedItem() == null ||
        nombreCompletoCampo.getText().isEmpty() ||
        salarioCampo.getText().isEmpty() ||
        direccionCampo.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
}
}
