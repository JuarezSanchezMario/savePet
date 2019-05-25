package savepet.example.com.savepet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Mensaje;
import savepet.example.com.savepet.modelos.Usuario;

public class FragmentEnviarMensaje extends Fragment implements Callback<Mensaje> {
    EditText buscarUsuarios, contenido;
    FloatingActionButton fab;
    Bundle args;
    Usuario destinatario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enviar_mensaje, container, false);
        args = getArguments();
        fab = (FloatingActionButton) view.findViewById(R.id.fab_enviar);
        if (args.containsKey("destinatario")) destinatario = args.getParcelable("destinatario");
        buscarUsuarios = (EditText) view.findViewById(R.id.edit_buscar_usuarios);
        contenido = (EditText) view.findViewById(R.id.contenido);
        if (args.containsKey("consulta")) buscarUsuarios.setClickable(false);
        if(destinatario != null) buscarUsuarios.setText(destinatario.getNombre_usuario());
        buscarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle filtro = new Bundle();
                filtro.putBoolean("buscar", true);
                ((MainActivity) getActivity()).ponerFragment(new FragmentRecyclerUsuarios(), "fragment_usuarios_buscar", false, filtro);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle filtro = new Bundle();
                if (args.containsKey("enviar")) {
                    final Mensaje nuevoMensaje = new Mensaje();
                    nuevoMensaje.setDestinatario_id(destinatario.getId() + "");
                    nuevoMensaje.setContenido(contenido.getText().toString().trim());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity) getActivity()).apiRest.enviarMensaje(FragmentEnviarMensaje.this, nuevoMensaje);
                        }
                    });
                } else if (args.containsKey("responder")) {
                    filtro.putParcelable("destinatario", destinatario);
                    ((MainActivity) getActivity()).ponerFragment(new FragmentEnviarMensaje(), "fragment_enviar_mensaje", true, filtro);
                }

            }
        });
        return view;
    }

    @Override
    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
        if (response.isSuccessful()) {
            Toast.makeText(getContext(), getString(R.string.mensaje_enviado_exito), Toast.LENGTH_LONG).show();
        }
        {
            try {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<Mensaje> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
