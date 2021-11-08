package models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author chris
 */

@Entity
@Table (name="actividades")
public class Actividades implements Serializable {
    
    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Long id;
    private LocalDate fecha;
    private String tipo_practica;
    private double total_horas;
    private String actividad_realizada;
    private String observaciones;
    private Date fecha_creacion;
    
    @ManyToOne
    @JoinColumn(name="alumno_id")
    private Alumno alumno;

    public Actividades() {
    }

    public Actividades(Long id, LocalDate fecha, String tipo_practica, double total_horas, String actividad_realizada, String observaciones, Date fecha_creacion, Alumno alumno) {
        this.id = id;
        this.fecha = fecha;
        this.tipo_practica = tipo_practica;
        this.total_horas = total_horas;
        this.actividad_realizada = actividad_realizada;
        this.observaciones = observaciones;
        this.fecha_creacion = fecha_creacion;
        this.alumno = alumno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipo_practica() {
        return tipo_practica;
    }

    public void setTipo_practica(String tipo_practica) {
        this.tipo_practica = tipo_practica;
    }

    public double getTotal_horas() {
        return total_horas;
    }

    public void setTotal_horas(double total_horas) {
        this.total_horas = total_horas;
    }

    public String getActividad_realizada() {
        return actividad_realizada;
    }

    public void setActividad_realizada(String actividad_realizada) {
        this.actividad_realizada = actividad_realizada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    @Override
    public String toString() {
        return "Actividades{" + "id=" + id + ", fecha=" + fecha + ", tipo_practica=" + tipo_practica + ", total_horas=" + total_horas + ", actividad_realizada=" + actividad_realizada + ", observaciones=" + observaciones + ", fecha_creacion=" + fecha_creacion + ", alumno=" + alumno + '}';
    }
    
    
}
