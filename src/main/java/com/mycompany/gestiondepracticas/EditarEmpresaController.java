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
    private Button btnVolver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            App.setRoot("gestionEmpresas");
        } catch (IOException ex) {
            Logger.getLogger(EditarEmpresaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
