package savepet.example.com.savepet.api;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import savepet.example.com.savepet.modelos.Evento;
import savepet.example.com.savepet.modelos.Login;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Mensaje;
import savepet.example.com.savepet.modelos.Usuario;

@SuppressWarnings("ALL")
public interface ProveedorServicios {

    /* USUARIOS */

    @GET("user")
    @Headers({"Accept: application/json"})
    Call<List<Usuario>> getUsuarios();

    @GET("user/{id}")
    @Headers({"Accept: application/json"})
    Call<Usuario> getUsuarios(@Path("id") Integer id);

    @POST("user/login")
    @Headers({"Accept: application/json"})
    Call<Usuario> login(@Body Login user);

    @Multipart
    @POST("user")
    @Headers({"Accept: application/json"})
    Call<Usuario> crearUsuario(@Part MultipartBody.Part imagen,@QueryMap Map<String,String> user);

    @POST("user/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> modificarUsuario(@Path("id") Integer id,@QueryMap Map<String,String> usuario);

    @Multipart
    @POST("user/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> modificarUsuario(@Part MultipartBody.Part imagen,@Path("id") Integer id,@QueryMap Map<String,String> usuario);

    @DELETE("user/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> borrarUsuario(@Path("id") Integer id);

    /*ANIMALES*/

    @GET("animal")
    @Headers({"Accept: application/json"})
    Call<List<Animal>> getAnimales();

    @GET("animal")
    @Headers({"Accept: application/json"})
    Call<List<Animal>> getAnimalesFiltro(@QueryMap Map<String ,String> filtros);

    @GET("animal/{id}")
    @Headers({"Accept: application/json"})
    Call<List<Animal>> getAnimales(@Path("id") Integer id);

    @POST("animal/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> modificarAnimal(@Path("id") Integer id,@QueryMap Map<String,String> animal);

    @Multipart
    @POST("animal/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> modificarAnimal(@Part MultipartBody.Part imagen,@Path("id") Integer id,@QueryMap Map<String,String> animal);

    @Multipart
    @POST("animal")
    @Headers({"Accept: application/json"})
    Call<Animal> crearAnimal(@Part MultipartBody.Part imagen,@QueryMap Map<String,String> animal);

    @DELETE("animal/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> borrarAnimal(@Path("id") Integer id);

    @Multipart
    @POST("imagen")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> subirImagen(@Part MultipartBody.Part id, @Part MultipartBody.Part imagen);

    @DELETE("imagen/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> borrarImagen(@Path("id") Integer id);

    /*EVENTOS*/

    @GET("evento")
    @Headers({"Accept: application/json"})
    Call<List<Evento>> getEventos();

    @GET("evento/{id}")
    @Headers({"Accept: application/json"})
    Call<Evento> getEvento(@Path("id") Integer id);

    @POST
    @Headers({"Accept: application/json"})
    Call<Evento> crearEvento(@QueryMap Map<String,String> evento);

    @POST("evento/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> modificarEvento(@Path("id") Integer id,@QueryMap Map<String,String> evento);

    @Multipart
    @POST("evento/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> modificarEvento(@Part MultipartBody.Part imagen,@Path("id") Integer id,@QueryMap Map<String,String> evento);

    @POST("evento/{id}/unirse")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> uniserEvento(@Path("id") Integer id);

    @POST("evento/{id}/abandonar")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> abandonarEvento(@Path("id") Integer id);

    @DELETE("evento/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> borrarEvento(@Path("id") Integer id);

    /*MENSAJES*/

    @POST("mensaje/")
    @Headers({"Accept: application/json"})
    Call<Mensaje> enviarMensaje(@Body Mensaje mensaje);

    @GET("mensaje/")
    @Headers({"Accept: application/json"})
    Call<List<Mensaje>> mensajesRecibidos(@QueryMap Map<String,Integer> propios);

    @DELETE("mensaje/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> borrarMensaje(@Path("id") Integer id);

    /*@POST("contract")
    @Headers({"Accept: application/json"})
    Call<Contract> createContract(@Body Contract contract);

    @POST("contract/{id}")
    @Headers({"Accept: application/json"})
    Call<Contract> updateContract(@Path("id") int id, @Body Contract contract);

    @Multipart
    @POST("contract/{id}/file")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> uploadFile(@Path("id") int id, @Part MultipartBody.Part file);*/

}
