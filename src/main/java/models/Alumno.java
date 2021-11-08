package models;

import java.io.Serializable;
import java.util.List;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import models.Actividades;
import models.Empresa;

/**
 *
 * @author DavidRamosNavas
 */

@Entity
@Table(name="alumno")
public class Alumno implements Serializable {
    
    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Long id;
    private String nombre;
    private String apellidos;
    private String contraseña;
    private Date fecha_nacimiento;
    private String email;
    private int telefono;
    private int tutor;
    private int horas_dual;
    private int horas_fct;
    private String observaciones;
    private String dni;
    private Date fecha_creacion;
    
    @OneToOne
    @JoinColumn(name="empresa")
    private Empresa empresaAsignada;
    @OneToOne
    @JoinColumn(name="tutor")
    private Profesor profesor;
    @OneToMany( mappedBy="alumno", fetch=FetchType.EAGER)
    private List<Actividades> actividades;

    public Alumno() {
    }

    public Alumno(Long id, String nombre, String apellidos, String contraseña, Date fecha_nacimiento, String email, int telefono, int tutor, int horas_dual, int horas_fct, String observaciones, String dni, Date fecha_creacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.fecha_nacimiento = fecha_nacimiento;
        this.email = email;
        this.telefono = telefono;
        this.tutor = tutor;
        this.horas_dual = horas_dual;
        this.horas_fct = horas_fct;
        this.observaciones = observaciones;
        this.dni = dni;
        this.fecha_creacion = fecha_creacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    public int getTutor() {
        return tutor;
    }

    public void setTutor(int tutor) {
        this.tutor = tutor;
    }

    public int getHoras_dual() {
        return horas_dual;
    }

    public void setHoras_dual(int horas_dual) {
        this.horas_dual = horas_dual;
    }

    public int getHoras_fct() {
        return horas_fct;
    }

    public void setHoras_fct(int horas_fct) {
        this.horas_fct = horas_fct;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public List<Actividades> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividades> actividades) {
        this.actividades = actividades;
    }

    @Override
    public String toString() {
        return "Alumno{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", contrase\u00f1a=" + contraseña + ", fecha_nacimiento=" + fecha_nacimiento + ", email=" + email + ", telefono=" + telefono + ", tutor=" + tutor + ", horas_dual=" + horas_dual + ", horas_fct=" + horas_fct + ", observaciones=" + observaciones + ", dni=" + dni + ", fecha_creacion=" + fecha_creacion + ", empresaAsignada=" + empresaAsignada + ", actividades=" + actividades + '}';
    }

    
    
}
