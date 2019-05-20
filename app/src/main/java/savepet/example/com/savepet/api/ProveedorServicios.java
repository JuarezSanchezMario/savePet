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

    @Multipart
    @POST("user")
    @Headers({"Accept: application/json"})
    Call<Usuario> crearUsuario(@Part MultipartBody.Part imagen,@QueryMap Map<String,String> user);

    @POST("user/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> modificarUsuario(@Path("id") Integer id);

    @DELETE("user/{id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> borrarUsuario(@Path("id") Integer id);

    @GET("animal")
    @Headers({"Accept: application/json"})
    Call<List<Animal>> getAnimales();

    @GET("animal")
    @Headers({"Accept: application/json"})
    Call<List<Animal>> getAnimalesFiltro(@QueryMap Map<String ,String> filtros);

    @GET("animal/{id}")
    @Headers({"Accept: application/json"})
    Call<List<Animal>> getAnimales(@Path("id") Integer id);

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
