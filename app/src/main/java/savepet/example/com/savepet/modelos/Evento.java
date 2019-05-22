package savepet.example.com.savepet.modelos;

import java.util.Date;

@SuppressWarnings("ALL")
public class Evento {
    private String id;
    private String organizador_id;
    private String nombre;
    private String descripcion;
    private String fecha;
    private Date objetoFecha;
    private int aforo;
    private String lat,lng;
    private Usuario organizador;

    public Evento() {
    }

    public Evento(String id, String organizador_id, String nombre, String descripcion, String fecha, int aforo, String lat, String lng, Usuario organizador) {
        this.id = id;
        this.organizador_id = organizador_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.objetoFecha = new Date(fecha);
        this.aforo = aforo;
        this.lat = lat;
        this.lng = lng;
        this.organizador = organizador;
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

    public Date getObjetoFecha() {
        return objetoFecha;
    }

    public void setObjetoFecha(Date objetoFecha) {
        this.objetoFecha = objetoFecha;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
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

}
