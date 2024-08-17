/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import com.mycompany.banco.Backend.ConexionMySQL;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private JTextField filtroFechaInicio, filtroFechaFin, filtro3;
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
        
        //modificando para el filtro de fecha
        filtroFechaInicio = new JTextField(10);
        filtroFechaFin = new JTextField(10);
        
        filtro2 = new JComboBox<>(new String[]{"", "INTERNACIONAL", "REGIONAL","NACIONAL"});
        filtro3 = new JTextField(15);
        filtro4 = new JComboBox<>(new String[]{"", "PENDIENTE", "APROBADA","RECHAZADA"});
        consultar = new JButton("Consultar");
        consultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    aplicarFiltros();
                } catch (SQLException ex) {
                    Logger.getLogger(reportesSubOpcion3ListadoDeSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Agregar el botón "Filtrar" al principio, seguido de los campos
        gbcFilter.gridx = 0;
        gbcFilter.gridy = 0;
        panelFiltros.add(consultar, gbcFilter);

        // Agregar los campos de fecha al panel de filtros
        gbcFilter.gridx = 1;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Fecha Inicio (dd/mm/aaaa):"), gbcFilter);

        gbcFilter.gridx = 2;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtroFechaInicio, gbcFilter);

        gbcFilter.gridx = 3;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Fecha Fin (dd/mm/aaaa):"), gbcFilter);

        gbcFilter.gridx = 4;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtroFechaFin, gbcFilter);

        gbcFilter.gridx = 5;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Por Tipo:"), gbcFilter);

        gbcFilter.gridx = 6;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro2, gbcFilter);

        gbcFilter.gridx = 7;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Salario Mayor A:"), gbcFilter);

        gbcFilter.gridx = 8;
        gbcFilter.gridy = 0;
        panelFiltros.add(filtro3, gbcFilter);

        gbcFilter.gridx = 9;
        gbcFilter.gridy = 0;
        panelFiltros.add(new JLabel("Estado:"), gbcFilter);

        gbcFilter.gridx = 10;
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


    private void aplicarFiltros() throws SQLException {
        String fechaInicio = filtroFechaInicio.getText();
        String fechaFin = filtroFechaFin.getText();
        String tipo = (String) filtro2.getSelectedItem();
        double salario = filtro3.getText().isEmpty() ? 0 : Double.parseDouble(filtro3.getText());
        String estado = (String) filtro4.getSelectedItem();

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

        ConexionMySQL connection = new ConexionMySQL();
        ResultSet rs = connection.obtenerListadoSolicitudes(fechaInicioMySQL, fechaFinMySQL, tipo, salario, estado);

        // Limpiar la tabla antes de agregar nuevos datos
        listadoSolicitudes.setRowCount(0);

        try {
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "No se ha encontrado ninguna solicitud que coincida con los filtros.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            } else {
                while (rs.next()) {
                    String numeroSolicitud = rs.getString("numero_solicitud");
                    String fecha = rs.getString("fecha");
                    String tipoSolicitud = rs.getString("tipo");
                    String nombre = rs.getString("nombre");
                    double salarioSolicitud = rs.getDouble("salario");
                    String direccion = rs.getString("direccion");
                    String estadoSolicitud = rs.getString("estado");

                    // Convertir la fecha al formato dd/MM/yyyy
                    String fechaFormateada = sdf.format(sdfMySQL.parse(fecha));

                    listadoSolicitudes.addRow(new Object[]{numeroSolicitud, fechaFormateada, tipoSolicitud, nombre, salarioSolicitud, direccion, estadoSolicitud});
                }
            }
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar obtener los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}