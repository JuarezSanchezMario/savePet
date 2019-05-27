package savepet.example.com.savepet.api;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import retrofit2.http.QueryMap;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.fragments.FragmentRecyclerUsuarios;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Login;
import savepet.example.com.savepet.modelos.Mensaje;
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

    /****USUARIOS******/

    public void login(Login usuario, Callback<Usuario> callback) {
        Call<Usuario> responseCall;
        responseCall = retrofit.login(usuario);

        responseCall.enqueue(callback);
    }

    public void getAnimales(Callback<List<Animal>> callback) {
        retrofit.getAnimales().enqueue(callback);
    }
    public void getAnimalesFiltro(Map<String,String> map,Callback<List<Animal>> callback) {
        retrofit.getAnimalesFiltro(map).enqueue(callback);
    }

    public void getAnimales(int id, Callback<List<Animal>> callback) {
        retrofit.getAnimales(id).enqueue(callback);
    }

    public void getUsuarios(Callback<List<Usuario>> callback,String filtro) {
        Map<String,String> map = new HashMap<>();
        map.put("filtro",filtro);
        retrofit.getUsuarios(map).enqueue(callback);
    }
    public void registrarUsuario(File imagen, Map usuario, Callback<Usuario> callback) {

        RequestBody requestBodyImagen = RequestBody.create(MediaType.parse("*///*"), imagen);
        MultipartBody.Part subiendo_imagen = MultipartBody.Part.createFormData("imagen_perfil",(imagen!=null?imagen.getName():""), requestBodyImagen);

        retrofit.crearUsuario(subiendo_imagen,usuario).enqueue(callback);
    }

    /****ANIMALES******/

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

    public void modificarUsuario(File f,Integer id,Map<String,String> usuario,Callback<ResponseBody> callback) {
        RequestBody requestBodyImagen = RequestBody.create(MediaType.parse("*///*"), f);
        MultipartBody.Part subiendo_imagen = MultipartBody.Part.createFormData("imagen_perfil",(f!=null?f.getName():""), requestBodyImagen);

        retrofit.modificarUsuario(subiendo_imagen,id,usuario).enqueue(callback);
    }

    public void modificarAnimal(int id, Map<String,String> animal,Callback<ResponseBody> callback) {
        retrofit.modificarAnimal(id,animal).enqueue(callback);
    }
    public void modificarAnimal(File f,Integer id,Map<String,String> animal,Callback<ResponseBody> callback) {
        RequestBody requestBodyImagen = RequestBody.create(MediaType.parse("*///*"), f);
        MultipartBody.Part subiendo_imagen = MultipartBody.Part.createFormData("imagen_perfil",(f!=null?f.getName():""), requestBodyImagen);

        retrofit.modificarUsuario(subiendo_imagen,id,animal).enqueue(callback);
    }
     public void subirImagen(final File imagen, int id,Callback<ResponseBody> callback) throws Exception {

         RequestBody requestBodyImagen = RequestBody.create(MediaType.parse("*/*"), imagen);
         MultipartBody.Part subiendo_imagen = MultipartBody.Part.createFormData("imagen", imagen.getName(), requestBodyImagen);
         Map<String,Integer> idAnimal = new HashMap<String, Integer>();
         idAnimal.put("id",id);

         retrofit.subirImagen(idAnimal,subiendo_imagen).enqueue(callback);
    }
    public void borrarImagen(int id,Callback<ResponseBody> callback)
    {
        retrofit.borrarImagen(id).enqueue(callback);

    }

    /****MENSAJES******/
    public void getMensajes(Callback<List<Mensaje>> callback, String filtro) {
        Map<String,String> map = new HashMap<>();
        map.put("tipo",filtro);
        retrofit.getMensajes(map).enqueue(callback);
    }
    public void enviarMensaje(Callback<Mensaje> callback,Mensaje mensaje) {
        retrofit.enviarMensaje(mensaje).enqueue(callback);
    }
    public void borrarMensaje(Callback<ResponseBody> callback,Integer id) {
        retrofit.borrarMensaje(id).enqueue(callback);
    }
    /****EVENTOS******/


}
