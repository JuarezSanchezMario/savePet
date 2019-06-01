package savepet.example.com.savepet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Mensaje;
import savepet.example.com.savepet.modelos.Usuario;

public class FragmentVistaMensaje extends Fragment {
    TextView mensaje, fecha, autor;
    Mensaje objetoMensaje;
    FloatingActionButton fab;
    CircleImageView imagen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vista_mensaje, container, false);
        mensaje = view.findViewById(R.id.mensaje);
        fab = view.findViewById(R.id.responder);
        SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        objetoMensaje = getArguments().getParcelable("mensaje");
        imagen = view.findViewById(R.id.imagen_autor);
        fecha = view.findViewById(R.id.fecha_mensaje);
        autor = view.findViewById(R.id.autor);

        autor.setText(" "+objetoMensaje.getAutor().getNombre());
        Picasso.get()
                .load(objetoMensaje.getAutor().getImagen_perfil())
                .fit()
                .placeholder(R.drawable.usuario_default)
                .error(R.drawable.error_imagen)
                .centerCrop()
                .into(imagen);
        mensaje.setText(objetoMensaje.getContenido());

        String fechaString = "";
        Date fecha_mensaje = new Date();
        try {
            fecha_mensaje = fechaFormat.parse(objetoMensaje.getFecha());
        } catch (ParseException e) {

        }
        fechaFormat = new SimpleDateFormat("dd-MM-yyyy");
        fechaString = fechaFormat.format(fecha_mensaje);

        fecha.setText(fechaString);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putParcelable("destinatario", objetoMensaje.getAutor());
                ((MainActivity) getActivity()).ponerFragment(new FragmentEnviarMensaje(), "fragment_enviar_mensaje", false, args);
            }
        });
        return view;
    }
}
