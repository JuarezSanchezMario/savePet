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
import savepet.example.com.savepet.modelos.Animal;

@SuppressWarnings("ALL")
public class AdapterAnimales extends RecyclerView.Adapter implements View.OnClickListener {
    List<Animal> animales;
    HolderAnimales holder;
    Context context;
    boolean propios,vista;
    View.OnClickListener listener;
    OnButtonClickListener listenerImageButton;


    public AdapterAnimales(List<Animal> animales,boolean propios,boolean vista) {
        super();
        this.animales = animales;
        this.propios = propios;
        this.vista = vista;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.detalle_animal, parent, false);
        holder = new HolderAnimales(view);
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
        ((HolderAnimales) viewHolder).bind(animales.get(position),propios,position,context,vista);
    }

    @Override
    public int getItemCount() {
        return this.animales.size();
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
