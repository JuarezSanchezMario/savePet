package savepet.example.com.savepet.Errores_servidor;

@SuppressWarnings("ALL")
public class LoginError {

    private int statusCode;
    private String message;

    public LoginError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}