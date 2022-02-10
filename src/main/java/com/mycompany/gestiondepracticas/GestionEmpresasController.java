/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Empresa;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 */
public class GestionEmpresasController implements Initializable {

    @FXML
    private TableColumn<Empresa, String> cNombre;
    @FXML
    private TableColumn<Empresa, Integer> cTelefono;
    @FXML
    private TableColumn<Empresa, String> cEmail;
    @FXML
    private TableColumn<Empresa, String> cResponsable;
    @FXML
    private TableColumn<Empresa, String> cObservaciones;
    @FXML
    private ImageView btnVolver;
    @FXML
    private TableView<Empresa> tablaEmpresa;
    @FXML
    private ImageView btnAñadir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        cTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        cEmail.setCellValueFactory(new PropertyValueFactory("email"));
        cResponsable.setCellValueFactory(new PropertyValueFactory("responsable"));
        cObservaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));

        Session s = HibernateUtil.getSessionFactory().openSession();

        Query q = s.createQuery("FROM Empresa");
        ArrayList<Empresa> emp = (ArrayList<Empresa>) q.list();
        tablaEmpresa.getItems().addAll(emp);

        s.close();
        
        añadirHandlers();
        
    }

    @FXML
    private void seleccion(MouseEvent event) {
        
        Empresa e = tablaEmpresa.getSelectionModel().getSelectedItem();

        if (e != null) {
            SessionData.setEmpresaActual(e);
            try {
                App.setRoot("editarEmpresa");
            } catch (IOException ex) {
                Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void añadirHandlers() {

        btnVolver.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("profesor");
                } catch (IOException ex) {
                    Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnAñadir.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("anadirEmpresa");
                } catch (IOException ex) {
                    Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
    
    

}
