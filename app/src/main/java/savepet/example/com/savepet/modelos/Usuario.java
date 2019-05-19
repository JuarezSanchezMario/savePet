package savepet.example.com.savepet.modelos;


import java.io.File;

import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;

@SuppressWarnings("ALL")
public class Usuario {
    private int id;
    private String lat;
    private String lon;
    private String nombre_usuario;
    private String nombre;
    private String email;
    private String telefono;
    private String imagen_perfil;
    private String api_token;

    public Usuario() {
    }

    public Usuario(int id, String lat, String lon, String nombre_usuario, String nombre, String email, String telefono, String imagen_perfil, String api_token) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.imagen_perfil = imagen_perfil;
        this.api_token = api_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImagen_perfil() {
        return imagen_perfil;
    }

    public void setImagen_perfil(String imagen_perfil) {
        this.imagen_perfil = imagen_perfil;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }
}