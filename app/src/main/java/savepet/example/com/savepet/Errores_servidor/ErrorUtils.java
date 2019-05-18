package savepet.example.com.savepet.Errores_servidor;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("ALL")
public class ErrorUtils {

    public static LoginError parseError(Response<?> response) {
        Retrofit retrofit =  new Retrofit.Builder().baseUrl("http://google.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Converter<ResponseBody, LoginError> converter =
                retrofit.responseBodyConverter(LoginError.class, new Annotation[0]);

        LoginError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new LoginError();
        }

        return error;
    }
}