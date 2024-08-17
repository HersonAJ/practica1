/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 *
 * @author herson
 */
public class SubOpcion6Reportes extends JInternalFrame {

    protected JDesktopPane desktopPane;

    public SubOpcion6Reportes() {
        setTitle("Reportes");
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
        JButton botonSubOpcion1 = new JButton("Estado de Cuenta");
        JButton botonSubOpcion2 = new JButton("Listado de Tarjetas");
        JButton botonSubOpcion3 = new JButton("Listado de Solicitudes");


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

        // Agregar los botones al JPanel
        panelBotones.add(botonSubOpcion1);
        panelBotones.add(botonSubOpcion2);
        panelBotones.add(botonSubOpcion3);

        getContentPane().add(panelBotones, BorderLayout.WEST);

        setVisible(true);
    }

    protected void abrirSubOpcion1() {
        reportesSubOpcion1EstadoDeCuenta subOpcion1Frame = new reportesSubOpcion1EstadoDeCuenta();
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
        reportesSubOpcion2ListadoDeTarjetas subOpcion2Frame = new reportesSubOpcion2ListadoDeTarjetas();
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
        reportesSubOpcion3ListadoDeSolicitudes subOpcion3Frame = new reportesSubOpcion3ListadoDeSolicitudes();
        desktopPane.add(subOpcion3Frame);
        subOpcion3Frame.setVisible(true);
        try {
            subOpcion3Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion3Frame.toFront();
    }
}