/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Actividades;
import models.Alumno;
import models.Empresa;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 */
public class FichaAlumnoController implements Initializable {

    @FXML
    private ImageView btnEditar;
    @FXML
    private TableColumn<Actividades, Date> cFecha;
    @FXML
    private TableColumn<Actividades, Integer> cHTotales;
    @FXML
    private TableColumn<Actividades, String> cActividad;
    @FXML
    private TableColumn<Actividades, String> cObservaciones;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblApellidos;
    @FXML
    private Label lblDNI;
    @FXML
    private Label lblFechaNac;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblTelefono;
    @FXML
    private ImageView btnVolver;
    @FXML
    private TableView<Actividades> tablaAlumno;
    @FXML
    private ImageView lblImagen;
    @FXML
    private Label lblDatos;
    @FXML
    private TableColumn<Actividades, String> cTipo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Session s = HibernateUtil.getSessionFactory().openSession();

        Alumno a = SessionData.getAlumnoActual();

        cFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        cTipo.setCellValueFactory(new PropertyValueFactory("tipo_practica"));
        cHTotales.setCellValueFactory(new PropertyValueFactory("total_horas"));
        cActividad.setCellValueFactory(new PropertyValueFactory("actividad_realizada"));
        cObservaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));

        tablaAlumno.getItems().addAll(a.getActividades());

        double totalDual = 0;
        double totalFCT = 0;
        for (int i = 0; i < a.getActividades().size(); i++) {
            if (a.getActividades().get(i).getTipo_practica().equals("Dual")) {
                totalDual += a.getActividades().get(i).getTotal_horas();
            } else {
                totalFCT += a.getActividades().get(i).getTotal_horas();
            }
        }

        lblNombre.setText(a.getNombre());
        lblApellidos.setText(a.getApellidos());
        lblDNI.setText(a.getDni());
        lblFechaNac.setText(a.getFecha_nacimiento().toString());
        lblEmail.setText(a.getEmail());
        lblTelefono.setText("" + a.getTelefono());

        s.close();

        añadirHandlers();

    }

    private void añadirHandlers() {

        btnVolver.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("profesor");
                } catch (IOException ex) {
                    Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnEditar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("editarAlumno");
                } catch (IOException ex) {
                    Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        lblDatos.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("datosEmpresa");
                } catch (IOException ex) {
                    Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });


    }

}
