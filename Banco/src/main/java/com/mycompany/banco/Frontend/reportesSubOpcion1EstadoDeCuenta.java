/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author herson
 */
public class reportesSubOpcion1EstadoDeCuenta extends JInternalFrame {

    private JPanel mainPanel;
    private JTextField filtro1, filtro3, filtro4;
    private JComboBox<String> filterComboBox;
    private JButton botonFiltrar, botonFiltrarTodo;

    public reportesSubOpcion1EstadoDeCuenta() {
        setTitle("Estado de Cuenta");
        setSize(700, 700);
        setClosable(true);
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setVisible(true);

        // Crear un panel principal con BoxLayout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        add(mainScrollPane, BorderLayout.CENTER);

        // Panel para filtros
        JPanel filtroPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcFilter = new GridBagConstraints();
        gbcFilter.insets = new Insets(5, 5, 5, 5);
        gbcFilter.fill = GridBagConstraints.HORIZONTAL;

        // Configuración de los filtros
        filtro1 = new JTextField(15);
        filterComboBox = new JComboBox<>(new String[]{"", "NACIONAL", "REGIONAL", "INTERNACIONAL"});
        filtro3 = new JTextField(15);
        filtro4 = new JTextField(15);
        botonFiltrar = new JButton("Filtrar");
        botonFiltrarTodo = new JButton("Mostrar Todo");

        botonFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });

        botonFiltrarTodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //mostrarTodo();
            }
        });

        // Agregar el botón "Filtrar" al principio, seguido de los campos
        gbcFilter.gridx = 0;
        gbcFilter.gridy = 0;
        filtroPanel.add(botonFiltrar, gbcFilter);

        gbcFilter.gridx = 1;
        gbcFilter.gridy = 0;
        filtroPanel.add(new JLabel("Por Numero de Tarjeta:"), gbcFilter);

        gbcFilter.gridx = 2;
        gbcFilter.gridy = 0;
        filtroPanel.add(filtro1, gbcFilter);

        gbcFilter.gridx = 3;
        gbcFilter.gridy = 0;
        filtroPanel.add(new JLabel("Por Tipo de Tarjeta:"), gbcFilter);

        gbcFilter.gridx = 4;
        gbcFilter.gridy = 0;
        filtroPanel.add(filterComboBox, gbcFilter);

        gbcFilter.gridx = 5;
        gbcFilter.gridy = 0;
        filtroPanel.add(new JLabel("Saldos Mayores A:"), gbcFilter);

        gbcFilter.gridx = 6;
        gbcFilter.gridy = 0;
        filtroPanel.add(filtro3, gbcFilter);

        gbcFilter.gridx = 7;
        gbcFilter.gridy = 0;
        filtroPanel.add(new JLabel("Intereses Mayores A:"), gbcFilter);

        gbcFilter.gridx = 8;
        gbcFilter.gridy = 0;
        filtroPanel.add(filtro4, gbcFilter);

        gbcFilter.gridx = 9;
        gbcFilter.gridy = 0;
        filtroPanel.add(botonFiltrarTodo, gbcFilter);

        // Añadir el panel de filtros al panel principal
        add(filtroPanel, BorderLayout.NORTH);
    }

    private void aplicarFiltros() {
        String numeroTarjetaFiltro = filtro1.getText();
        String tipoTarjetaFiltro = (String) filterComboBox.getSelectedItem();
        String saldoMayorAFiltro = filtro3.getText();
        String interesesMayorAFiltro = filtro4.getText();


}
}
