package savepet.example.com.savepet.api;

import java.util.List;

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
import savepet.example.com.savepet.modelos.AnimalNuevo;
import savepet.example.com.savepet.modelos.Login;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Usuario;

@SuppressWarnings("ALL")
public interface ProveedorServicios {

    @GET("user")
    @Headers({"Accept: application/json"})
    Call<List<Usuario>> getUsuarios();

    @GET("user/{id}")
    @Headers({"Accept: application/json"})
    Call<Usuario> getUsuarios(@Path("id") Integer id);

    @POST("user/login")
    @Headers({"Accept: application/json"})
    Call<Usuario> login(@Body Login user);

    @POST("user")
    @Headers({"Accept: application/json"})
    Call<Usuario> crearUsuario();

    @POST("user/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> modificarUsuario(@Path("id") Integer id);

    @DELETE("user/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> borrarUsuario(@Path("id") Integer id);

    @GET("animal")
    @Headers({"Accept: application/json"})
    Call<List<Animal>> getAnimales();

    @GET("animal/{id}")
    @Headers({"Accept: application/json"})
    Call<List<Animal>> getAnimales(@Path("id") Integer id);

    @POST("animal")
    @Headers({"Accept: application/json"})
    Call<Animal> crearAnimal(@Body AnimalNuevo animalNuevo);

    @DELETE("animal/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> borrarAnimal(@Path("id") Integer id);

    @Multipart
    @POST("imagen")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> subirImagen(@Part MultipartBody.Part id, @Part MultipartBody.Part imagen);

    

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
