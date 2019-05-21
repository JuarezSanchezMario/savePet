package savepet.example.com.savepet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.adapters.AdapterAnimales;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Usuario;

public class FragmentRecyclerAnimales extends Fragment implements Callback<List<Animal>> {
    FloatingActionButton fab;
    List<Animal> listaAnimales = new ArrayList<>();
    AdapterAnimales adapter;
    RecyclerView recyclerView;
    TextView mensaje;
    RelativeLayout containerRecycler;
    RelativeLayout containerMensaje;
    Usuario usuario;
    boolean sinAnimales = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        usuario = ((MainActivity) getActivity()).getUsuario();
        Bundle arg = getArguments();
        view = inflater.inflate(R.layout.fragment_recycler_animales, container, false);
        containerMensaje = view.findViewById(R.id.container_mensaje);
        containerRecycler = view.findViewById(R.id.container_recycler);
        mensaje = ((TextView) view.findViewById(R.id.mensaje));
        if (arg != null && usuario == null) {
            editVisibilidad(true);
            mensaje.setText(getString(R.string.necesitas_login));
        } else {
            fab = view.findViewById(R.id.fab_crear_animal);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).ponerFragment(new Fragment_alta_animal(), "fragment_alta_animales", false);
                }
            });
            recyclerView = view.findViewById(R.id.recycler_animales);
            if (arg == null) {
                ((MainActivity) getActivity()).apiRest.getAnimales(FragmentRecyclerAnimales.this);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("dueno_id", ((MainActivity) getActivity()).getUsuario().getId() + "");
                ((MainActivity) getActivity()).apiRest.getAnimalesFiltro(map, FragmentRecyclerAnimales.this);
            }
        }
        return view;
    }
    public void editVisibilidad(boolean visible)
    {
        if(visible)
        {
            containerMensaje.setVisibility(View.VISIBLE);
            containerRecycler.setVisibility(View.GONE);
        }
        else{
            containerMensaje.setVisibility(View.GONE);
            containerRecycler.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
        if (response.isSuccessful()) {
            listaAnimales = response.body();
            editVisibilidad(true);
            if (listaAnimales.size() == 0)
            {
                containerRecycler.setVisibility(View.GONE);
                mensaje.setText(getString(R.string.aún_no_animales));
                containerMensaje.setVisibility(View.VISIBLE);
            }
            else{
                editVisibilidad(false);
                adapter = new AdapterAnimales(listaAnimales);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerView.getAdapter().notifyDataSetChanged();
            }



        } else {
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<List<Animal>> call, Throwable t) {

    }
}
