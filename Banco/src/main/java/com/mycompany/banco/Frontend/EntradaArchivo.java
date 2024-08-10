/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author herson
 */
public class EntradaArchivo extends JInternalFrame {
    
    protected JButton seleccionarArchivo;
    protected JButton seleccionarRuta;
    protected JButton cargarArchivo;
    protected JTextField rutaArchivo;
    protected JTextField rutaReporte;
    protected JTextArea logArea;

    public EntradaArchivo() {
        
        setTitle("Carga de Archivo");
        setSize(700, 500);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);
        
        JPanel cargaArchivo = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        seleccionarArchivo = new JButton("Seleccionar Archivo");
        cargaArchivo.add(seleccionarArchivo, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        rutaArchivo = new JTextField(30);
        cargaArchivo.add(rutaArchivo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        seleccionarRuta = new JButton("Seleccionar Ruta de Informe");
        cargaArchivo.add(seleccionarRuta, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        rutaReporte = new JTextField(30);
        cargaArchivo.add(rutaReporte, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        cargarArchivo = new JButton("Cargar Archivo");
        cargaArchivo.add(cargarArchivo, gbc);
        
        add(cargaArchivo, BorderLayout.CENTER);

        logArea = new JTextArea(10, 10);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        add(logScrollPane, BorderLayout.SOUTH);

        // Agregar ActionListener a los botones
        seleccionarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarArchivoAction();
            }
        });
        
        seleccionarRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarRutaAction();
            }
        });

        cargarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivoAction();
            }
        });
    }

    private void seleccionarArchivoAction() {

    }

    private void seleccionarRutaAction() {

    }

    private void cargarArchivoAction() {
    }

}

