package savepet.example.com.savepet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Evento;
import savepet.example.com.savepet.modelos.Usuario;

public class FragmentVistaUsuario extends Fragment {
    Usuario usuario;
    TextView info;
    CircleImageView imagenPerfil;
    TextView nombre;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vista_usuario, container, false);
        Bundle args = new Bundle();
        usuario = getArguments().getParcelable("usuario");
        imagenPerfil = view.findViewById(R.id.imagen_usuario_card);
        info = view.findViewById(R.id.info_usuario);
        nombre = view.findViewById(R.id.nombre);
        args.putParcelable("vista_animales", usuario);
        ImageButton correo = view.findViewById(R.id.correo);
        if (MainActivity.getUsuario() != null) {
            if (usuario.getId() == MainActivity.getUsuario().getId()) {
                correo.setVisibility(View.GONE);
            }
        } else {
            correo.setVisibility(View.GONE);
        }
        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putParcelable("destinatario", usuario);
                ((MainActivity) getActivity()).ponerFragment(new FragmentEnviarMensaje(), "fragment_enviar_mensaje", false, args);
            }
        });
        info.setText(usuario.getInfo());
        nombre.setText(usuario.getNombre());
        Picasso.get()
                .load(usuario.getImagen_perfil())
                .fit()
                .placeholder(R.drawable.no_photo)
                .error(R.drawable.error_imagen)
                .centerCrop()
                .into(imagenPerfil);
        FragmentRecyclerAnimales fragmentRecyclerAnimales = new FragmentRecyclerAnimales();
        fragmentRecyclerAnimales.setArguments(args);
        getChildFragmentManager().beginTransaction().replace(R.id.animales_usuario, fragmentRecyclerAnimales, "fragment_animales_lista").commit();
        return view;
    }
}
