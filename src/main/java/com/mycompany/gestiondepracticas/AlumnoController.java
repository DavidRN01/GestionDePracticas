package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
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
import models.Actividades;
import models.Alumno;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author DavidRamosNavas
 */
public class AlumnoController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnNuevaTarea;
    @FXML
    private TableView<Actividades> tabla;
    @FXML
    private TableColumn<Actividades, String> colFecha;
    @FXML
    private TableColumn<Actividades, String> colTipo;
    @FXML
    private TableColumn<Actividades, String> colHoras;
    @FXML
    private TableColumn<Actividades, String> colActividad;
    @FXML
    private TableColumn<Actividades, String> colObservaciones;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblApellidos;
    @FXML
    private Label lblDNI;
    @FXML
    private Label lblNacimiento;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblTelefono;
    @FXML
    private Label lblEmpresa;
    @FXML
    private Label lblProfesor;
    @FXML
    private Label lblHorasDual;
    @FXML
    private Label lblHorasFCT;
    @FXML
    private Label lblRestantesDual;
    @FXML
    private Label lblRestantesFCT;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Creamos la lista observable
        ObservableList<Actividades> contenido = FXCollections.observableArrayList();
        tabla.setItems(contenido);

        //Asignamos las columnas
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_practica"));
        colHoras.setCellValueFactory(new PropertyValueFactory<>("total_horas"));
        colActividad.setCellValueFactory(new PropertyValueFactory<>("actividad_realizada"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

        //Iniciamos sesión y sacamos las tareas del alumno
        Session s = HibernateUtil.getSessionFactory().openSession();
        Alumno a = s.load(Alumno.class, SessionData.getAlumnoActual().getId());
        SessionData.setAlumnoActual(a);

        //Añadimos el contenido
        contenido.addAll(a.getActividades());

        //Rellenos los datos de la ficha del alumno
        lblNombre.setText("Nombre: " + a.getNombre());
        lblApellidos.setText("Apellidos: " + a.getApellidos());
        lblDNI.setText("DNI: " + a.getDni());
        lblNacimiento.setText("Fecha de nacimiento: " + a.getFecha_nacimiento());
        lblEmail.setText("Email: " + a.getEmail());
        lblTelefono.setText("Teléfono: " + a.getTelefono());
        lblEmpresa.setText("Empresa: " + a.getEmpresaAsignada().getNombre());
        lblProfesor.setText("Tutor asignado: " + a.getProfesor().getNombre());
        lblHorasDual.setText("Horas totales Dual: " + a.getHoras_dual());
        lblHorasFCT.setText("Horas totales FCT: " + a.getHoras_fct());

    }

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void nuevaTarea(ActionEvent event) {

        SessionData.setActividadActual(null);
        try {
            App.setRoot("nuevaTarea");
        } catch (IOException ex) {
            Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void seleccionar(MouseEvent event) {

        Actividades a = tabla.getSelectionModel().getSelectedItem();

        SessionData.setActividadActual(a);
        try {
            App.setRoot("editarTarea");
        } catch (IOException ex) {
            Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
