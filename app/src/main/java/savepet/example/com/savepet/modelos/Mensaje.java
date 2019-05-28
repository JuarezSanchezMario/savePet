package savepet.example.com.savepet.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@SuppressWarnings("ALL")
public class Mensaje implements Parcelable {
    private String id;
    private String autor_id;
    private Usuario autor;
    private String destinatario_id;
    private String contenido,fecha;



    public Mensaje(String id, String autor_id, Usuario autor, String destinatario_id, String contenido, String fecha) {
        this.id = id;
        this.autor_id = autor_id;
        this.autor = autor;
        this.destinatario_id = destinatario_id;
        this.contenido = contenido;
        this.fecha = fecha;
    }
    public Mensaje() {
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutor_id() {
        return autor_id;
    }

    public void setAutor_id(String autor_id) {
        this.autor_id = autor_id;
    }

    public String getDestinatario_id() {
        return destinatario_id;
    }

    public void setDestinatario_id(String destinatario_id) {
        this.destinatario_id = destinatario_id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.autor_id);
        dest.writeParcelable(this.autor, flags);
        dest.writeString(this.destinatario_id);
        dest.writeString(this.contenido);
        dest.writeString(this.fecha);
    }

    protected Mensaje(Parcel in) {
        this.id = in.readString();
        this.autor_id = in.readString();
        this.autor = in.readParcelable(Usuario.class.getClassLoader());
        this.destinatario_id = in.readString();
        this.contenido = in.readString();
        this.fecha = in.readString();
    }

    public static final Parcelable.Creator<Mensaje> CREATOR = new Parcelable.Creator<Mensaje>() {
        @Override
        public Mensaje createFromParcel(Parcel source) {
            return new Mensaje(source);
        }

        @Override
        public Mensaje[] newArray(int size) {
            return new Mensaje[size];
        }
    };
}
