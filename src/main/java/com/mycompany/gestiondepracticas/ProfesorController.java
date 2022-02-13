package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Alumno;
import models.Profesor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 */
public class ProfesorController implements Initializable {

    @FXML
    private ImageView btnSalir;
    @FXML
    private ImageView btnAñadir;
    @FXML
    private Label btnGestionar;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelApellidos;
    @FXML
    private Label labelEmail;
    @FXML
    private TableView<Alumno> tabla;
    @FXML
    private TableColumn<Alumno, String> colDNI;
    @FXML
    private TableColumn<Alumno, String> colNombre;
    @FXML
    private TableColumn<Alumno, String> colApellidos;
    @FXML
    private TableColumn<Alumno, String> colEmail;
    @FXML
    private ImageView lblImagen;

    /**
     * Initializes the controller class.
     */
    @Override
    // https://openjfx.io/javadoc/11/javafx.controls/javafx/scene/control/TableView.html
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Alumno> contenido = FXCollections.observableArrayList();
        tabla.setItems(contenido);

        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        Session s = HibernateUtil.getSessionFactory().openSession();
        Profesor p = s.load(Profesor.class, SessionData.getProfesorActual().getId());
        
        Query q = s.createQuery("FROM Alumno al WHERE al.profesor.id = :id");
        q.setParameter("id", p.getId());
        
        SessionData.setProfesorActual(p);

        contenido.addAll(q.list());

        labelNombre.setText(p.getNombre());
        labelApellidos.setText(p.getApellidos());
        labelEmail.setText(p.getEmail());

        s.close();

        añadirHandlers();

    }

    @FXML
    private void seleccionar(MouseEvent event) {
        Alumno a = tabla.getSelectionModel().getSelectedItem();
        SessionData.setAlumnoActual(a);
        if (a != null) {
            SessionData.setAlumnoActual(a);
            try {
                App.setRoot("fichaAlumno");
            } catch (IOException ex) {
                Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void añadirHandlers() {

        btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });

        btnGestionar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("gestionEmpresas");
                } catch (IOException ex) {
                    Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnAñadir.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("nuevoAlumno");
                } catch (IOException ex) {
                    Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

}
