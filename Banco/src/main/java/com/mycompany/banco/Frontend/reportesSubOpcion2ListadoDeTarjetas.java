/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import com.mycompany.banco.Backend.ConexionMySQL;
import java.awt.*;
import java.awt.Insets;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author herson
 */
public class reportesSubOpcion2ListadoDeTarjetas extends JInternalFrame {

    private JTable table;
    private DefaultTableModel listadoTarjetas;
    private JTextField filtro2, filtro3, filtroFechaInicio, filtroFechaFin;
    private JComboBox<String> filtro1, filtro5; 
    private JButton consultar;

    public reportesSubOpcion2ListadoDeTarjetas() {
        setTitle("Listado de Tarjetas");
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
        filtro1 = new JComboBox<>(new String []{"", "INTERNACIONAL", "REGIONAL", "NACIONAL"});
        filtro2 = new JTextField(15);
        filtro3 = new JTextField(15);
        
        filtroFechaInicio = new JTextField(10);
        filtroFechaFin = new JTextField(10);
        
        filtro5 = new JComboBox<>(new String []{"", "ACTIVA", "CANCELADA"});
        consultar = new JButton("Consultar");
        consultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });

        // Agregar el botón "Filtrar" al principio
        gbcFilter.gridx = 0;
        gbcFilter.gridy = 0;
        panelFiltros.add(consultar, gbcFilter);

        gbcFilter.gridx = 1;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Por Tipo"), gbcFilter);

        gbcFilter.gridx = 2;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro1, gbcFilter);

        gbcFilter.gridx = 3;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Por Nombre:"), gbcFilter);

        gbcFilter.gridx = 4;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro2, gbcFilter);

        gbcFilter.gridx = 5;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Limite mayor a:"), gbcFilter);

        gbcFilter.gridx = 6;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro3, gbcFilter);

        gbcFilter.gridx = 7;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Fecha Inicio (dd/mm/aaaa):"), gbcFilter);

        gbcFilter.gridx = 8;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtroFechaInicio, gbcFilter);

        gbcFilter.gridx = 9;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Fecha Fin (dd/mm/aaaa):"), gbcFilter);

        gbcFilter.gridx = 10;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtroFechaFin, gbcFilter);

        gbcFilter.gridx = 11;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Por Estado:"), gbcFilter);

        gbcFilter.gridx = 12;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro5, gbcFilter);

        // Añadir el panel de filtros al panel principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(panelFiltros, gbc);

        // Crear el modelo de la tabla
        String[] columnNames = {"NUMERO TARJETA", "TIPO DE TARJETA", "LIMITE", "NOMBRE", "DIRECCION", "FECHA", "ESTADO"};
        listadoTarjetas = new DefaultTableModel(columnNames, 0);
        table = new JTable(listadoTarjetas);

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

    private void aplicarFiltros() {
        String tipo = (String) filtro1.getSelectedItem();
        String nombre = filtro2.getText().trim();
        double limite = 0;
        String fechaInicio = filtroFechaInicio.getText().trim();
        String fechaFin = filtroFechaFin.getText().trim();
        String estado = (String) filtro5.getSelectedItem();

        // Validación para el campo de límite
        if (!filtro3.getText().trim().isEmpty()) {
            try {
                limite = Double.parseDouble(filtro3.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El límite debe ser un número válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Convertir las fechas al formato yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfMySQL = new SimpleDateFormat("yyyy-MM-dd");
        String fechaInicioMySQL = null;
        String fechaFinMySQL = null;

        try {
            if (!fechaInicio.isEmpty()) {
                fechaInicioMySQL = sdfMySQL.format(sdf.parse(fechaInicio));
            }
            if (!fechaFin.isEmpty()) {
                fechaFinMySQL = sdfMySQL.format(sdf.parse(fechaFin));
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use dd/MM/yyyy.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Limpiar la tabla antes de agregar nuevos datos
        listadoTarjetas.setRowCount(0);

        ConexionMySQL conection = new ConexionMySQL();
        ResultSet rs = null;

        try {
            rs = conection.obtenerListadoTarjetas(tipo, nombre, limite, fechaInicioMySQL, fechaFinMySQL, estado);

            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                String numeroTarjeta = rs.getString("numero_tarjeta");
                String tipoTarjeta = rs.getString("tipo_tarjeta");
                double limiteTarjeta = rs.getDouble("limite");
                String nombreCliente = rs.getString("nombre_cliente");
                String direccion = rs.getString("direccion_cliente");
                String estadoTarjeta = rs.getString("estado_tarjeta");
                String fechaEstado = rs.getString("fecha_ultimo_estado");

                // Convertir la fecha al formato dd/MM/yyyy
                String fechaEstadoFormateada = fechaEstado != null ? sdf.format(sdfMySQL.parse(fechaEstado)) : "";

                listadoTarjetas.addRow(new Object[]{numeroTarjeta, tipoTarjeta, limiteTarjeta, nombreCliente, direccion, fechaEstadoFormateada, estadoTarjeta});
            }

            if (!hasResults) {
                JOptionPane.showMessageDialog(this, "No se han encontrado resultados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la consulta: " + e.getMessage(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
}


}
