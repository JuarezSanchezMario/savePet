package savepet.example.com.savepet.modelos;

import java.util.List;

public class Animal {
    private String id;
    private String imagen_perfil;
    private String nombre;
    private String tipo;
    private String fecha_nacimiento;
    private String lat;
    private String lng;
    private List<Usuario> dueno;
    private int dueno_id;
    private String descripcion;
    private String descripcionCorta;

    public Animal(String id, String imagen_perfil, String nombre, String tipo, String fecha_nacimiento, String lat, String lng, List<Usuario> dueno, int dueno_id, String descripcion, String descripcionCorta) {
        this.id = id;
        this.imagen_perfil = imagen_perfil;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lat = lat;
        this.lng = lng;
        this.dueno = dueno;
        this.dueno_id = dueno_id;
        this.descripcion = descripcion;
        this.descripcionCorta = descripcionCorta;
    }

    public Animal(String id, String imagen_perfil, String nombre, String tipo, String fecha_nacimiento, String lat, String lng, String descripcion, String descripcionCorta) {
        this.id = id;
        this.imagen_perfil = imagen_perfil;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lat = lat;
        this.lng = lng;
        this.descripcion = descripcion;
        this.descripcionCorta = descripcionCorta;
    }

    public Animal(String id, String imagen_perfil, String nombre, String tipo, String fecha_nacimiento, String lat, String lng, int dueno_id, String descripcion, String descripcionCorta) {
        this.id = id;
        this.imagen_perfil = imagen_perfil;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lat = lat;
        this.lng = lng;
        this.dueno_id = dueno_id;
        this.descripcion = descripcion;
        this.descripcionCorta = descripcionCorta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen_perfil() {
        return imagen_perfil;
    }

    public void setImagen_perfil(String imagen_perfil) {
        this.imagen_perfil = imagen_perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public List<Usuario> getDueno() {
        return dueno;
    }

    public void setDueno(List<Usuario> dueno) {
        this.dueno = dueno;
    }

    public int getDueno_id() {
        return dueno_id;
    }

    public void setDueno_id(int dueno_id) {
        this.dueno_id = dueno_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public Animal() {
    }
}
