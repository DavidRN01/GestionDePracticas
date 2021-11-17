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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Actividades;
import models.Alumno;
import models.Empresa;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author DavidRamosNavas
 */
public class FichaAlumnoController implements Initializable {

    @FXML
    private Button btnEditar;
    @FXML
    private TableColumn<Alumno, Date> cFecha;
    @FXML
    private TableColumn<Alumno, String> cTipo;
    @FXML
    private TableColumn<Alumno, Integer> cHTotales;
    @FXML
    private TableColumn<Alumno, String> cActividad;
    @FXML
    private TableColumn<Alumno, String> cObservaciones;
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
    private Label lblEmpresa;
    @FXML
    private Label lblTutor;
    @FXML
    private Label lblHDual;
    @FXML
    private Label lblHFCT;
    @FXML
    private Label lblResDual;
    @FXML
    private Label lblResFCT;
    @FXML
    private Button btnVolver;
    @FXML
    private TableView<Actividades> tablaAlumno;
    @FXML
    private Label lblNomPerfil;
    @FXML
    private ChoiceBox<String> chEmpresa;

    /**
     * Initializes the controller class.
     */
    Session s = HibernateUtil.getSessionFactory().openSession();

    Alumno a = s.load(Alumno.class, SessionData.getAlumnoActual().getId());

    @FXML
    private TextArea txtObservaciones;
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Query q = s.createQuery("FROM Empresa");
        ArrayList<Empresa> empresas = (ArrayList<Empresa>) q.list();
        empresas.forEach((e) -> chEmpresa.getItems().add(e.getNombre()));
        
        Alumno a = SessionData.getAlumnoActual();
        if (a != null) {
            txtObservaciones.setText(a.getObservaciones());
            chEmpresa.setValue(a.getEmpresaAsignada().getNombre());
        }

        cFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        cTipo.setCellValueFactory(new PropertyValueFactory("tipo_practica"));
        cHTotales.setCellValueFactory(new PropertyValueFactory("total_horas"));
        cActividad.setCellValueFactory(new PropertyValueFactory("actividad_realizada"));
        cObservaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));

        Query listaActividades = s.createQuery("FROM Actividades");
        ArrayList<Actividades> act = (ArrayList<Actividades>) listaActividades.list();
        tablaAlumno.getItems().addAll(act);

        double totalDual = 0;
        double totalFCT = 0;
        for (int i = 0; i < a.getActividades().size(); i++) {
            if (a.getActividades().get(i).getTipo_practica().equals("Dual")) {
                totalDual += a.getActividades().get(i).getTotal_horas();
            } else {
                totalFCT += a.getActividades().get(i).getTotal_horas();
            }
        }

        lblNomPerfil.setText(a.getNombre());

        lblNombre.setText("Nombre: " + a.getNombre());
        lblApellidos.setText("Apellidos: " + a.getApellidos());
        lblDNI.setText("DNI: " + a.getDni());
        lblFechaNac.setText("Nacimiento: " + a.getFecha_nacimiento().toString());
        lblEmail.setText("Email: " + a.getEmail());
        lblTelefono.setText("Telefono: " + a.getTelefono());
        lblEmpresa.setText("Empresa: " + a.getEmpresaAsignada().getNombre());
        lblTutor.setText("Tutor de empresa: " + a.getProfesor().getNombre());
        lblHDual.setText("Horas Dual: " + a.getHoras_dual() + " h");
        lblHFCT.setText("Horas FCT: " + a.getHoras_fct() + " h");
        lblResDual.setText("Horas restantes Dual: " + (a.getHoras_dual() - totalDual) + " totales.");
        lblResFCT.setText("Horas restantes FCT: " + (a.getHoras_fct() - totalFCT) + " totales.");
        

    }

    @FXML
    private void editar(ActionEvent event) {

        try {
            App.setRoot("editarAlumno");
        } catch (IOException ex) {
            Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            App.setRoot("profesor");
        } catch (IOException ex) {
            Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
        
        Query q = s.createQuery("FROM Empresa WHERE nombre = :n");
        q.setParameter("n", chEmpresa.getValue());
        a.setEmpresaAsignada((Empresa) q.list().get(0));
        
        a.setObservaciones(txtObservaciones.getText());
        
        Transaction tr = s.beginTransaction();
        s.update(a);
        tr.commit();
        
        try {
            App.setRoot("profesor");
        } catch (IOException ex) {
            Logger.getLogger(FichaAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
