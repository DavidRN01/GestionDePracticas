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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Alumno;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EditarAlumnoController implements Initializable {

    @FXML
    private TextField labelNombre;
    @FXML
    private TextField labelApellidos;
    @FXML
    private TextField labelCorreo;
    @FXML
    private TextField labelPass;
    @FXML
    private TextField labelDNI;
    @FXML
    private DatePicker dateFecha;
    @FXML
    private TextField labelTelefono;
    @FXML
    private TextField labelDual;
    @FXML
    private TextField labelFCT;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnBorrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Alumno a = SessionData.getAlumnoActual();
        if (a != null) {
            java.sql.Date fecha = a.getFecha_nacimiento();
            LocalDate localDateNacimiento = fecha.toLocalDate();

            labelNombre.setText(a.getNombre());
            labelApellidos.setText(a.getApellidos());
            labelCorreo.setText(a.getEmail());
            labelPass.setText(a.getContraseña());
            labelDNI.setText(a.getDni());
            dateFecha.setValue(localDateNacimiento);
            labelTelefono.setText("" + a.getTelefono());
            labelDual.setText("" + a.getHoras_dual());
            labelFCT.setText("" + a.getHoras_fct());

        }
    }

    @FXML
    private void aceptar(ActionEvent event) {

        Alumno a = SessionData.getAlumnoActual();

        a.setNombre(labelNombre.getText());
        a.setApellidos(labelApellidos.getText());
        a.setEmail(labelCorreo.getText());
        a.setContraseña(labelPass.getText());
        a.setDni(labelDNI.getText());
        a.setFecha_nacimiento(java.sql.Date.valueOf(dateFecha.getValue()));

        a.setTelefono(Integer.parseInt(labelTelefono.getText()));
        a.setHoras_dual(Double.parseDouble(labelDual.getText()));
        a.setHoras_fct(Double.parseDouble(labelFCT.getText()));

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = s.beginTransaction();
        s.update(a);
        tr.commit();
        s.close();

        try {
            App.setRoot("fichaAlumno");
        } catch (IOException ex) {
            Logger.getLogger(EditarAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void borrar(ActionEvent event) {

        Alumno a = SessionData.getAlumnoActual();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = s.beginTransaction();
        s.remove(a);
        tr.commit();
        s.close();

        try {
            App.setRoot("profesor");
        } catch (IOException ex) {
            Logger.getLogger(EditarAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
