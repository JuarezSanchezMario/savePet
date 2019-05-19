package savepet.example.com.savepet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.adapters.AdapterAnimales;
import savepet.example.com.savepet.modelos.Animal;

public class FragmentRecyclerAnimales extends Fragment implements Callback<List<Animal>> {
    FloatingActionButton fab;
    List<Animal> listaAnimales = new ArrayList<>();
    AdapterAnimales adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_animales,container,false);
        fab = view.findViewById(R.id.fab_crear_animal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).ponerFragment(new Fragment_alta_animal(),"fragment_alta_animales",false);
            }
        });
        ((MainActivity)getActivity()).apiRest.getAnimales(FragmentRecyclerAnimales.this);
        adapter = new AdapterAnimales(listaAnimales);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_animales);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
        if(response.isSuccessful())
        {
            listaAnimales = response.body();
            adapter = new AdapterAnimales(listaAnimales);

        }
        else{
            Toast.makeText(getContext(),"ERROR", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<List<Animal>> call, Throwable t) {

    }
}
