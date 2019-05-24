package savepet.example.com.savepet.modelos;

import java.util.Date;

@SuppressWarnings("ALL")
public class Mensaje {
    private String id;
    private String autor_id;
    private Usuario autor;
    private String destinatario_id;
    private String contenido,fecha;
    private Date objetoFecha;

    public Mensaje() {
    }

    public Mensaje(String id, String autor_id, Usuario autor, String destinatario_id, String contenido, String fecha, Date objetoFecha) {
        this.id = id;
        this.autor_id = autor_id;
        this.autor = autor;
        this.destinatario_id = destinatario_id;
        this.contenido = contenido;
        this.fecha = fecha;
        this.objetoFecha = new Date(fecha);
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

    public Date getObjetoFecha() {
        return objetoFecha;
    }

    public void setObjetoFecha(Date objetoFecha) {
        this.objetoFecha = objetoFecha;
    }
}
