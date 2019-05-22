package savepet.example.com.savepet.api;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Animal;
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
                ((MainActivity)parent).generarSnackBar(parent.getString(R.string.necesitas_login));
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

    public void getAnimal(Callback<List<Animal>> callback) {
        retrofit.getAnimales().enqueue(callback);
    }
    public void getAnimalesFiltro(Map<String,String> map,Callback<List<Animal>> callback) {
        retrofit.getAnimalesFiltro(map).enqueue(callback);
    }

    public void getAnimal(int id, Callback<List<Animal>> callback) {
        retrofit.getAnimales(id).enqueue(callback);
    }

    public void registrarUsuario(File imagen, Map usuario, Callback<Usuario> callback) {

        RequestBody requestBodyImagen = RequestBody.create(MediaType.parse("*///*"), imagen);
        MultipartBody.Part subiendo_imagen = MultipartBody.Part.createFormData("imagen_perfil",(imagen!=null?imagen.getName():""), requestBodyImagen);

        retrofit.crearUsuario(subiendo_imagen,usuario).enqueue(callback);
    }

    public void registrarAnimal(File imagen,Map animal, Callback<Animal> callback) {
        RequestBody requestBodyImagen = RequestBody.create(MediaType.parse("*///*"), imagen);
        MultipartBody.Part subiendo_imagen = MultipartBody.Part.createFormData("imagen_perfil",(imagen!=null?imagen.getName():""), requestBodyImagen);

        retrofit.crearAnimal(subiendo_imagen,animal).enqueue(callback);
    }

    public void borrarAnimal(Integer id,Callback<ResponseBody> callback) {
        retrofit.borrarAnimal(id).enqueue(callback);
    }

    public void borrarUsuario(Integer id,Callback<ResponseBody> callback) {
        retrofit.borrarUsuario(id).enqueue(callback);
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

     public void uploadFile(final File imagen, int id) throws Exception {

         RequestBody requestBodyImagen = RequestBody.create(MediaType.parse("*/*"), imagen);
        // RequestBody requestBodyId = RequestBody.create(MediaType.parse("*/*"), id);
         MultipartBody.Part subiendo_imagen = MultipartBody.Part.createFormData("imagen", imagen.getName(), requestBodyImagen);
         MultipartBody.Part id_imagen = MultipartBody.Part.createFormData("id_imagen",null,requestBodyImagen);

      //   Response response = retrofit.uploadFile(id, subiendo_imagen).execute();
        /*if (!response.isSuccessful()) {
            if (response.code() == 422) {
                throw new Exception(parent.getString(R.string.errorUploadFile));
            } else throw new Exception(parent.getString(R.string.serverError));
        }*/
    }
}
