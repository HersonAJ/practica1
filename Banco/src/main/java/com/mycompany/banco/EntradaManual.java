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
        JButton botonSubOpcion2 = new JButton("Autorizacion de Tarjeta");
        JButton botonSubOpcion3 = new JButton("Consultar Tarjeta");
        JButton botonSubOpcion4 = new JButton("Movimientos");
        JButton botonSubOpcion5 = new JButton("Cancelacion de Tarjeta");
        JButton botonSubOpcion6 = new JButton("Reportes");

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
        
        botonSubOpcion6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion6();
            }
        });

        // Agregar los botones al JPanel
        panelBotones.add(botonSubOpcion1);
        panelBotones.add(botonSubOpcion2);
        panelBotones.add(botonSubOpcion3);
        panelBotones.add(botonSubOpcion4);
        panelBotones.add(botonSubOpcion5);
        panelBotones.add(botonSubOpcion6);

        getContentPane().add(panelBotones, BorderLayout.WEST);

        setVisible(true);
    }

    protected void abrirSubOpcion1() {
        SubOpcion1Solicitud subOpcion1Frame = new SubOpcion1Solicitud();
        desktopPane.add(subOpcion1Frame);
        subOpcion1Frame.setVisible(true);
        try {
            subOpcion1Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion1Frame.toFront();
    }

    protected void abrirSubOpcion2() {
        SubOpcion2AutorizacionDeTarjeta subOpcion2Frame = new SubOpcion2AutorizacionDeTarjeta();
        desktopPane.add(subOpcion2Frame);
        subOpcion2Frame.setVisible(true);
        try {
            subOpcion2Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion2Frame.toFront();
    }

    protected void abrirSubOpcion3() {
        SubOpcion3ConsultarTarjeta subOpcion3Frame = new SubOpcion3ConsultarTarjeta();
        desktopPane.add(subOpcion3Frame);
        subOpcion3Frame.setVisible(true);
        try {
            subOpcion3Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion3Frame.toFront();
    }

    protected void abrirSubOpcion4() {
        SubOpcion4Movimientos subOpcion4Frame = new SubOpcion4Movimientos();
        desktopPane.add(subOpcion4Frame);
        subOpcion4Frame.setVisible(true);
        try {
            subOpcion4Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion4Frame.toFront();
    }

    protected void abrirSubOpcion5() {
        SubOpcion5CancelacionDeTarjeta subOpcion5Frame = new SubOpcion5CancelacionDeTarjeta();
        desktopPane.add(subOpcion5Frame);
        subOpcion5Frame.setVisible(true);
        try {
            subOpcion5Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion5Frame.toFront();
    }
    
    protected void abrirSubOpcion6() {
        SubOpcion6Reportes subOpcion7Frame = new SubOpcion6Reportes();
        desktopPane.add(subOpcion7Frame);
        subOpcion7Frame.setVisible(true);
        try {
            subOpcion7Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion7Frame.toFront();
    }
}