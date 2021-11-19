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
import models.Actividades;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 */
public class EditarTareaController implements Initializable {

    @FXML
    private Spinner<Double> horas;
    @FXML
    private TextField txtNombreTarea;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private ChoiceBox<String> choiceTipo;
    @FXML
    private DatePicker dateFecha;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnBorrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Actividades a = SessionData.getActividadActual();
        if (a != null) {
            txtNombreTarea.setText(a.getActividad_realizada());
            txtObservaciones.setText(a.getObservaciones());
            choiceTipo.setValue(a.getTipo_practica());
            SpinnerValueFactory svf = new DoubleSpinnerValueFactory(0,24,a.getTotal_horas(),0.25);
            horas.setValueFactory(svf);
            dateFecha.setValue(a.getFecha());
     
        }

    }

    @FXML
    private void aceptar(ActionEvent event) {
        
        //Asigno la actividad que voy a editar
        Actividades a = SessionData.getActividadActual();

        //Le paso los valores de los cajetines de texto
        a.setObservaciones(txtObservaciones.getText());
        a.setActividad_realizada(txtNombreTarea.getText());


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

    @FXML
    private void borrar(ActionEvent event) {
        
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

}
