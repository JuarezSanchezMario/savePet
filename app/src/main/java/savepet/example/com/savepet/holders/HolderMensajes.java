package savepet.example.com.savepet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import savepet.example.com.savepet.OnButtonClickListener;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Mensaje;

@SuppressWarnings("ALL")
public class HolderMensajes extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView mensaje_corto, nombreAutor,fecha;
    ImageView imagenAutor;
    OnButtonClickListener listener;
    int pos;
    ImageButton opciones;
    public HolderMensajes(View itemView)
    {
        super(itemView);
        fecha = (TextView)itemView.findViewById(R.id.fecha);
        opciones = (ImageButton) itemView.findViewById(R.id.opciones);
        nombreAutor = (TextView)itemView.findViewById(R.id.nombre_autor);
        imagenAutor = (ImageView) itemView.findViewById(R.id.imagen_autor);
        opciones.setOnClickListener(this);
    }
    public void bind(Mensaje mensaje,int pos)
    {
        Date fecha_hoy = Calendar.getInstance().getTime();
        opciones.setVisibility(View.VISIBLE);
        mensaje_corto.setText(mensaje.getContenido().substring(0,mensaje.getContenido().length()-20));
        if(mensaje.getObjetoFecha().getDay() == fecha_hoy.getDay())
        {
            fecha.setText(mensaje.getObjetoFecha().getHours());
        }
        else if(mensaje.getObjetoFecha().getMonth() == fecha_hoy.getMonth())
        {
            fecha.setText(mensaje.getObjetoFecha().getDay()+" " + mensaje.getObjetoFecha().getMonth());
        }
        else {
            fecha.setText(mensaje.getObjetoFecha().getDay()+"/" + mensaje.getObjetoFecha().getMonth()+"/"+mensaje.getObjetoFecha().getDay());
        }
        nombreAutor.setText(mensaje.getAutor().getNombre());
        Picasso.get()
                .load(mensaje.getAutor().getImagen_perfil())
                .fit()
                .placeholder(R.drawable.usuario_default)
                .error(R.drawable.not_found)
                .centerCrop()
                .into(imagenAutor);
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
