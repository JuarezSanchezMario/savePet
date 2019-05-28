package savepet.example.com.savepet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import savepet.example.com.savepet.OnButtonClickListener;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Evento;

@SuppressWarnings("ALL")
public class HolderEventos extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView nombre,fecha,participantes,aforo,descripcion;
    ImageView imagenEvento;
    OnButtonClickListener listener;
    int pos;
    ImageButton opciones;
    public HolderEventos(View itemView)
    {
        super(itemView);
        opciones = (ImageButton) itemView.findViewById(R.id.opciones);
        aforo = (TextView)itemView.findViewById(R.id.aforo_evento);
        descripcion = itemView.findViewById(R.id.descripcion);
        participantes = (TextView)itemView.findViewById(R.id.participantes);
        nombre = (TextView)itemView.findViewById(R.id.nombre);
        fecha = (TextView)itemView.findViewById(R.id.fecha);
        imagenEvento = (ImageView) itemView.findViewById(R.id.imagen_evento_card);
        opciones.setOnClickListener(this);
    }
    public void bind(Evento evento, boolean propios, int pos)
    {
        SimpleDateFormat stringFecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fecha_evento = new Date();
        try {
            fecha_evento = stringFecha.parse(evento.getFecha());
        } catch (ParseException e) {

        }
        stringFecha = new SimpleDateFormat("dd-MM-yyyy hh:mm");

        fecha.setText(stringFecha.format(fecha_evento));
        opciones.setVisibility( propios ? View.VISIBLE : View.GONE);
        nombre.setText(evento.getNombre());
        aforo.setText(evento.getAforo());
        descripcion.setText(evento.getDescripcion());

        participantes.setText(evento.getAsistentes_count());
        Picasso.get()
                .load(evento.getImagen())
                .fit()
                .placeholder(R.drawable.logo)
                .error(R.drawable.error_imagen)
                .centerCrop()
                .into(imagenEvento);
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
