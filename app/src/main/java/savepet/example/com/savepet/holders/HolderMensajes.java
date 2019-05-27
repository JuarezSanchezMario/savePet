package savepet.example.com.savepet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import savepet.example.com.savepet.OnButtonClickListener;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Mensaje;

@SuppressWarnings("ALL")
public class HolderMensajes extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView mensaje_corto, nombreAutor, fecha;
    CircleImageView imagenAutor;
    OnButtonClickListener listener;
    int pos;
    ImageView opciones;

    public HolderMensajes(View itemView) {
        super(itemView);
        fecha = (TextView) itemView.findViewById(R.id.fecha);
        opciones = (ImageView) itemView.findViewById(R.id.opciones);
        nombreAutor = (TextView) itemView.findViewById(R.id.nombre_autor);
        imagenAutor = (CircleImageView) itemView.findViewById(R.id.imagen_autor);
        mensaje_corto = (TextView) itemView.findViewById(R.id.mensaje);
        opciones.setOnClickListener(this);
    }

    public void bind(Mensaje mensaje, int pos) {
        SimpleDateFormat stringFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha_mensaje = new Date();
        try{
            fecha_mensaje =  stringFecha.parse(mensaje.getFecha());
        }catch (ParseException e)
        {

        }
        Date fecha_hoy = Calendar.getInstance().getTime();
        opciones.setVisibility(View.VISIBLE);
        String fechaString = "";
        mensaje_corto.setText(mensaje.getContenido().substring(0, mensaje.getContenido().length() - (mensaje.getContenido().length() / 2)) + "...");
        if (fecha_mensaje.getDay() == fecha_hoy.getDay()) {
            stringFecha = new SimpleDateFormat("HH:mm");
        } else if (fecha_mensaje.getMonth() == fecha_hoy.getMonth()) {
            stringFecha = new SimpleDateFormat("dd-MM");
        } else {
            stringFecha = new SimpleDateFormat("dd-MM-yyyy");
        }
        fechaString = stringFecha.format(fecha_mensaje);
        fecha.setText(fechaString);
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

    public void setClickButton(OnButtonClickListener listener) {
        if (listener != null) this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) listener.onButtonClick(pos, v);
    }
}
