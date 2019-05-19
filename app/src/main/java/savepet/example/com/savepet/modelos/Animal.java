package savepet.example.com.savepet.modelos;

public class Animal {
    private String imagen_perfil;
    private String nombre;
    private String tipo;
    private String fecha_nacimiento;
    private String lat;
    private String lng;
    private String descripcion;
    private String descripcionCorta;

    public Animal(String imagen_perfil, String nombre, String tipo, String fecha_nacimiento, String lat, String lng, String descripcion, String descripcionCorta) {
        this.imagen_perfil = imagen_perfil;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lat = lat;
        this.lng = lng;
        this.descripcion = descripcion;
        this.descripcionCorta = descripcionCorta;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public Animal() {
    }
}
