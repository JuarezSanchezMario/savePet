package savepet.example.com.savepet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Animal;

@SuppressWarnings("ALL")
public class HolderAnimales extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView nombre,descripcionCorta;
    ImageView imagenPerfil;
    View.OnClickListener listener;
    public HolderAnimales(View itemView)
    {
        super(itemView);
        nombre = (TextView)itemView.findViewById(R.id.nombre_animal_card);
        descripcionCorta = (TextView)itemView.findViewById(R.id.descripcion_corta);
        imagenPerfil = (ImageView) itemView.findViewById(R.id.imagen_animal_card);
    }
    public void bind(Animal animal)
    {
        nombre.setText(animal.getNombre());
        descripcionCorta.setText(animal.getDescripcionCorta());
        Picasso.get()
                .load(animal.getImagen_perfil())
                .fit()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.not_found)
                .centerCrop()
                .into(imagenPerfil);
    }

    @Override
    public void onClick(View v) {
        if(listener != null) listener.onClick(v);
    }
}
