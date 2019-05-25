package savepet.example.com.savepet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
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
import savepet.example.com.savepet.OnButtonClickListener;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Mensaje;
import savepet.example.com.savepet.modelos.Usuario;
import savepet.example.com.savepet.recycler_adapters.AdapterAnimales;
import savepet.example.com.savepet.recycler_adapters.AdapterMensajes;

@SuppressWarnings("ALL")
public class FragmentRecyclerMensajes extends Fragment implements Callback<List<Mensaje>> {
    FloatingActionButton fab;
    List<Mensaje> listaMensajes = new ArrayList<>();
    AdapterMensajes adapter;
    RecyclerView recyclerView;
    TextView mensaje;
    RelativeLayout containerRecycler;
    RelativeLayout containerMensaje;
    Bundle arg;
    boolean propios = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        arg = getArguments();
        view = inflater.inflate(R.layout.fragment_recycler, container, false);
        containerMensaje = view.findViewById(R.id.container_mensaje);
        containerRecycler = view.findViewById(R.id.container_recycler);
        mensaje = ((TextView) view.findViewById(R.id.mensaje));

        fab = view.findViewById(R.id.fab_crear);
        fab.setImageResource(R.drawable.escribir_mensaje);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle filtro = new Bundle();
                filtro.putBoolean("buscar",true);
                ((MainActivity) getActivity()).ponerFragment(new FragmentRecyclerUsuarios(), "fragment_usuarios_buscar", true,filtro);
            }
        });
        recyclerView = view.findViewById(R.id.recycler);
        if (arg == null) {
            ((MainActivity) getActivity()).apiRest.getMensajes(FragmentRecyclerMensajes.this, "recibidos");
        } else {
            Map<String, String> map = new HashMap<>();
            ((MainActivity) getActivity()).apiRest.getMensajes(FragmentRecyclerMensajes.this, "enviados");
        }

        return view;
    }

    public void textVisibilidad(boolean visible) {
        if (visible) {
            containerMensaje.setVisibility(View.VISIBLE);
            containerRecycler.setVisibility(View.GONE);
        } else {
            containerMensaje.setVisibility(View.GONE);
            containerRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
        if (response.isSuccessful()) {
            listaMensajes = response.body();
            textVisibilidad(true);
            if (listaMensajes.size() == 0) {
                if (arg != null && arg.containsKey("enviados")) {
                    mensaje.setText(getString(R.string.aun_no_mensajes_enviados));
                } else {
                    mensaje.setText(getString(R.string.aun_no_mensajes_recibidos));
                }
                containerRecycler.setVisibility(View.GONE);
                containerMensaje.setVisibility(View.VISIBLE);
            } else {
                textVisibilidad(false);
                adapter = new AdapterMensajes(listaMensajes);
                adapter.setClickBtImagen(new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(int position, View view) {
                        PopupMenu pop = new PopupMenu(getContext(), view);
                        pop.getMenuInflater().inflate(R.menu.popup_opciones_lista_animales, pop.getMenu());
                        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.editar: //TODO
                                        break;
                                    case R.id.eliminar: //TODO
                                        break;
                                }
                                return true;
                            }
                        });
                        pop.show();
                    }
                });
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerView.getAdapter().notifyDataSetChanged();
            }


        } else {
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<List<Mensaje>> call, Throwable t) {

    }
}
