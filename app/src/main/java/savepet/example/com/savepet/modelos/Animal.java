package savepet.example.com.savepet.modelos;

public class Animal extends AnimalBase {
    private String imagen_perfil;

    public String getImagen_perfil() {
        return imagen_perfil;
    }

    public void setImagen_perfil(String imagen_perfil) {
        this.imagen_perfil = imagen_perfil;
    }

    public Animal(String imagen_perfil) {
        super();
        this.imagen_perfil = imagen_perfil;
    }
}
