package com.mycompany.gestiondepracticas;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import models.Actividades;
import models.Alumno;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 */
public class AlumnoController implements Initializable {

    @FXML
    private ImageView btnSalir;
    @FXML
    private ImageView btnNuevaTarea;
    @FXML
    private TableView<Actividades> tabla;
    @FXML
    private TableColumn<Actividades, Date> colFecha;
    @FXML
    private TableColumn<Actividades, String> colTipo;
    @FXML
    private TableColumn<Actividades, Double> colHoras;
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
    private ImageView lblImagen;
    @FXML
    private Label lblDatos;

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

        //Calculamos las horas que ya lleva realizadas en cada módulo
        double totalDual = 0;
        double totalFCT = 0;
        for (int i = 0; i < a.getActividades().size(); i++) {
            if (a.getActividades().get(i).getTipo_practica().equals("Dual")) {
                totalDual += a.getActividades().get(i).getTotal_horas();
            } else {
                totalFCT += a.getActividades().get(i).getTotal_horas();
            }
        }

        //Rellenos los datos de la ficha del alumno
        lblNombre.setText(a.getNombre());
        lblApellidos.setText(a.getApellidos());
        lblDNI.setText(a.getDni());
        lblNacimiento.setText(""+a.getFecha_nacimiento());
        lblEmail.setText(a.getEmail());
        lblTelefono.setText("" + a.getTelefono());

        s.close();

        añadirHandlers();

    }

    private void añadirHandlers() {

        btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });

        btnNuevaTarea.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                SessionData.setActividadActual(null);
                try {
                    App.setRoot("nuevaTarea");
                } catch (IOException ex) {
                    Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        lblDatos.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    App.setRoot("datosEmpresaAlumno");
                } catch (IOException ex) {
                    Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @FXML
    private void seleccionar(MouseEvent event) {

        Actividades a = tabla.getSelectionModel().getSelectedItem();

        if (a != null) {
            SessionData.setActividadActual(a);
            try {
                App.setRoot("editarTarea");
            } catch (IOException ex) {
                Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
