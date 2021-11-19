package com.mycompany.gestiondepracticas;


import models.Alumno;
import models.Actividades;
import models.Empresa;
import models.Profesor;
/**
 *
 * @author chris
 */
public class SessionData {
    
    private static Alumno alumnoActual;
    private static Actividades actividadActual;
    private static Profesor profesorActual;
    private static Empresa empresaActual;

    public static Empresa getEmpresaActual() {
        return empresaActual;
    }

    public static void setEmpresaActual(Empresa empresaActual) {
        SessionData.empresaActual = empresaActual;
    }
    
    public static Alumno getAlumnoActual() {
        return alumnoActual;
    }

    public static void setAlumnoActual(Alumno alumnoActual) {
        SessionData.alumnoActual = alumnoActual;
    }

    public static Actividades getActividadActual() {
        return actividadActual;
    }

    public static void setActividadActual(Actividades actividadActual) {
        SessionData.actividadActual = actividadActual;
    }

    public static Profesor getProfesorActual() {
        return profesorActual;
    }

    public static void setProfesorActual(Profesor profesorActual) {
        SessionData.profesorActual = profesorActual;
    }
    
    
    
}
