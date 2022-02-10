/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Alumno;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
/**
 * FXML Controller class
 *
 * @author david
 */
public class DatosEmpresaAlumnoController implements Initializable {


    @FXML
    private Label lblHDual;
    @FXML
    private Label lblHFCT;
    @FXML
    private Label lblResDual;
    @FXML
    private Label lblResFCT;
    @FXML
    private Label lblEmpresa;
    @FXML
    private ImageView btnVolver;
    @FXML
    private Label lblTutor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Alumno a = SessionData.getAlumnoActual();
        if (a != null) {
            lblHDual.setText(a.getHoras_dual() + "");
            lblHFCT.setText(a.getHoras_fct() + "");
            double totalDual = 0;
            double totalFCT = 0;
            for (int i = 0; i < a.getActividades().size(); i++) {
                if (a.getActividades().get(i).getTipo_practica().equals("Dual")) {
                    totalDual += a.getActividades().get(i).getTotal_horas();
                } else {
                    totalFCT += a.getActividades().get(i).getTotal_horas();
                }
            }
            lblResDual.setText((a.getHoras_dual() - totalDual)+"");
            lblResFCT.setText((a.getHoras_fct() - totalFCT)+"");
            lblTutor.setText("Tutor asignado: " + a.getEmpresaAsignada().getResponsable());
            lblEmpresa.setText("Empresa: " + a.getEmpresaAsignada().getNombre());
        }

        añadirHandlers();
        
    }    
    
    private void añadirHandlers() {

        btnVolver.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("alumno");
                } catch (IOException ex) {
                    Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
    
}
