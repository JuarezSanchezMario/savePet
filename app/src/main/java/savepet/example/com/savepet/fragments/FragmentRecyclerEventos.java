package savepet.example.com.savepet.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
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
            ((MainActivity) getActivity()).apiRest.getEventosFiltro(map, FragmentRecyclerEventos.this);
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
                adapter.setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putString("evento", listaEventos.get(recyclerView.getChildAdapterPosition(v)).getId());
                        ((MainActivity) getActivity()).ponerFragment(new FragmentVistaEvento(), "fragment_vista_evento", false, args);
                    }
                });
                adapter.setClickBtImagen(new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(final int position, View view) {
                        PopupMenu pop = new PopupMenu(getContext(), view);
                        pop.getMenuInflater().inflate(R.menu.popup_opciones_lista, pop.getMenu());
                        final Evento evento = listaEventos.get(position);
                        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.eliminar:
                                        final ProgressDialog progressDialogo = new ProgressDialog(getContext());
                                        progressDialogo.setMessage(getString(R.string.borrando_evento));

                                        AlertDialog dialogo = new AlertDialog.Builder(getContext())
                                                .setTitle("")
                                                .setMessage(getString(R.string.estas_seguro_evento))
                                                .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ((MainActivity) getActivity()).apiRest.borrarEvento(Integer.parseInt(evento.getId()), new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                if (response.isSuccessful()) {
                                                                    Toast.makeText(getContext(), getString(R.string.eliminado_exito), Toast.LENGTH_LONG).show();
                                                                    ((MainActivity) getActivity()).ponerFragment(new FragmentEventos(), "fragment_eventos", true, null);
                                                                } else {
                                                                    try {
                                                                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }
                                                })
                                                .setNegativeButton(getString(R.string.no), null)
                                                .show();
                                        break;
                                    case R.id.editar:
                                        Bundle args = new Bundle();
                                        args.putParcelable("actualizar",listaEventos.get(position));
                                        ((MainActivity) getActivity()).ponerFragment(new FragmentAltaEvento(),"fragment_alta_evento",false,args);
                                        break;

                                }
                                return true;
                            }

                            ;
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
                if (tiempo.getSelectedItem().toString().equals("Actuales")) {
                    map.put("pasado", "0");
                } else {
                    map.put("pasado", "1");
                }
                if (propios) {
                    map.put("organizador_id", ((MainActivity) getActivity()).getUsuario().getId() + "");
                    ((MainActivity) getActivity()).apiRest.getEventosFiltro(map, FragmentRecyclerEventos.this);
                } else {
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
