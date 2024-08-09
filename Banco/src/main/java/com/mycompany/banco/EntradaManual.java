/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author herson
 */
public class EntradaManual extends JInternalFrame {
static {
    try {
        //tema para la ventana
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


    protected JDesktopPane desktopPane;

    public EntradaManual() {
        setTitle("Entrada Manual");
        setSize(800, 520);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);

        // Configurar el layout del contenedor principal
        getContentPane().setLayout(new BorderLayout());

        // Crear el JDesktopPane y agregarlo al centro
        desktopPane = new JDesktopPane();
        getContentPane().add(desktopPane, BorderLayout.CENTER);

        // Crear un JPanel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        // Crear los botones
        JButton botonSubOpcion1 = new JButton("Solicitudes");
        JButton botonSubOpcion2 = new JButton("Movimientos");
        JButton botonSubOpcion3 = new JButton("Consultar Tarjeta");
        JButton botonSubOpcion4 = new JButton("Autorizacion de Tarjeta");
        JButton botonSubOpcion5 = new JButton("Cancelacion de Tarjeta");
        JButton botonSubOpcion7 = new JButton("Estado De Cuenta");

        // Agregar ActionListener a los botones
        botonSubOpcion1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion1();
            }
        });

        botonSubOpcion2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion2();
            }
        });

        botonSubOpcion3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion3();
            }
        });

        botonSubOpcion4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion4();
            }
        });

        botonSubOpcion5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion5();
            }
        });
        
        botonSubOpcion7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion7();
            }
        });

        // Agregar los botones al JPanel
        panelBotones.add(botonSubOpcion1);
        panelBotones.add(botonSubOpcion2);
        panelBotones.add(botonSubOpcion3);
        panelBotones.add(botonSubOpcion4);
        panelBotones.add(botonSubOpcion5);
        panelBotones.add(botonSubOpcion7);

        getContentPane().add(panelBotones, BorderLayout.WEST);

        setVisible(true);
    }

    protected void abrirSubOpcion1() {

    }

    protected void abrirSubOpcion2() {

    }

    protected void abrirSubOpcion3() {

    }

    protected void abrirSubOpcion4() {

    }

    protected void abrirSubOpcion5() {

    }
    
    protected void abrirSubOpcion7() {
    }
}