package savepet.example.com.savepet.modelos;

@SuppressWarnings("ALL")
public class Evento {
    private String id;
    private String organizador_id;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String aforo;
    private String lat,lng;
    private Usuario organizador;
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

}
