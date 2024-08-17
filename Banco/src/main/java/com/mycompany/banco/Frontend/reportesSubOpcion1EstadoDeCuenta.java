/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import com.mycompany.banco.Backend.EstadoDeCuenta;
import com.mycompany.banco.Backend.Movimiento;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
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
                mostrarTodo();
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

        try {
            List<EstadoDeCuenta> estadosDeCuenta;

            if (!numeroTarjetaFiltro.isEmpty()) {
                estadosDeCuenta = EstadoDeCuenta.filtrarPorNumeroTarjeta(numeroTarjetaFiltro);
            } else if (tipoTarjetaFiltro != null && !tipoTarjetaFiltro.isEmpty()) {
                estadosDeCuenta = EstadoDeCuenta.filtrarPorTipoTarjeta(tipoTarjetaFiltro);
            } else if (!saldoMayorAFiltro.isEmpty()) {
                double saldoMayorA = Double.parseDouble(saldoMayorAFiltro);
                estadosDeCuenta = EstadoDeCuenta.filtrarPorSaldoMayorA(saldoMayorA);
            } else if (!interesesMayorAFiltro.isEmpty()) {
                double interesesMayorA = Double.parseDouble(interesesMayorAFiltro);
                estadosDeCuenta = EstadoDeCuenta.filtrarPorInteresesMayorA(interesesMayorA);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un filtro.");
                return;
            }

            if (estadosDeCuenta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay resultados para el reporte.");
                return;
            }

            // Limpiar el panel principal antes de agregar nuevos datos
            mainPanel.removeAll();

            for (EstadoDeCuenta estado : estadosDeCuenta) {
                JPanel tarjetaPanel = new JPanel();
                tarjetaPanel.setLayout(new BoxLayout(tarjetaPanel, BoxLayout.Y_AXIS));
                tarjetaPanel.setBorder(BorderFactory.createTitledBorder("Tarjeta"));

                // Información de la tarjeta
                tarjetaPanel.add(new JLabel("Número Tarjeta: " + estado.getNumeroTarjeta()));
                tarjetaPanel.add(new JLabel("Tipo Tarjeta: " + estado.getTipoTarjeta()));
                tarjetaPanel.add(new JLabel("Nombre Cliente: " + estado.getNombreCliente()));
                tarjetaPanel.add(new JLabel("Dirección Cliente: " + estado.getDireccionCliente()));

                // Crear el modelo de la tabla
                String[] columnNames = {"FECHA", "TIPO DE MOVIMIENTO", "DESCRIPCIÓN", "ESTABLECIMIENTO", "MONTO"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);

                // Agregar los movimientos a la tabla
                for (Movimiento mov : estado.getMovimientos()) {
                    Object[] row = {
                        mov.getFecha(),
                        mov.getTipoMovimiento(),
                        mov.getDescripcion(),
                        mov.getEstablecimiento(),
                        mov.getMonto()
                    };
                    tableModel.addRow(row);
                }

                // Agregar la tabla a un JScrollPane
                JScrollPane scrollPane = new JScrollPane(table);
                tarjetaPanel.add(scrollPane);

                // Totales
                tarjetaPanel.add(new JLabel("Monto Total: " + String.format("%.2f", estado.getMontoTotal())));
                tarjetaPanel.add(new JLabel("Intereses: " + String.format("%.2f", estado.getIntereses())));
                tarjetaPanel.add(new JLabel("Saldo Total: " + String.format("%.2f", estado.getSaldoTotal())));

                // Añadir el panel de la tarjeta al panel principal
                mainPanel.add(tarjetaPanel);
            }

            // Refrescar el panel principal
            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los datos: " + e.getMessage());
        }
    }


     private void mostrarTodo() {
        try {
            List<EstadoDeCuenta> estadosDeCuenta = EstadoDeCuenta.obtenerTodasLasTarjetasActivas();

            if (estadosDeCuenta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay resultados para el reporte.");
                return;
            }

            // Limpiar el panel principal antes de agregar nuevos datos
            mainPanel.removeAll();

            for (EstadoDeCuenta estado : estadosDeCuenta) {
                JPanel tarjetaPanel = new JPanel();
                tarjetaPanel.setLayout(new BoxLayout(tarjetaPanel, BoxLayout.Y_AXIS));
                tarjetaPanel.setBorder(BorderFactory.createTitledBorder("Tarjeta"));

                // Información de la tarjeta
                tarjetaPanel.add(new JLabel("Número Tarjeta: " + estado.getNumeroTarjeta()));
                tarjetaPanel.add(new JLabel("Tipo Tarjeta: " + estado.getTipoTarjeta()));
                tarjetaPanel.add(new JLabel("Nombre Cliente: " + estado.getNombreCliente()));
                tarjetaPanel.add(new JLabel("Dirección Cliente: " + estado.getDireccionCliente()));

                // Crear el modelo de la tabla
                String[] columnNames = {"FECHA", "TIPO DE MOVIMIENTO", "DESCRIPCIÓN", "ESTABLECIMIENTO", "MONTO"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);

                // Agregar los movimientos a la tabla
                for (Movimiento mov : estado.getMovimientos()) {
                    Object[] row = {
                        mov.getFecha(),
                        mov.getTipoMovimiento(),
                        mov.getDescripcion(),
                        mov.getEstablecimiento(),
                        mov.getMonto()
                    };
                    tableModel.addRow(row);
                }

                // Agregar la tabla a un JScrollPane
                JScrollPane scrollPane = new JScrollPane(table);
                tarjetaPanel.add(scrollPane);

                // Totales
                tarjetaPanel.add(new JLabel("Monto Total: " + String.format("%.2f", estado.getMontoTotal())));
                tarjetaPanel.add(new JLabel("Intereses: " + String.format("%.2f", estado.getIntereses())));
                tarjetaPanel.add(new JLabel("Saldo Total: " + String.format("%.2f", estado.getSaldoTotal())));

                // Añadir el panel de la tarjeta al panel principal
                mainPanel.add(tarjetaPanel);
            }

            // Refrescar el panel principal
            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los datos: " + e.getMessage());
        }
    }
}
