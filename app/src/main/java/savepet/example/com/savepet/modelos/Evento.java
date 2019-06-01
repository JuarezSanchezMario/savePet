package savepet.example.com.savepet.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@SuppressWarnings("ALL")
public class Evento implements Parcelable {
    private String id;
    private String organizador_id;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String aforo;
    private String lat,lng;
    private Usuario organizador;
    private List<Usuario> asistentes;
    private String imagen;
    private String asistentes_count;


    public Evento(String id, String organizador_id, String nombre, String descripcion, String fecha, String aforo, String lat, String lng, Usuario organizador, String imagen, String asistentes_count) {
        this.id = id;
        this.organizador_id = organizador_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.aforo = aforo;
        this.lat = lat;
        this.lng = lng;
        this.organizador = organizador;
        this.imagen = imagen;
        this.asistentes_count = asistentes_count;
    }

    public List<Usuario> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<Usuario> asistentes) {
        this.asistentes = asistentes;
    }

    public Evento(String id, String organizador_id, String nombre, String descripcion, String fecha, String aforo, String lat, String lng, Usuario organizador, List<Usuario> asistentes, String imagen, String asistentes_count) {
        this.id = id;
        this.organizador_id = organizador_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.aforo = aforo;
        this.lat = lat;
        this.lng = lng;
        this.organizador = organizador;
        this.asistentes = asistentes;
        this.imagen = imagen;
        this.asistentes_count = asistentes_count;
    }

    public Evento() {
    }
    public String getAsistentes_count() {
        return asistentes_count;
    }

    public void setAsistentes_count(String asistentes_count) {
        this.asistentes_count = asistentes_count;
    }
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizador_id() {
        return organizador_id;
    }

    public void setOrganizador_id(String organizador_id) {
        this.organizador_id = organizador_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAforo() {
        return aforo;
    }

    public void setAforo(String aforo) {
        this.aforo = aforo;
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

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.organizador_id);
        dest.writeString(this.nombre);
        dest.writeString(this.descripcion);
        dest.writeString(this.fecha);
        dest.writeString(this.aforo);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeParcelable(this.organizador, flags);
        dest.writeTypedList(this.asistentes);
        dest.writeString(this.imagen);
        dest.writeString(this.asistentes_count);
    }

    protected Evento(Parcel in) {
        this.id = in.readString();
        this.organizador_id = in.readString();
        this.nombre = in.readString();
        this.descripcion = in.readString();
        this.fecha = in.readString();
        this.aforo = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.organizador = in.readParcelable(Usuario.class.getClassLoader());
        this.asistentes = in.createTypedArrayList(Usuario.CREATOR);
        this.imagen = in.readString();
        this.asistentes_count = in.readString();
    }

    public static final Parcelable.Creator<Evento> CREATOR = new Parcelable.Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel source) {
            return new Evento(source);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };
}
