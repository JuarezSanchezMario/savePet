package savepet.example.com.savepet.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.OnButtonClickListener;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Animal;

@SuppressWarnings("ALL")
public class HolderAnimales extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView nombre,descripcionCorta;
    CircleImageView imagenPerfil;
    OnButtonClickListener listener;
    int pos;
    ImageButton opciones;
    public HolderAnimales(View itemView)
    {
        super(itemView);
        opciones = (ImageButton) itemView.findViewById(R.id.opciones);
        nombre = (TextView)itemView.findViewById(R.id.nombre_animal_card);
        descripcionCorta = (TextView)itemView.findViewById(R.id.descripcion_corta);
        imagenPerfil = (CircleImageView) itemView.findViewById(R.id.imagen_animal_card);
        opciones.setOnClickListener(this);
    }
    public void bind(final Animal animal,final boolean propios,final int pos,final Context context,final boolean vista)
    {
        final String descripcionCortaAnimal = animal.getDescripcion_corta();
        ((MainActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                opciones.setVisibility( (propios && !vista) ? View.VISIBLE : View.GONE);
                nombre.setText(animal.getNombre());
                descripcionCorta.setText((descripcionCortaAnimal == null)?context.getString(R.string.sin_decripcion):descripcionCortaAnimal);
                Picasso.get()
                        .load(animal.getImagen_perfil())
                        .fit()
                        .placeholder(R.drawable.no_photo)
                        .error(R.drawable.error_imagen)
                        .centerCrop()
                        .into(imagenPerfil);
            }
        });
        this.pos = pos;
    }
    public void setClickButton(OnButtonClickListener listener)
    {
        if(listener != null)this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(listener != null) listener.onButtonClick(pos,v);
    }
}
