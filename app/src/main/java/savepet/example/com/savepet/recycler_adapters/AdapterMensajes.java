package savepet.example.com.savepet.recycler_adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import savepet.example.com.savepet.OnButtonClickListener;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.holders.HolderAnimales;
import savepet.example.com.savepet.holders.HolderMensajes;
import savepet.example.com.savepet.modelos.Mensaje;

@SuppressWarnings("ALL")
public class AdapterMensajes extends RecyclerView.Adapter implements View.OnClickListener {
    List<Mensaje> mensajes;
    HolderMensajes holder;
    boolean propios;
    OnButtonClickListener listenerImageButton;


    public AdapterMensajes(List<Mensaje> mensajes) {
        super();
        this.mensajes = mensajes;
        this.propios = propios;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_mensaje, parent, false);
        holder = new HolderMensajes(view);
        holder.setClickButton(new OnButtonClickListener() {
            @Override
            public void onButtonClick(int position, View view) {
                listenerImageButton.onButtonClick(position,view);
            }
        });
        return holder;
    }
    public void setClickBtImagen(OnButtonClickListener listener) {
        if (listener != null) listenerImageButton = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((HolderMensajes) viewHolder).bind(mensajes.get(position),position);
    }

    @Override
    public int getItemCount() {
        return this.mensajes.size();
    }

    @Override
    public void onClick(View v) {

    }
}
