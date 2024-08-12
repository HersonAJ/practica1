/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
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

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField filterField1, filterField2;
    private JButton filterButton;
    private JTextField numeroTarjeta, tipoTarjeta, nombreCliente, direccionCliente, montoTotal, intereses, saldoTotal;

    public reportesSubOpcion1EstadoDeCuenta() {
        setTitle("Estado de Cuenta");
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
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcFilter = new GridBagConstraints();
        gbcFilter.insets = new Insets(5, 5, 5, 5);
        gbcFilter.fill = GridBagConstraints.HORIZONTAL;

        // Configuración de los filtros
        filterField1 = new JTextField(15);
        filterField2 = new JTextField(15);
        filterButton = new JButton("Filtrar");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        // Agregar el botón "Filtrar" al principio, seguido de los campos
        gbcFilter.gridx = 0;
        gbcFilter.gridy = 0;
        filterPanel.add(filterButton, gbcFilter);

        gbcFilter.gridx = 1;
        gbcFilter.gridy = 0;
        filterPanel.add(new JLabel("Por Numero de Tarjeta:"), gbcFilter);

        gbcFilter.gridx = 2;
        gbcFilter.gridy = 0;
        filterPanel.add(filterField1, gbcFilter);

        gbcFilter.gridx = 3;
        gbcFilter.gridy = 0;
        filterPanel.add(new JLabel("Por Tipo de Tarjeta:"), gbcFilter);

        gbcFilter.gridx = 4;
        gbcFilter.gridy = 0;
        filterPanel.add(filterField2, gbcFilter);

        // Añadir el panel de filtros al panel principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(filterPanel, gbc);

        // Panel para la información de la tarjeta
        JPanel infoTarjetaPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        numeroTarjeta = new JTextField("XXXXX", 15);
        numeroTarjeta.setEditable(false);
        tipoTarjeta = new JTextField("XXXX", 15);
        tipoTarjeta.setEditable(false);
        nombreCliente = new JTextField("XXXX", 15);
        nombreCliente.setEditable(false);
        direccionCliente = new JTextField("XXXX", 15);
        direccionCliente.setEditable(false);

        infoTarjetaPanel.add(new JLabel("Número Tarjeta:"));
        infoTarjetaPanel.add(numeroTarjeta);
        infoTarjetaPanel.add(new JLabel("Tipo Tarjeta:"));
        infoTarjetaPanel.add(tipoTarjeta);
        infoTarjetaPanel.add(new JLabel("Nombre Cliente:"));
        infoTarjetaPanel.add(nombreCliente);
        infoTarjetaPanel.add(new JLabel("Dirección Cliente:"));
        infoTarjetaPanel.add(direccionCliente);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(infoTarjetaPanel, gbc);

        // Crear el modelo de la tabla
        String[] columnNames = {"FECHA", "TIPO DE MOVIMIENTO", "DESCRIPCIÓN", "ESTABLECIMIENTO", "MONTO"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // Agregar la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(scrollPane, gbc);

        // Panel para mostrar los totales
        JPanel totalsPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        montoTotal = new JTextField("XXXXX", 15);
        montoTotal.setEditable(false);
        intereses = new JTextField("XXXX", 15);
        intereses.setEditable(false);
        saldoTotal = new JTextField("XXXX", 15);
        saldoTotal.setEditable(false);

        totalsPanel.add(new JLabel("Monto Total:"));
        totalsPanel.add(montoTotal);
        totalsPanel.add(new JLabel("Intereses:"));
        totalsPanel.add(intereses);
        totalsPanel.add(new JLabel("Saldo Total:"));
        totalsPanel.add(saldoTotal);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        mainPanel.add(totalsPanel, gbc);

        // Agregar el panel principal a un JScrollPane
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        add(mainScrollPane, BorderLayout.CENTER);
    }
}
