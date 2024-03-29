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
import savepet.example.com.savepet.holders.HolderEventos;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Evento;

@SuppressWarnings("ALL")
public class AdapterEventos extends RecyclerView.Adapter implements View.OnClickListener {
    List<Evento> eventos;
    HolderEventos holder;
    boolean propios;
    View.OnClickListener listener;
    OnButtonClickListener listenerImageButton;


    public AdapterEventos(List<Evento> eventos, boolean propios, boolean vista) {
        super();
        this.eventos = eventos;
        this.propios = propios;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_evento, parent, false);
        holder = new HolderEventos(view);
        holder.setClickButton(new OnButtonClickListener() {
            @Override
            public void onButtonClick(int position, View view) {
                listenerImageButton.onButtonClick(position,view);
            }
        });
        view.setOnClickListener(listener);
        return holder;
    }
    public void setClickBtImagen(OnButtonClickListener listener) {
        if (listener != null) listenerImageButton = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((HolderEventos) viewHolder).bind(eventos.get(position),propios,position);
    }

    @Override
    public int getItemCount() {
        return this.eventos.size();
    }

    public void setClickListener(View.OnClickListener listener)
    {
        if(listener!=null) this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null) listener.onClick(v);
    }
}
