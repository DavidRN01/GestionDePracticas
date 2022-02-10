/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Actividades;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 */
public class NuevaTareaController implements Initializable {

    @FXML
    private Spinner<Double> horas;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private ChoiceBox<String> chooser;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ImageView btnAceptar;
    @FXML
    private ImageView btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Inicializo el chooser con las dos opciones, pongo la primera por defecto
        chooser.getItems().addAll("Dual", "FCT");
        chooser.getSelectionModel().selectFirst();

        //Inicializo el spinner
        SpinnerValueFactory svf = new DoubleSpinnerValueFactory(0, 24, 0, 0.25);
        horas.setValueFactory(svf);

        añadirHandlers();

    }

    private void añadirHandlers() {

        btnCancelar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("alumno");
                } catch (IOException ex) {
                    Logger.getLogger(NuevaTareaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //Creo la actividad que voy añadir
                Actividades a = new Actividades();

                //Al alumno le pongo el que tenga la sesión iniciada
                a.setAlumno(SessionData.getAlumnoActual());

                //Creo la fecha actual y se la paso
                java.util.Date ahora = new java.util.Date();
                java.sql.Date fecha = new java.sql.Date(ahora.getTime());
                a.setFecha_creacion(fecha);

                //Le paso el valor del Date Picker
                LocalDate localDate = datePicker.getValue();
                a.setFecha(localDate);

                //Le paso los valores de los cajetines de texto
                a.setObservaciones(txtObservaciones.getText());
                a.setActividad_realizada(txtNombre.getText());

                //Le paso el valor del chooser
                a.setTipo_practica(chooser.getValue());

                //Le paso el valor del spinner
                a.setTotal_horas(horas.getValue());

                //Iniciamos la transacción y la guardamos
                Session s = HibernateUtil.getSessionFactory().openSession();
                Transaction tr = s.beginTransaction();
                s.save(a);
                tr.commit();
                s.close();
                try {
                    App.setRoot("alumno");
                } catch (IOException ex) {
                    Logger.getLogger(NuevaTareaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

}
