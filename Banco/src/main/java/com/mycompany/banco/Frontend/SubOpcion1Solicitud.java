/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
public class SubOpcion1Solicitud extends JInternalFrame {

    private final JTextField campoNumeroSolicitud;
    private final JComboBox<String> tipoCombo;
    private final JTextField campoNombreCompleto;
    private final JTextField campoSalario;
    private final JTextField campoDireccion;

    public SubOpcion1Solicitud() {
        setTitle("Solicitudes");
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);

        // Configurando el panel
        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Elementos de la primera fila
        gbc.gridx = 0;
        gbc.gridy = 0;
        formulario.add(new JLabel("Numero de solicitud"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        campoNumeroSolicitud = new JTextField(20);
        campoNumeroSolicitud.setEditable(false); 
        //campoNumeroSolicitud.setText(String.valueOf());
        formulario.add(campoNumeroSolicitud, gbc);

        // Elementos de la segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        formulario.add(new JLabel("Tipo de Tarjeta"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        tipoCombo = new JComboBox<>(new String[]{"Nacional", "Regional", "Internacional"});
        formulario.add(tipoCombo, gbc);

        // Elementos de la tercera fila
        gbc.gridx = 0;
        gbc.gridy = 2;
        formulario.add(new JLabel("Nombre Completo"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        campoNombreCompleto = new JTextField(20);
        formulario.add(campoNombreCompleto, gbc);

        // Elementos de la cuarta fila
        gbc.gridx = 0;
        gbc.gridy = 3;
        formulario.add(new JLabel("Salario"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        campoSalario = new JTextField(20);
        formulario.add(campoSalario, gbc);

        // Elementos de la quinta fila
        gbc.gridx = 0;
        gbc.gridy = 4;
        formulario.add(new JLabel("Direccion"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        campoDireccion = new JTextField(20);
        formulario.add(campoDireccion, gbc);

        // Botón de enviar
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(e -> validarCampos());
        formulario.add(enviarButton, gbc);

        add(formulario, BorderLayout.CENTER);
    }

private void validarCampos() {
    if (tipoCombo.getSelectedItem() == null ||
        campoNombreCompleto.getText().isEmpty() ||
        campoSalario.getText().isEmpty() ||
        campoDireccion.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
    } 
}

}
