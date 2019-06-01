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
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Usuario;
import savepet.example.com.savepet.recycler_adapters.AdapterAnimales;

@SuppressWarnings("ALL")
public class FragmentRecyclerAnimales extends Fragment implements Callback<List<Animal>> {
    FloatingActionButton fab;
    List<Animal> listaAnimales = new ArrayList<>();
    AdapterAnimales adapter;
    RecyclerView recyclerView;
    TextView mensaje;
    RelativeLayout containerRecycler;
    RelativeLayout containerMensaje;
    Usuario usuario;
    boolean propios = false;
    boolean vista = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        usuario = ((MainActivity) getActivity()).getUsuario();
        Bundle arg = getArguments();
        view = inflater.inflate(R.layout.fragment_recycler, container, false);
        containerMensaje = view.findViewById(R.id.container_mensaje);
        containerRecycler = view.findViewById(R.id.container_recycler);
        recyclerView = view.findViewById(R.id.recycler);
        mensaje = ((TextView) view.findViewById(R.id.mensaje));
        fab = view.findViewById(R.id.fab_crear);
        if (arg == null) {
            ((MainActivity) getActivity()).apiRest.getAnimales(FragmentRecyclerAnimales.this);
        } else {
            if (arg.containsKey("vista_animales")) {
                vista = true;
                Map<String, String> map = new HashMap<>();
                fab.setVisibility(View.GONE);
                map.put("dueno_id", ((Usuario) getArguments().getParcelable("vista_animales")).getId() + "");
                ((MainActivity) getActivity()).apiRest.getAnimalesFiltro(map, FragmentRecyclerAnimales.this);
            } else if (usuario != null) {
                propios = true;
                Map<String, String> map = new HashMap<>();
                map.put("dueno_id", ((MainActivity) getActivity()).getUsuario().getId() + "");
                ((MainActivity) getActivity()).apiRest.getAnimalesFiltro(map, FragmentRecyclerAnimales.this);
            } else {
                editVisibilidad(true);
                mensaje.setText(getString(R.string.necesitas_login));
            }
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).ponerFragment(new FragmentAltaAnimal(), "fragment_alta_animales", false, null);
            }
        });
        return view;
    }

    public void editVisibilidad(boolean visible) {
        if (visible) {
            containerMensaje.setVisibility(View.VISIBLE);
            containerRecycler.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        } else {
            containerMensaje.setVisibility(View.GONE);
            containerRecycler.setVisibility(View.VISIBLE);
            if (!vista) {
                fab.setVisibility(View.VISIBLE);
            } else {
                fab.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
        if (response.isSuccessful()) {
            listaAnimales = response.body();
            editVisibilidad(true);
            if (listaAnimales.size() == 0) {
                containerRecycler.setVisibility(View.GONE);
                mensaje.setText(vista ? getString(R.string.sin_animales) : getString(R.string.aún_no_animales));
                containerMensaje.setVisibility(View.VISIBLE);
            } else {
                editVisibilidad(false);
                adapter = new AdapterAnimales(listaAnimales, propios, vista);
                adapter.setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putString("animal", listaAnimales.get(recyclerView.getChildAdapterPosition(v)).getId());
                        args.putString("dueño", listaAnimales.get(recyclerView.getChildAdapterPosition(v)).getDueno_id() + "");
                        ((MainActivity) getActivity()).ponerFragment(new FragmentVistaAnimal(), "fragment_vista_animal", false, args);
                    }
                });
                adapter.setClickBtImagen(new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(final int position, final View view) {
                        PopupMenu pop = new PopupMenu(getContext(), view);
                        pop.getMenuInflater().inflate(R.menu.popup_opciones_lista, pop.getMenu());
                        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.eliminar:
                                        final Animal animal = listaAnimales.get(position);
                                        final ProgressDialog progressDialogo = new ProgressDialog(getContext());

                                        progressDialogo.setMessage(getString(R.string.borrando_animal));

                                        AlertDialog dialogo = new AlertDialog.Builder(getContext())
                                                .setTitle("")
                                                .setMessage(getString(R.string.estas_seguro_animal))
                                                .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ((MainActivity) getActivity()).apiRest.borrarAnimal(Integer.parseInt(animal.getId()), new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                if (response.isSuccessful()) {
                                                                    listaAnimales.remove(animal);
                                                                    recyclerView.getAdapter().notifyDataSetChanged();
                                                                    Toast.makeText(getContext(), getString(R.string.eliminado_exito), Toast.LENGTH_LONG).show();
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
                                        args.putParcelable("actualizar",listaAnimales.get(position));
                                        ((MainActivity) getActivity()).ponerFragment(new FragmentAltaAnimal(),"fragment_alta_animal",false,args);
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
            try {
                Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<List<Animal>> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
