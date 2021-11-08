package models;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author chris
 */
@Entity
@Table(name="profesor")
public class Profesor {
    
    @Id
    @GeneratedValue(strategy=IDENTITY)
    
    private Long id;
    private String nombre;
    private String apellidos;
    private String contraseña;
    private String email;
    private Date fecha_creacion;

    public Profesor() {
    }

    public Profesor(Long id, String nombre, String apellidos, String contraseña, String email, Date fecha_creacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @Override
    public String toString() {
        return "Profesor{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", contrase\u00f1a=" + contraseña + ", email=" + email + ", fecha_creacion=" + fecha_creacion + '}';
    }
    
}
