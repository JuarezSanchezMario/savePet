package savepet.example.com.savepet.modelos;

@SuppressWarnings("ALL")
public class Login {

    private String nombre_usuario;
    private String contraseña;
    private String api_token;

    /**
     * Este método se utilizará cuando solo tengamos usuario y contraseña
     * @param nombre_usuario
     * @param contraseña
     */
    public Login(String nombre_usuario, String contraseña) {
        this.nombre_usuario = nombre_usuario;
        this.contraseña = contraseña;
    }

    /**
     * Este constructor se utilizará cuando iniciemos sesión automaticamente si ya hemos iniciado anteriormente
     * @param api_token es un string con el que validaremos al usuario y que el ORM en el servidor lo verifica automaticamente
     */
    public Login(String api_token) {
        this.api_token = api_token;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }
}
