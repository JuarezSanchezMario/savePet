package savepet.example.com.savepet.recycler_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import savepet.example.com.savepet.OnButtonClickListener;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.holders.HolderAnimales;
import savepet.example.com.savepet.holders.HolderImagenes;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Imagen;

@SuppressWarnings("ALL")
public class AdapterImagenes extends RecyclerView.Adapter implements View.OnClickListener {
    List<Imagen> imagenes;
    HolderImagenes holder;

    View.OnClickListener listener;


    public AdapterImagenes(List<Imagen> imagenes) {
        super();
        this.imagenes = imagenes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_animal, parent, false);
        holder = new HolderImagenes(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((HolderImagenes) viewHolder).bind(imagenes.get(position).getPath());
    }

    @Override
    public int getItemCount() {
        return this.imagenes.size();
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
