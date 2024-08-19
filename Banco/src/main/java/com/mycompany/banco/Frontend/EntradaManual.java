/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco.Frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


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

        
        desktopPane = new JDesktopPane();
        getContentPane().add(desktopPane, BorderLayout.CENTER);

        
        JMenuBar menuBar = new JMenuBar();

        
        JMenu menuOpciones = new JMenu("Opciones");
        JMenu menuReportes = new JMenu("Reportes");

        //elementos del menú
        JMenuItem menuItemSolicitudes = new JMenuItem("Solicitudes");
        JMenuItem menuItemAutorizacion = new JMenuItem("Autorización de Tarjeta");
        JMenuItem menuItemConsultar = new JMenuItem("Consultar Tarjeta");
        JMenuItem menuItemMovimientos = new JMenuItem("Movimientos");
        JMenuItem menuItemCancelacion = new JMenuItem("Cancelación de Tarjeta");

        // elementos del menú Reportes
        JMenuItem menuItemEstadoDeCuenta = new JMenuItem("Estado de Cuenta");
        JMenuItem menuItemListadoDeTarjetas = new JMenuItem("Listado de Tarjetas");
        JMenuItem menuItemListadoDeSolicitudes = new JMenuItem("Listado de Solicitudes");

        // Agregar ActionListener a los elementos del menú
        menuItemSolicitudes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    abrirSubOpcion1();
                } catch (SQLException ex) {
                    Logger.getLogger(EntradaManual.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        menuItemAutorizacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion2();
            }
        });

        menuItemConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion3();
            }
        });

        menuItemMovimientos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion4();
            }
        });

        menuItemCancelacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcion5();
            }
        });

        menuItemEstadoDeCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcionEstadoDeCuenta();
            }
        });

        menuItemListadoDeTarjetas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcionListadoDeTarjetas();
            }
        });

        menuItemListadoDeSolicitudes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSubOpcionListadoDeSolicitudes();
            }
        });

       
        menuOpciones.add(menuItemSolicitudes);
        menuOpciones.add(menuItemAutorizacion);
        menuOpciones.add(menuItemConsultar);
        menuOpciones.add(menuItemMovimientos);
        menuOpciones.add(menuItemCancelacion);

        
        menuReportes.add(menuItemEstadoDeCuenta);
        menuReportes.add(menuItemListadoDeTarjetas);
        menuReportes.add(menuItemListadoDeSolicitudes);

        // Agregar los menús a la barra de menú
        menuBar.add(menuOpciones);
        menuBar.add(menuReportes); 

       
        setJMenuBar(menuBar);

        setVisible(true);
    }

    protected void abrirSubOpcion1() throws SQLException {
        SubOpcion1Solicitud subOpcion1Frame = new SubOpcion1Solicitud();
        desktopPane.add(subOpcion1Frame);
        subOpcion1Frame.setVisible(true);
        maximizarVentana(subOpcion1Frame);
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
        maximizarVentana(subOpcion2Frame);
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
        maximizarVentana(subOpcion3Frame);
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
        maximizarVentana(subOpcion4Frame);
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
        maximizarVentana(subOpcion5Frame);
        try {
            subOpcion5Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion5Frame.toFront();
    }

    protected void abrirSubOpcionEstadoDeCuenta() {
        reportesSubOpcion1EstadoDeCuenta subOpcion1Frame = new reportesSubOpcion1EstadoDeCuenta();
        desktopPane.add(subOpcion1Frame);
        subOpcion1Frame.setVisible(true);
        maximizarVentana(subOpcion1Frame);
        //subOpcion1Frame.setSelected(true);
        subOpcion1Frame.toFront();
    }

    protected void abrirSubOpcionListadoDeTarjetas() {
        reportesSubOpcion2ListadoDeTarjetas subOpcion2Frame = new reportesSubOpcion2ListadoDeTarjetas();
        desktopPane.add(subOpcion2Frame);
        subOpcion2Frame.setVisible(true);
        maximizarVentana(subOpcion2Frame);
        try {
            subOpcion2Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion2Frame.toFront();
    }

    protected void abrirSubOpcionListadoDeSolicitudes() {
        reportesSubOpcion3ListadoDeSolicitudes subOpcion3Frame = new reportesSubOpcion3ListadoDeSolicitudes();
        desktopPane.add(subOpcion3Frame);
        subOpcion3Frame.setVisible(true);
        maximizarVentana(subOpcion3Frame);
        try {
            subOpcion3Frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        subOpcion3Frame.toFront();
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