package savepet.example.com.savepet.modelos;

import java.io.File;

public class AnimalNuevo extends AnimalBase {
    private File imagen_perfil;

    public File getImagen_perfil() {
        return imagen_perfil;
    }

    public void setImagen_perfil(File imagen_perfil) {
        this.imagen_perfil = imagen_perfil;
    }

    public AnimalNuevo(File imagen_perfil) {
        super();
        this.imagen_perfil = imagen_perfil;
    }

}
