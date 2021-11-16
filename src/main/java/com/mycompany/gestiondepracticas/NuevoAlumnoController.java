package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
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

public class NuevoAlumnoController implements Initializable {

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
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void aceptar(ActionEvent event) {

        Alumno a = new Alumno();

        a.setId(0L);
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
        s.save(a);
        tr.commit();

        try {
            App.setRoot("profesor");
        } catch (IOException ex) {
            Logger.getLogger(NuevoAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        try {
            App.setRoot("profesor");
        } catch (IOException ex) {
            Logger.getLogger(NuevoAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
