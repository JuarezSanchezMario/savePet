package savepet.example.com.savepet.api;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.AnimalNuevo;
import savepet.example.com.savepet.modelos.Login;
import savepet.example.com.savepet.modelos.Usuario;

@SuppressWarnings("ALL")
public class ApiRest {
    private static String url;
    private static Context parent;
    public static String apiToken = null;
    private static ProveedorServicios retrofit;

    public ApiRest(String url, Context parent) {
        this.url = url;
        this.parent = parent;
        retrofit = createRetrofit();
    }


    /**
     * Interceptor para introducir en la cabecera automaticamente en las peticiones el api_token si existe
     */
    public static class AddHeaderInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            if (apiToken != null) {
                builder.addHeader("Authorization", "Bearer " + apiToken);
            }
            return chain.proceed(builder.build());
        }
    }

    /**
     * Interceptor para gestionar los errores de login con el servidor
     */
    public static class UnauthenticatedError implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);
            Log.d("Upload", "" + response.code());
            if (response.code() == 403 || response.code() == 401) {
                //   ((MainActivity) parent).openLoginFragment(true);
            }
            return response;
        }
    }

    public static ProveedorServicios createRetrofit() {


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new AddHeaderInterceptor());
        httpClient.addNetworkInterceptor(new UnauthenticatedError());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(ProveedorServicios.class);
    }

    public void login(Login usuario, Callback<Usuario> callback) {
        Call<Usuario> responseCall;
        responseCall = retrofit.login(usuario);

        responseCall.enqueue(callback);
    }

    public void getAnimales(Callback<List<Animal>> callback) throws Exception {
        retrofit.getAnimales().enqueue(callback);
    }

    public void registrarUsuario(Usuario usuario, Callback<Usuario> callback) {
        retrofit.crearUsuario().enqueue(callback);
    }

    public void registrarAnimal(AnimalNuevo animalNuevo, Callback<Animal> callback) {
        retrofit.crearAnimal(animalNuevo).enqueue(callback);
    }

    public void borrarAnimal() {

    }

    public void borrarUsuario() {

    }

    public void modificarUsuario() {

    }

    public void modificarAnimal() {

    }
  /*  public void updateContract(Contract contract) throws Exception {
        contract.setState(1);
        Response response = retrofit.updateContract(contract.getId(), contract).execute();
        if (!response.isSuccessful()) {
            throw new Exception(parent.getString(R.string.serverError));
        }
    }*/

    /*  public void uploadFile(final File file, int id) throws Exception {*/
    // RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
    //  MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
    //  RequestBody.create(MediaType.parse("text/plain"), file.getName());

     /*   Response response = retrofit.uploadFile(id, fileToUpload).execute();
        if (!response.isSuccessful()) {
            if (response.code() == 422) {
                throw new Exception(parent.getString(R.string.errorUploadFile));
            } else throw new Exception(parent.getString(R.string.serverError));
        }
    }*/
}
