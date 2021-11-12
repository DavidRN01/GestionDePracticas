
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Alumno;
import models.Profesor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author DavidRamosNavas
 */
public class ProfesorController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnGestionar;
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
    private Label labelImagen;

    
    /**
     * Initializes the controller class.
     */
    @Override
    // https://openjfx.io/javadoc/11/javafx.controls/javafx/scene/control/TableView.html
    public void initialize(URL url, ResourceBundle rb) {
        //Consigo la lista de alumnos del profesor
//        ObservableList<Alumno> contenido = FXCollections.observableArrayList();
//        tabla.setItems(contenido);
        
        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        Session s = HibernateUtil.getSessionFactory().openSession();

        Query q = s.createQuery("FROM Alumno");
        ArrayList<Alumno> alumno = (ArrayList<Alumno>) q.list();
        tabla.getItems().addAll(alumno);
        
        Profesor p = s.load(Profesor.class, SessionData.getProfesorActual().getId());
        SessionData.setProfesorActual(p);
        
        labelImagen.setText(p.getNombre() );
        labelNombre.setText( "Nombre: "+p.getNombre() );
        labelApellidos.setText("Apellidos: "+p.getApellidos() );
        labelEmail.setText("Email: "+p.getEmail() );
    }    

     private void actualizarTabla() throws HibernateException {
        
        //abro una sesion y hago una query para imprimir todos los datos de la carta
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("FROM Alumno");
        ArrayList<Alumno> alumno = (ArrayList<Alumno>) q.list();
        tabla.getItems().addAll(alumno);
        s.close();
    }
    
    @FXML
    private void seleccionar(MouseEvent event) {
        Alumno a = tabla.getSelectionModel().getSelectedItem();
        SessionData.setAlumnoActual(a);
        try {
            App.setRoot("fichaAlumno");
        } catch (IOException ex) {
            Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void añadir(ActionEvent event) {
        try {
            App.setRoot("nuevoAlumno");
        } catch (IOException ex) {
            Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void gestionar(ActionEvent event) {
        try {
            App.setRoot("gestionEmpresas");
        } catch (IOException ex) {
            Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
