package savepet.example.com.savepet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.Errores_servidor.ErrorUtils;
import savepet.example.com.savepet.Errores_servidor.LoginError;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.api.ApiRest;
import savepet.example.com.savepet.modelos.Login;
import savepet.example.com.savepet.modelos.Usuario;

public class Fragment_inicio_sesion extends Fragment implements Callback<Usuario> {

    Button registrarme;
    Button iniciarSesion;
    EditText nombre_usuario;
    EditText contraseña;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login,container,false);

        registrarme = view.findViewById(R.id.registrarme);
        iniciarSesion = view.findViewById(R.id.iniciar_sesion);
        nombre_usuario = view.findViewById(R.id.nombre_usuario_login);
        contraseña = view.findViewById(R.id.contraseña_login);
        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).ponerFragment(new Fragment_registro(),"registro_usuario",false);
            }
        });
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario;
                Login user = new Login(nombre_usuario.getText().toString(), contraseña.getText().toString());
                ((MainActivity)getActivity()).apiRest.login(user,Fragment_inicio_sesion.this);
            }
        });
        return view;
    }
    @Override
    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
        if (response.isSuccessful()) {
            Usuario usuario = response.body();
            ApiRest.apiToken = usuario.getApi_token();

            SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getString(R.string.MisPreferencias), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString(getString(R.string.apiToken), ApiRest.apiToken);
            editor.commit();
            //Bundle arg = new Bundle();
            /*arg.putParcelable("user", user);
            getFragmentManager().getFragments().clear();

            WelcomeFragment welcomeFragment = new WelcomeFragment();
            welcomeFragment.setArguments(arg);
            FragmentTransaction FT = getFragmentManager().beginTransaction();
            FT.replace(R.id.fragmentContainer, welcomeFragment, "welcomeFragment");
            FT.addToBackStack(null);
            FT.commit();*/
            ((MainActivity)getActivity()).ponerFragment(new FragmentAnimales(),"fragment_animales",true);
            ((MainActivity)getActivity()).sesion_iniciada(usuario);

        } else {
            LoginError error = ErrorUtils.parseError(response);
            Toast.makeText(getContext(), error.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Usuario> call, Throwable t) {
        Toast.makeText(getContext(), "Error" + t.toString(), Toast.LENGTH_LONG).show();
    }
}
