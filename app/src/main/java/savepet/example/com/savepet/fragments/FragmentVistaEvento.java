package savepet.example.com.savepet.fragments;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Evento;
import savepet.example.com.savepet.modelos.Usuario;
import savepet.example.com.savepet.recycler_adapters.AdapterUsuarios;


public class FragmentVistaEvento extends Fragment implements Callback<Evento> {

    ProgressDialog progressDialog;
    Evento evento;
    ImageView imagenPerfil;
    TextView descripcion, nombre, sinAsistentes;
    Button unirse, abandonar;
    RecyclerView usuariosRecycler;
    AdapterUsuarios adapter;
    List<Usuario> asistentes;
    String idEvento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vista_evento, container, false);
        idEvento = getArguments().getString("evento");
        sinAsistentes = view.findViewById(R.id.sin_asistentes);
        imagenPerfil = view.findViewById(R.id.imagen_evento);
        descripcion = view.findViewById(R.id.descripcion);
        nombre = view.findViewById(R.id.nombre);
        unirse = view.findViewById(R.id.unirse);
        abandonar = view.findViewById(R.id.abandonar);
        usuariosRecycler = view.findViewById(R.id.usuarios);
        progressDialog = new ProgressDialog(getContext());
        ((MainActivity) getActivity()).apiRest.getEvento(Integer.parseInt(idEvento), FragmentVistaEvento.this);
        return view;
    }
    public boolean esAsistente()
    {
        int id = MainActivity.getUsuario().getId();
        for(Usuario usuario : asistentes)
        {
            if(usuario.getId() == id) return true;
        }
        return false;
    }
    @Override
    public void onResponse(Call<Evento> call, Response<Evento> response) {
        if (response.isSuccessful()) {
            evento = response.body();
            asistentes = evento.getAsistentes();
            descripcion.setText(evento.getDescripcion());
            nombre.setText(evento.getNombre());
            Picasso.get()
                    .load(evento.getImagen())
                    .fit()
                    .placeholder(R.drawable.no_photo)
                    .error(R.drawable.error_imagen)
                    .centerCrop()
                    .into(imagenPerfil);
            adapter = new AdapterUsuarios(asistentes);
            adapter.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(evento.getOrganizador_id() == MainActivity.getUsuario().getId()+"")
                    {
                        Toast.makeText(getContext(), getString(R.string.tu_mismo), Toast.LENGTH_LONG).show();
                    }
                    Bundle args = new Bundle();
                    args.putParcelable("usuario", asistentes.get(usuariosRecycler.getChildAdapterPosition(v)));
                    ((MainActivity) getActivity()).ponerFragment(new FragmentVistaUsuario(), "fragment_vista_evento", false, args);
                }
            });
            abandonar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!esAsistente()) {
                        Toast.makeText(getContext(), getString(R.string.no_asistente), Toast.LENGTH_LONG).show();
                    }else if(evento.getOrganizador_id().equals(MainActivity.getUsuario().getId()))
                    {
                        Toast.makeText(getContext(), getString(R.string.eres_dueño), Toast.LENGTH_LONG).show();
                    }
                    else {
                        progressDialog.setMessage(getString(R.string.abandonando));
                        progressDialog.show();
                        ((MainActivity) getActivity()).apiRest.abandonar(Integer.parseInt(idEvento), new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    Toast.makeText(getContext(), getString(R.string.abandonado_exito), Toast.LENGTH_LONG).show();
                                    ((MainActivity) getActivity()).apiRest.getEvento(Integer.parseInt(idEvento), FragmentVistaEvento.this);

                                } else {
                                    Toast.makeText(getContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
            });
            unirse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (esAsistente()) {
                        Toast.makeText(getContext(), getString(R.string.ya_unido), Toast.LENGTH_LONG).show();
                    } else {
                        progressDialog.setMessage(getString(R.string.uniendose));
                        progressDialog.show();
                        ((MainActivity) getActivity()).apiRest.unirseEvento(Integer.parseInt(idEvento), new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    Toast.makeText(getContext(), getString(R.string.unido_exito), Toast.LENGTH_LONG).show();
                                    ((MainActivity) getActivity()).apiRest.getEvento(Integer.parseInt(idEvento), FragmentVistaEvento.this);

                                } else {
                                    Toast.makeText(getContext(), response.message().toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
            });
            if (asistentes.size() == 0) {
                usuariosRecycler.setVisibility(View.GONE);
                sinAsistentes.setVisibility(View.VISIBLE);
            } else {
                usuariosRecycler.setVisibility(View.VISIBLE);
                sinAsistentes.setVisibility(View.GONE);
                usuariosRecycler.setAdapter(adapter);
                usuariosRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        } else {
            Toast.makeText(getContext(), response.message().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Evento> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

    }
}
