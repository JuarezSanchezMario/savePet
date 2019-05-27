package savepet.example.com.savepet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.security.auth.callback.Callback;

import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.maps;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.recycler_adapters.AdapterImagenes;

import static savepet.example.com.savepet.MainActivity.MAPS;

public class FragmentVistaAnimal extends Fragment {
    TextView nombre,descripcion,descripcionCorta,fecha,sinImagenes;
    ImageButton localizacion,mensaje;
    ImageView imagenPerfil;
    Animal animal;
    RecyclerView recyclerView;
    List<String> imagenes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vista_animal,container,false);
        imagenPerfil = view.findViewById(R.id.imagen_animal_card);
        localizacion = view.findViewById(R.id.localizacion);
        mensaje = view.findViewById(R.id.mensaje);
        nombre = view.findViewById(R.id.nombre);
        descripcion = view.findViewById(R.id.descripcion);
        descripcionCorta = view.findViewById(R.id.descripcion_corta);
        fecha = view.findViewById(R.id.fecha);
        recyclerView = view.findViewById(R.id.recycler);
        sinImagenes = view.findViewById(R.id.sin_imagenes);
        animal = getArguments().getParcelable("animal");

        nombre.setText(animal.getNombre());
        descripcionCorta.setText(animal.getDescripcion_corta());
        descripcion.setText(animal.getDescripcion_larga());

        SimpleDateFormat format = new SimpleDateFormat("");
        fecha.setText();

        Picasso.get()
                .load(animal.getImagen_perfil())
                .fit()
                .placeholder(R.drawable.no_photo)
                .error(R.drawable.error_imagen)
                .centerCrop()
                .into(imagenPerfil);

        localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!animal.getLat().equals("") && !animal.getLng().equals(""))
                {
                    Intent verLocalizacion = new Intent(getActivity(), maps.class);
                    verLocalizacion.putExtra("lat",animal.getLat());
                    verLocalizacion.putExtra("lng",animal.getLng());
                    startActivityForResult(new Intent(getActivity(), maps.class), MAPS);
                }
                else {
                    Toast.makeText(getContext(),getString(R.string.no_localizacion),Toast.LENGTH_LONG).show();
                }
            }
        });

        mensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putParcelable("destinatario",animal.getDueno());
                ((MainActivity)getActivity()).ponerFragment(new FragmentEnviarMensaje(),"fragment_enviar_mensaje",false,args);
            }
        });

        if(animal.getImagenes() == null)
        {
            recyclerView.setVisibility(View.GONE);
            sinImagenes.setVisibility(View.VISIBLE);

        }
        else{
            AdapterImagenes adapter = new AdapterImagenes(animal.getImagenes());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }

        return view;
    }
}
