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
import savepet.example.com.savepet.holders.HolderUsuarios;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Usuario;

@SuppressWarnings("ALL")
public class AdapterUsuarios extends RecyclerView.Adapter implements View.OnClickListener {
    List<Usuario> usuarios;
    HolderUsuarios holder;
    View.OnClickListener listener;
    Context context;
    public AdapterUsuarios(List<Usuario> usuarios) {
        super();
        this.usuarios = usuarios;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.detalle_usuario, parent, false);
        holder = new HolderUsuarios(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((HolderUsuarios) viewHolder).bind(usuarios.get(position),context);
    }

    @Override
    public int getItemCount() {
        return this.usuarios.size();
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
