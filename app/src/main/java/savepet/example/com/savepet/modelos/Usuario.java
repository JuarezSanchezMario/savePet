package savepet.example.com.savepet.modelos;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("ALL")
public class Usuario {
    private String nombre_usuario;
    private String apellidos;
    private String contraseña;
    private String email;

    public Usuario() {
    }

    public Usuario(String nombre_usuario, String apellidos, String contraseña, String email) {
        this.nombre_usuario = nombre_usuario;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.email = email;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
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
}