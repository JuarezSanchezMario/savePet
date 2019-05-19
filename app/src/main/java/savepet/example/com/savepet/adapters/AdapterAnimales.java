package savepet.example.com.savepet.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import savepet.example.com.savepet.holders.HolderAnimales;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.modelos.Animal;

@SuppressWarnings("ALL")
public class AdapterAnimales extends RecyclerView.Adapter implements View.OnClickListener {
    List<Animal> animales;
    HolderAnimales holder;

    public AdapterAnimales(List<Animal> animales) {
        super();
        this.animales = animales;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, parent, false);
        holder = new HolderAnimales(view);

        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((HolderAnimales) viewHolder).bind(animales.get(position));
    }

    @Override
    public int getItemCount() {
        return this.animales.size();
    }

    @Override
    public void onClick(View v) {

    }
}
