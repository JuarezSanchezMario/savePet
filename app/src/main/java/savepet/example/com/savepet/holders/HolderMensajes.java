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
public class HolderMensajes extends RecyclerView.ViewHolder {
    TextView mensaje_corto, nombreAutor, fecha;
    CircleImageView imagenAutor;
    View.OnClickListener listener;
    public HolderMensajes(View itemView) {
        super(itemView);
        fecha = (TextView) itemView.findViewById(R.id.fecha);
        nombreAutor = (TextView) itemView.findViewById(R.id.nombre_autor);
        imagenAutor = (CircleImageView) itemView.findViewById(R.id.imagen_autor);
        mensaje_corto = (TextView) itemView.findViewById(R.id.mensaje);
    }

    public void bind(Mensaje mensaje) {
        SimpleDateFormat stringFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha_mensaje = new Date();
        try {
            fecha_mensaje = stringFecha.parse(mensaje.getFecha());
        } catch (ParseException e) {

        }
        Date fecha_hoy = Calendar.getInstance().getTime();
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
                .error(R.drawable.error_imagen)
                .centerCrop()
                .into(imagenAutor);
    }
}
