package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Alumno;
import models.Empresa;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
    @FXML
    private ChoiceBox<String> empresa;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Session s = HibernateUtil.getSessionFactory().openSession();

        Query q = s.createQuery("FROM Empresa");
        ArrayList<Empresa> empresas = (ArrayList<Empresa>) q.list();
        
        empresas.forEach((e) -> empresa.getItems().add(e.getNombre()));
        
        s.close();
        
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
        Query q = s.createQuery("FROM Empresa WHERE nombre = :n");
        q.setParameter("n", empresa.getValue());
        a.setEmpresaAsignada((Empresa) q.list().get(0));
        
        java.util.Date ahora = new java.util.Date();
        java.sql.Date fecha = new java.sql.Date(ahora.getTime());
        a.setFecha_creacion(fecha);
        
        a.setProfesor(SessionData.getProfesorActual());
        
        Transaction tr = s.beginTransaction();
        s.save(a);
        tr.commit();
        s.close();

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
