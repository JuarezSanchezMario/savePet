package savepet.example.com.savepet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Usuario;
import savepet.example.com.savepet.recycler_adapters.AdapterUsuarios;

@SuppressWarnings("ALL")
public class FragmentRecyclerUsuarios extends Fragment {

    EditText busqueda;
    Button buscar;
    List<Usuario> listaUsuarios = new ArrayList<>();
    AdapterUsuarios adapter;
    RecyclerView recyclerView;
    TextView mensaje;
    Usuario usuario;
    String filtro = "";
    Bundle arg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        usuario = ((MainActivity) getActivity()).getUsuario();
        arg = getArguments();
        view = inflater.inflate(R.layout.fragment_recycler, container, false);
        mensaje = ((TextView) view.findViewById(R.id.mensaje));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refrescarRecycler();
            }
        });
        recyclerView = view.findViewById(R.id.recycler);

        return view;
    }

    public boolean refrescarRecycler() {
        ((MainActivity) getActivity()).apiRest.getUsuarios(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    listaUsuarios = response.body();
                    adapter = new AdapterUsuarios(listaUsuarios);
                    adapter.setClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (arg != null) {
                                if (arg.containsKey("buscar")) {
                                    Bundle argumentos = new Bundle();
                                    argumentos.putBoolean("enviar", true);
                                    argumentos.putParcelable("destinatario", listaUsuarios.get(recyclerView.getChildAdapterPosition(v)));
                                    ((MainActivity) getActivity()).ponerFragment(new FragmentEnviarMensaje(), "fragment_enviar_mensaje", true, argumentos);
                                }
                            } else {
                                //TODO
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        }, filtro);
        return true;
    }
}
