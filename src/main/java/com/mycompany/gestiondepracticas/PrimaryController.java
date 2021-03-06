package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import models.Profesor;
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
        Query qa = s.createQuery("FROM Alumno a WHERE a.email=:correo AND a.contraseña=:pass ");
        qa.setParameter("correo", txtEmail.getText());
        String passMD5A = encriptar(txtPassword.getText());
        qa.setParameter("pass", passMD5A);
        
        if(qa.list().size()>0){
            
            Alumno a = (Alumno) qa.list().get(0);
            
            SessionData.setAlumnoActual(a);
            
            s.close();
            try {
                App.setRoot("alumno");
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            
            Query qp = s.createQuery("FROM Profesor p WHERE p.email=:correo AND p.contraseña=:pass ");
            qp.setParameter("correo", txtEmail.getText());
            String passMD5P = encriptar(txtPassword.getText());
            qp.setParameter("pass", passMD5P);
            
            if(qp.list().size()>0){
            
                Profesor p = (Profesor) qp.list().get(0);

                SessionData.setProfesorActual(p);
                s.close();
                try {
                    App.setRoot("profesor");
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            } else {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setContentText("El correo "+txtEmail.getText() 
                    + " no existe o la contraseña es incorrecta");
                alerta.showAndWait();
                s.close();
               
            }
        }
        
    }
    
    @FXML
    private void forgotPassword(ActionEvent event) {
    }
    
    private String encriptar(String pass) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(pass.getBytes(),0,pass.length());
            return new BigInteger(1,m.digest()).toString(16); 
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}