package com.mycompany.gestiondepracticas;


import models.Alumno;
import models.Actividades;
/**
 *
 * @author chris
 */
public class SessionData {
    
    private static Alumno alumnoActual;
    private static Actividades actividadActual;

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
    
    
    
}
