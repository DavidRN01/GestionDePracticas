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
 */
public class EditarTareaController implements Initializable {

    @FXML
    private TextField horas;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private ImageView btnBorrar;
    @FXML
    private ImageView datosPersonales;
    @FXML
    private TextField txtNombre;
    @FXML
    private ImageView btnGuardar;
    @FXML
    private TextField chooser;
    @FXML
    private TextField datePicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Actividades a = SessionData.getActividadActual();
        if (a != null) {
            txtNombre.setText(a.getActividad_realizada());
            txtObservaciones.setText(a.getObservaciones());
            chooser.setText("" + a.getTipo_practica());
            SpinnerValueFactory svf = new DoubleSpinnerValueFactory(0, 24, a.getTotal_horas(), 0.25);
            horas.setText("" + a.getTotal_horas());
            datePicker.setText(""+a.getFecha());

        }

        añadirHandlers();

    }

    private void añadirHandlers() {

        btnGuardar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //Asigno la actividad que voy a editar
                Actividades a = SessionData.getActividadActual();

                //Le paso los valores de los cajetines de texto
                a.setObservaciones(txtObservaciones.getText());
                a.setActividad_realizada(txtNombre.getText());

                //Iniciamos la transacción y la guardamos
                Session s = HibernateUtil.getSessionFactory().openSession();
                Transaction tr = s.beginTransaction();
                s.update(a);
                tr.commit();
                s.close();

                try {
                    App.setRoot("alumno");
                } catch (IOException ex) {
                    Logger.getLogger(NuevaTareaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnBorrar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //Asigno la actividad que voy a borrar
                Actividades a = SessionData.getActividadActual();

                //Iniciamos la transacción y la borramos
                Session s = HibernateUtil.getSessionFactory().openSession();
                Transaction tr = s.beginTransaction();
                s.remove(a);
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
