package savepet.example.com.savepet.modelos;

import java.util.Date;
import java.util.List;

public class Animal {
    private String id;
    private String imagen_perfil;
    private String nombre;
    private String tipo;
    private String fecha_nacimiento;
    private Date objetoFecha;
    private String lat;
    private String lng;
    private List<Usuario> dueno;
    private int dueno_id;
    private String descripcion_larga;
    private String descripcion_corta;

    public Animal(String id, String imagen_perfil, String nombre, String tipo, String fecha_nacimiento, String lat, String lng, List<Usuario> dueno, int dueno_id, String descripcion_larga, String descripcion_corta) {
        this.id = id;
        this.imagen_perfil = imagen_perfil;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lat = lat;
        this.objetoFecha = new Date(fecha_nacimiento);
        this.lng = lng;
        this.dueno = dueno;
        this.dueno_id = dueno_id;
        this.descripcion_larga = descripcion_larga;
        this.descripcion_corta = descripcion_corta;
    }

    public Date getObjetoFecha() {
        return objetoFecha;
    }

    public void setObjetoFecha(Date objetoFecha) {
        this.objetoFecha = objetoFecha;
    }

    public Animal(String id, String imagen_perfil, String nombre, String tipo, String fecha_nacimiento, String lat, String lng, String descripcion_larga, String descripcion_corta) {
        this.id = id;
        this.imagen_perfil = imagen_perfil;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lat = lat;
        this.lng = lng;
        this.descripcion_larga = descripcion_larga;
        this.descripcion_corta = descripcion_corta;
    }

    public Animal(String id, String imagen_perfil, String nombre, String tipo, String fecha_nacimiento, String lat, String lng, int dueno_id, String descripcion_larga, String descripcion_corta) {
        this.id = id;
        this.imagen_perfil = imagen_perfil;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lat = lat;
        this.lng = lng;
        this.dueno_id = dueno_id;
        this.descripcion_larga = descripcion_larga;
        this.descripcion_corta = descripcion_corta;
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

    public String getDescripcion_larga() {
        return descripcion_larga;
    }

    public void setDescripcion_larga(String descripcion_larga) {
        this.descripcion_larga = descripcion_larga;
    }

    public String getDescripcion_corta() {
        return descripcion_corta;
    }

    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }

    public Animal() {
    }
}
