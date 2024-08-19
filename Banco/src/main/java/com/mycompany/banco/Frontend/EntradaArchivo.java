/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import com.mycompany.banco.Backend.operadorDeArchivo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


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
    protected JSpinner intervaloSpinner;
    public static String rutaSeleccionada;

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

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel intervaloLabel = new JLabel("Intervalo (ms):");
        cargaArchivo.add(intervaloLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        intervaloSpinner = new JSpinner(new SpinnerNumberModel(1000, 100, 10000, 100));
        cargaArchivo.add(intervaloSpinner, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            rutaArchivo.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void seleccionarRutaAction() {
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = directoryChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            rutaSeleccionada = directoryChooser.getSelectedFile().getAbsolutePath();
            rutaReporte.setText(rutaSeleccionada);
        }
    }

    private void cargarArchivoAction() {
        new Thread(() -> {
            try {
                File file = new File(rutaArchivo.getText());
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String linea;
                int intervalo = (int) intervaloSpinner.getValue();
                operadorDeArchivo ejecutor = new operadorDeArchivo(logArea);
                while ((linea = reader.readLine()) != null) {
                    logArea.append("Leyendo:..................." + linea + "\n");
                    ejecutor.ejecutarLinea(linea);
                    Thread.sleep(intervalo);
                }
                reader.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

