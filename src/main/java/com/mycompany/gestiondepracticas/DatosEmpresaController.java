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
import javafx.event.EventHandler;
import org.hibernate.query.Query;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
 * @author david
 */
public class DatosEmpresaController implements Initializable {

    @FXML
    private Label lblHDual;
    @FXML
    private Label lblHFCT;
    @FXML
    private Label lblResDual;
    @FXML
    private Label lblResFCT;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private ChoiceBox<String> chEmpresa;
    @FXML
    private ImageView btnGuardar;
    @FXML
    private ImageView btnVolver;
    @FXML
    private Label lblTutor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Session s = HibernateUtil.getSessionFactory().openSession();

        Query q = s.createQuery("FROM Empresa");
        ArrayList<Empresa> empresas = (ArrayList<Empresa>) q.list();
        empresas.forEach((e) -> chEmpresa.getItems().add(e.getNombre()));

        Alumno a = SessionData.getAlumnoActual();
        if (a != null) {
            txtObservaciones.setText(a.getObservaciones());
            chEmpresa.setValue(a.getEmpresaAsignada().getNombre());
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
        }

        añadirHandlers();

    }

    private void añadirHandlers() {

        btnVolver.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("fichaAlumno");
                } catch (IOException ex) {
                    Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnGuardar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Session s = HibernateUtil.getSessionFactory().openSession();
                Alumno a = s.load(Alumno.class, SessionData.getAlumnoActual().getId());
                Query q = s.createQuery("FROM Empresa WHERE nombre = :n");
                q.setParameter("n", chEmpresa.getValue());
                a.setEmpresaAsignada((Empresa) q.list().get(0));

                a.setObservaciones(txtObservaciones.getText());

                Transaction tr = s.beginTransaction();
                s.update(a);
                tr.commit();
                s.close();

                try {
                    App.setRoot("profesor");
                } catch (IOException ex) {
                    Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

}
