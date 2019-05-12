package savepet.example.com.savepet.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import savepet.example.com.savepet.modelos.AnimalNuevo;
import savepet.example.com.savepet.modelos.Login;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Usuario;

@SuppressWarnings("ALL")
public interface ProveedorServicios {
    @POST("user/login")
    @Headers({"Accept: application/json"})
    Call<Usuario> login(@Body Login user);

    @GET("animal/")
    @Headers({"Accept: application/json"})
    Call<List<Animal>> getAnimales();

    @POST("animal")
    @Headers({"Accept: application/json"})
    Call<Animal> crearAnimal(@Body AnimalNuevo animalNuevo);

    @POST("user/")
    @Headers({"Accept: application/json"})
    Call<Usuario> crearUsuario();
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
