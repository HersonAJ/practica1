/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import static java.awt.SystemColor.info;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author herson
 */
public class InterfazGrafica extends JFrame {
    static {
    try {
    // tema para la ventana
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private JDesktopPane desktopPane;

    public InterfazGrafica() {
        setTitle("Banco");
        //setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        desktopPane = new JDesktopPane();
        getContentPane().add(desktopPane);

        // Crear un JPanel para la imagen
        JPanel logo = new JPanel();
        ImageIcon banco = null;


        try {
            banco = new ImageIcon(getClass().getResource("/banco.jpg"));
            Image imagenEscalada = banco.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            banco = new ImageIcon(imagenEscalada);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (banco != null) {
            JLabel etiquetaImagen = new JLabel(banco);
            logo.setLayout(new BorderLayout());
            logo.add(etiquetaImagen, BorderLayout.CENTER);

            // Agregar el JPanel con la imagen al JFrame
            logo.setBounds(10, 10, 250, 250);
            desktopPane.add(logo, JDesktopPane.DEFAULT_LAYER);
        } else {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Crear un JPanel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        // Crear los botones
        JButton botonEntradaTexto = new JButton("Entrada de texto");
        JButton botonEntradaManual = new JButton("Entrada manual");

        // Agregar ActionListener a los botones
        botonEntradaTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEntradaTexto();
            }
        });

        botonEntradaManual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEntradaManual();
            }
        });

        // Agregar los botones al JPanel
        panelBotones.add(botonEntradaTexto);
        panelBotones.add(botonEntradaManual);

        // Agregar el JPanel de los botones al JFrame
        panelBotones.setBounds(1, 270, 260, 70);
        desktopPane.add(panelBotones, JDesktopPane.DEFAULT_LAYER);

        // Crear un menú
        JMenuBar menuBar = new JMenuBar();
        JMenu reportes = new JMenu("Reportes");
        menuBar.add(reportes);

        // Asignar la barra de menú al JFrame
        setJMenuBar(menuBar);

        setVisible(true);
    }

    private void abrirEntradaTexto() {
       
        EntradaArchivo entradaTextoFrame = new EntradaArchivo();
        desktopPane.add(entradaTextoFrame);
        entradaTextoFrame.setVisible(true);
        entradaTextoFrame.toFront();
        maximizarVentana(entradaTextoFrame);
        
    }

    private void abrirEntradaManual() {
        EntradaManual entradaManualFrame = new EntradaManual();
        desktopPane.add(entradaManualFrame);
        entradaManualFrame.setVisible(true);
        entradaManualFrame.toFront();
        maximizarVentana(entradaManualFrame);
    }

        private void maximizarVentana(JInternalFrame frame) {
        try {
            frame.setMaximum(true);
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        frame.toFront();
    }
}