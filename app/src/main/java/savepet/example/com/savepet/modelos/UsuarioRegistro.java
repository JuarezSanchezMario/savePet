package savepet.example.com.savepet.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

@SuppressWarnings("ALL")
public class UsuarioRegistro {
    private String nombre_usuario;
    private String nombre;
    private String password;
    private String email;
    private String telefono;
    private File imagen_perfil;

    public UsuarioRegistro() {
    }

    public UsuarioRegistro(String nombre_usuario, String nombre, String password, String email, String telefono, File imagen_perfil) {
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.telefono = telefono;
        this.imagen_perfil = imagen_perfil;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public File getImagen_perfil() {
        return imagen_perfil;
    }

    public void setImagen_perfil(File imagen_perfil) {
        this.imagen_perfil = imagen_perfil;
    }
}