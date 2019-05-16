package savepet.example.com.savepet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment_inicio_sesion extends Fragment {

    Button registrarme;
    Button iniciarSesion;
    EditText nombreUsuario;
    EditText contraseña;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login,container,false);

        registrarme = view.findViewById(R.id.registrarme);
        iniciarSesion = view.findViewById(R.id.iniciar_sesion);

        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).ponerFragment(new Fragment_registro(),"registro");
            }
        });

        return view;
    }
}
