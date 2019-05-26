package savepet.example.com.savepet.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Usuario;

@SuppressWarnings("ALL")
public class HolderUsuarios extends RecyclerView.ViewHolder {
    TextView nombre, numeroAnimales, info;
    CircleImageView imagenPerfil;

    public HolderUsuarios(View itemView) {
        super(itemView);
        info = (TextView) itemView.findViewById(R.id.info);
        numeroAnimales = (TextView) itemView.findViewById(R.id.numero_animales);
        nombre = (TextView) itemView.findViewById(R.id.nombre_usuario_card);
        imagenPerfil = (CircleImageView) itemView.findViewById(R.id.imagen_usuario_card);
    }

    public void bind(final Usuario usuario, final Context context) {

        final String informacion = usuario.getInfo();

        ((MainActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info.setText((informacion == null) ? context.getString(R.string.sin_informacion) : informacion);
                numeroAnimales.setText(usuario.getAnimales_count());
                nombre.setText(usuario.getNombre());
                Picasso.get()
                        .load(usuario.getImagen_perfil())
                        .fit()
                        .placeholder(R.drawable.no_photo)
                        .error(R.drawable.error_imagen)
                        .centerCrop()
                        .into(imagenPerfil);
            }
        });
    }
}
