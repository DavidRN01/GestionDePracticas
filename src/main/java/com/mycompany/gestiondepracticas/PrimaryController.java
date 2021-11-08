package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Alumno;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PrimaryController {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Hyperlink linkPassword;

    @FXML
    private void login(ActionEvent event) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("FROM Alumno a WHERE a.email=:correo AND a.contraseña=:pass ");
        q.setParameter("correo", txtEmail.getText());
        q.setParameter("pass", txtPassword.getText());
        
        if(q.list().size()>0){
            
            Alumno a = (Alumno) q.list().get(0);
            
            SessionData.setAlumnoActual(a);
            
            try {
                App.setRoot("alumno");
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

    @FXML
    private void forgotPassword(ActionEvent event) {
    }
}
