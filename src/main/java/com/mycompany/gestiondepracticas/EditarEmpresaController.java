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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Empresa;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author diegu
 */
public class EditarEmpresaController implements Initializable {

    @FXML
    private TextField lblNombre;
    @FXML
    private TextField lblTelefono;
    @FXML
    private TextField lblEmail;
    @FXML
    private TextField lblResponsable;
    @FXML
    private TextField lblObservaciones;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnBorrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Empresa e = SessionData.getEmpresaActual();

        lblNombre.setText(e.getNombre());
        lblTelefono.setText(e.getTelefono() + "");
        lblEmail.setText(e.getEmail());
        lblResponsable.setText(e.getResponsable());
        lblObservaciones.setText(e.getObservaciones());

    }

    @FXML
    private void guardar(ActionEvent event) {

        Empresa e = SessionData.getEmpresaActual();

        e.setNombre(lblNombre.getText());
        int i = Integer.parseInt(lblTelefono.getText());
        e.setEmail(lblEmail.getText());
        e.setResponsable(lblResponsable.getText());
        e.setObservaciones(lblObservaciones.getText());

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = s.beginTransaction();
        s.update(e);
        tr.commit();
        s.close();
        
        try {
            App.setRoot("gestionEmpresas");
        } catch (IOException ex) {
            Logger.getLogger(EditarEmpresaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void borrar(ActionEvent event) {
        
        Empresa e = SessionData.getEmpresaActual();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = s.beginTransaction();
        s.remove(e);
        tr.commit();
        s.close();

        try {
            App.setRoot("gestionEmpresas");
        } catch (IOException ex) {
            Logger.getLogger(EditarAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
