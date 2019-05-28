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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import savepet.example.com.savepet.modelos.Evento;
import savepet.example.com.savepet.modelos.Usuario;
import savepet.example.com.savepet.recycler_adapters.AdapterAnimales;
import savepet.example.com.savepet.recycler_adapters.AdapterEventos;

@SuppressWarnings("ALL")
public class FragmentRecyclerEventos extends Fragment implements Callback<List<Evento>> {
    FloatingActionButton fab;
    List<Evento> listaEventos = new ArrayList<>();
    AdapterEventos adapter;
    RecyclerView recyclerView;
    TextView mensaje;
    RelativeLayout containerRecycler;
    RelativeLayout containerMensaje;
    Usuario usuario;
    Spinner tiempo;
    boolean propios = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        usuario = ((MainActivity) getActivity()).getUsuario();
        Bundle arg = getArguments();
        view = inflater.inflate(R.layout.fragment_recycler_evento, container, false);
        containerMensaje = view.findViewById(R.id.container_mensaje);
        containerRecycler = view.findViewById(R.id.container_recycler);
        mensaje = ((TextView) view.findViewById(R.id.mensaje));
        tiempo = view.findViewById(R.id.tiempo);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tiempo_evento));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiempo.setAdapter(dataAdapter);
        fab = view.findViewById(R.id.fab_crear);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).ponerFragment(new FragmentAltaEvento(), "fragment_alta_evento", false, null);
            }
        });
        recyclerView = view.findViewById(R.id.recycler);
        Map<String, String> map = new HashMap<>();
        map.put("pasado", (tiempo.getSelectedItem().toString().equals("Actuales") ? "0" : "1"));
        if (arg == null) {
            ((MainActivity) getActivity()).apiRest.getEventosFiltro(map,FragmentRecyclerEventos.this);
        } else {
            propios = true;
            map.put("organizador_id", ((MainActivity) getActivity()).getUsuario().getId() + "");
            ((MainActivity) getActivity()).apiRest.getEventosFiltro(map, FragmentRecyclerEventos.this);
        }
        return view;
    }

    public void editVisibilidad(boolean visible) {
        if (visible) {
            containerMensaje.setVisibility(View.VISIBLE);
            containerRecycler.setVisibility(View.GONE);
        } else {
            containerMensaje.setVisibility(View.GONE);
            containerRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
        if (response.isSuccessful()) {
            listaEventos = response.body();
            editVisibilidad(true);
            if (listaEventos.size() == 0) {
                containerRecycler.setVisibility(View.GONE);
                mensaje.setText(getString(R.string.aún_no_evento_propio));
                containerMensaje.setVisibility(View.VISIBLE);
            } else {
                editVisibilidad(false);
                adapter = new AdapterEventos(listaEventos, propios, false);
                adapter.setClickBtImagen(new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(int position, View view) {
                        PopupMenu pop = new PopupMenu(getContext(), view);
                        pop.getMenuInflater().inflate(R.menu.popup_opciones_lista, pop.getMenu());
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
            Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
        }
        tiempo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Map<String, String> map = new HashMap<>();
                if (tiempo.getSelectedItem().toString().equals("Actuales"))
                {
                    map.put("pasado","0");
                }
                else{
                    map.put("pasado","1");
                }
                if(propios)
                {
                    map.put("organizador_id", ((MainActivity) getActivity()).getUsuario().getId() + "");
                    ((MainActivity) getActivity()).apiRest.getEventosFiltro(map, FragmentRecyclerEventos.this);
                }
                else {
                    ((MainActivity) getActivity()).apiRest.getEventosFiltro(map, FragmentRecyclerEventos.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    @Override
    public void onFailure(Call<List<Evento>> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

    }
}
