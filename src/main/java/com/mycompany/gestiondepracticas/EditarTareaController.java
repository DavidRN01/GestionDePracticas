/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondepracticas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Actividades;

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
     
        }

    }

}
