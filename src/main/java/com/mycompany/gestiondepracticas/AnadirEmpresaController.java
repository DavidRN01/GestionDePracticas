/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Alumno;
import models.Empresa;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 */
public class AnadirEmpresaController implements Initializable {

    @FXML
    private ImageView btnGuardar;
    @FXML
    private ImageView btnSalir;
    @FXML
    private TextField lblEmpresa;
    @FXML
    private TextField lblTelefono;
    @FXML
    private TextField lblEmail;
    @FXML
    private TextField lblResponsable;
    @FXML
    private TextField lblObservaciones;
    @FXML
    private ImageView datosPersonales;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        añadirHandlers();
        
    }

    private void añadirHandlers() {

        btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("gestionEmpresas");
                } catch (IOException ex) {
                    Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnGuardar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Empresa e = new Empresa();

                java.util.Date ahora = new java.util.Date();
                java.sql.Date fecha = new java.sql.Date(ahora.getTime());
                e.setFecha_creacion(fecha);

                int i = Integer.parseInt(lblTelefono.getText());
                e.setNombre(lblEmpresa.getText());
                e.setTelefono(i);
                e.setEmail(lblEmail.getText());
                e.setResponsable(lblResponsable.getText());
                e.setObservaciones(lblObservaciones.getText());

                Session s = HibernateUtil.getSessionFactory().openSession();
                Transaction tr = s.beginTransaction();
                s.save(e);
                tr.commit();
                s.close();

                try {
                    App.setRoot("gestionEmpresas");
                } catch (IOException ex) {
                    Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

}
