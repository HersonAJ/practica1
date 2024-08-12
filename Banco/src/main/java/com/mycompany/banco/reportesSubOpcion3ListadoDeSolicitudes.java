/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class reportesSubOpcion3ListadoDeSolicitudes extends JInternalFrame {

    private JTable table;
    private DefaultTableModel listadoSolicitudes;
    private JTextField filtro1, filtro3;
    private JComboBox<String> filtro2, filtro4;
    private JButton consultar;

    public reportesSubOpcion3ListadoDeSolicitudes() {
        setTitle("Listado de Solicitudes");
        setSize(700, 700);
        setClosable(true);
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setVisible(true);

        // Crear un panel principal con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel para filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        GridBagConstraints gbcFilter = new GridBagConstraints();
        gbcFilter.insets = new Insets(5, 5, 5, 5);
        gbcFilter.fill = GridBagConstraints.HORIZONTAL;

        // Configuración de los filtros
        filtro1 = new JTextField(15);
        filtro2 = new JComboBox<>(new String[]{"", "INTERNACIONAL", "REGIONAL","NACIONAL"});
        filtro3 = new JTextField(15);
        filtro4 = new JComboBox<>(new String[]{"", "PENDIENTE", "APOBADA","RECHAZADA"});
        consultar = new JButton("Consultar");
        consultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        // Agregar el botón "Filtrar" al principio, seguido de los campos
        gbcFilter.gridx = 0;
        gbcFilter.gridy = 0;
        panelFiltros.add(consultar, gbcFilter);

        gbcFilter.gridx = 1;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Rango de Fecha: "), gbcFilter);

        gbcFilter.gridx = 2;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro1, gbcFilter);

        gbcFilter.gridx = 3;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Por Tipo:"), gbcFilter);

        gbcFilter.gridx = 4;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro2, gbcFilter);

        gbcFilter.gridx = 5;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Salario Mayor A:"), gbcFilter);

        gbcFilter.gridx = 6;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro3, gbcFilter);

        gbcFilter.gridx = 7;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Estado:"), gbcFilter);

        gbcFilter.gridx = 8;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro4, gbcFilter);


        // Añadir el panel de filtros al panel principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(panelFiltros, gbc);

        // Crear el modelo de la tabla
        String[] columnNames = {"NUMERO SOLICITUD", "FECHA", "TIPO", "NOMBRE", "SALARIO", "DIRECCION","ESTADO"};
        listadoSolicitudes = new DefaultTableModel(columnNames, 0);
        table = new JTable(listadoSolicitudes);

        // Añadir la tabla a un JScrollPane para que sea desplazable
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(scrollPane, gbc);

        // Añadir el panel principal a un JScrollPane
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        add(mainScrollPane, BorderLayout.CENTER);
    }
 
}
