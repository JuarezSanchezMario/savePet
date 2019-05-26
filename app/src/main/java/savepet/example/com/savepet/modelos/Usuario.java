package savepet.example.com.savepet.modelos;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Usuario implements Parcelable {
    private int id;
    private String lat;
    private String lng;
    private String nombre_usuario;
    private String nombre;
    private String email;
    private String telefono;
    private String imagen_perfil;
    private String api_token;
    private String info;
    private List<Animal> animales;
    private String animales_count;

    public List<Animal> getAnimales() {
        return animales;
    }

    public void setAnimales(List<Animal> animales) {
        this.animales = animales;
    }

    public Usuario() {
    }

    public Usuario(int id, String lat, String lng, String nombre_usuario, String nombre, String email, String telefono, String imagen_perfil, String api_token, String animales_count) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.imagen_perfil = imagen_perfil;
        this.api_token = api_token;
        this.animales_count = animales_count;
    }

    public Usuario(int id, String lat, String lng, String nombre_usuario, String nombre, String email, String telefono, String imagen_perfil, String api_token, String info, String animales_count) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.imagen_perfil = imagen_perfil;
        this.api_token = api_token;
        this.info = info;
        this.animales_count = animales_count;
    }

    public Usuario(int id, String lat, String lng, String nombre_usuario, String nombre, String email, String telefono, String imagen_perfil, String api_token) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.imagen_perfil = imagen_perfil;
        this.api_token = api_token;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAnimales_count() {
        return animales_count;
    }

    public void setAnimales_count(String animales_count) {
        this.animales_count = animales_count;
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

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeString(this.nombre_usuario);
        dest.writeString(this.nombre);
        dest.writeString(this.email);
        dest.writeString(this.telefono);
        dest.writeString(this.imagen_perfil);
        dest.writeString(this.api_token);
        dest.writeString(this.info);
        dest.writeList(this.animales);
        dest.writeString(this.animales_count);
    }

    protected Usuario(Parcel in) {
        this.id = in.readInt();
        this.lat = in.readString();
        this.lng = in.readString();
        this.nombre_usuario = in.readString();
        this.nombre = in.readString();
        this.email = in.readString();
        this.telefono = in.readString();
        this.imagen_perfil = in.readString();
        this.api_token = in.readString();
        this.info = in.readString();
        this.animales = new ArrayList<Animal>();
        in.readList(this.animales, Animal.class.getClassLoader());
        this.animales_count = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel source) {
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}