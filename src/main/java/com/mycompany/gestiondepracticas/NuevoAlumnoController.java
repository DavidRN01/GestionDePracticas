package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private ImageView btnAceptar;
    @FXML
    private ImageView btnCancelar;
    @FXML
    private ChoiceBox<String> empresa;
    @FXML
    private ImageView datosPersonales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Session s = HibernateUtil.getSessionFactory().openSession();

        Query q = s.createQuery("FROM Empresa");
        ArrayList<Empresa> empresas = (ArrayList<Empresa>) q.list();

        empresas.forEach((e) -> empresa.getItems().add(e.getNombre()));

        s.close();

        añadirHandlers();

    }

    private void añadirHandlers() {

        btnCancelar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("profesor");
                } catch (IOException ex) {
                    Logger.getLogger(NuevoAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Alumno a = new Alumno();

                a.setId(0L);
                a.setNombre(labelNombre.getText());
                a.setApellidos(labelApellidos.getText());
                a.setEmail(labelCorreo.getText());
                
                String passMD5 = encriptar(labelPass.getText());
                a.setContraseña(passMD5);
                
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
        });

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
