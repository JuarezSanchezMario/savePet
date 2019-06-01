package savepet.example.com.savepet.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import savepet.example.com.savepet.FileUtilidades.FileUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.GridSpacingItemDecoration;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.maps;
import savepet.example.com.savepet.modelos.Animal;
import savepet.example.com.savepet.modelos.Imagen;
import savepet.example.com.savepet.recycler_adapters.AdapterImagenes;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static savepet.example.com.savepet.MainActivity.GALERIA;
import static savepet.example.com.savepet.MainActivity.MAPS;

public class FragmentVistaAnimal extends Fragment implements Callback<Animal> {
    TextView nombre, descripcion, fecha, sinImagenes;
    ImageButton localizacion, mensaje;
    ImageView imagenPerfil;
    Animal animal;
    FloatingActionButton fabGaleria, fabCamara;
    RecyclerView recyclerView;
    List<Imagen> imagenes;
    String idAnimal;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vista_animal, container, false);
        imagenPerfil = view.findViewById(R.id.imagen_animal_card);
        localizacion = view.findViewById(R.id.localizacion);
        mensaje = view.findViewById(R.id.mensaje);
        nombre = view.findViewById(R.id.nombre);
        descripcion = view.findViewById(R.id.descripcion);
        fecha = view.findViewById(R.id.fecha);
        fabGaleria = view.findViewById(R.id.galeria);
        fabCamara = view.findViewById(R.id.camara);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 60, true));
        sinImagenes = view.findViewById(R.id.sin_imagenes);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.subiendo_imagen));
        idAnimal = getArguments().getString("animal");

        if (MainActivity.getUsuario() != null) {
            if ((MainActivity.getUsuario().getId() + "").equals((getArguments().getString("dueño")))) {
                mensaje.setVisibility(View.GONE);
            } else {
                fabCamara.hide();
                fabGaleria.hide();
            }
        } else {
            fabCamara.hide();
            fabGaleria.hide();
        }
        ((MainActivity) getActivity()).apiRest.getAnimales(idAnimal, this);
        return view;
    }

    @Override
    public void onResponse(Call<Animal> call, Response<Animal> response) {
        if (response.isSuccessful()) {
            animal = response.body();
            nombre.setText(animal.getNombre());
            descripcion.setText(animal.getDescripcion_larga());

            if (animal.getFecha_nacimiento() != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date fechaAnimal;
                try {
                    fechaAnimal = format.parse(animal.getFecha_nacimiento());
                    format = new SimpleDateFormat("dd-MM-yyyy");
                    fecha.setText(getString(R.string.fecha_nacimiento) + ": " + format.format(fechaAnimal));
                } catch (ParseException e) {

                }

            }

            Picasso.get()
                    .load(animal.getImagen_perfil())
                    .fit()
                    .placeholder(R.drawable.no_photo)
                    .error(R.drawable.error_imagen)
                    .centerCrop()
                    .into(imagenPerfil);

            localizacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (animal.getLat() != null && animal.getLng() != null) {
                        Intent verLocalizacion = new Intent(getActivity(), maps.class);
                        verLocalizacion.putExtra("lat", Double.parseDouble(animal.getLat()));
                        verLocalizacion.putExtra("lng", Double.parseDouble(animal.getLng()));
                        startActivityForResult(verLocalizacion, MAPS);
                    } else {
                        Toast.makeText(getContext(), getString(R.string.no_localizacion), Toast.LENGTH_LONG).show();
                    }
                }
            });

            mensaje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.getUsuario() == null) {
                        ((MainActivity) getActivity()).generarSnackBar(getString(R.string.necesitas_login));
                    } else {
                        Bundle args = new Bundle();
                        args.putParcelable("destinatario", animal.getDueno());
                        ((MainActivity) getActivity()).ponerFragment(new FragmentEnviarMensaje(), "fragment_enviar_mensaje", false, args);
                    }
                }
            });
            fabCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camara, CAMERA);
                }
            });
            fabGaleria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent abrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(abrirGaleria, GALERIA);
                }
            });

            if (animal.getImagenes().size() == 0) {
                recyclerView.setVisibility(View.GONE);
                sinImagenes.setVisibility(View.VISIBLE);

            } else {
                imagenes = animal.getImagenes();
                AdapterImagenes adapter = new AdapterImagenes(imagenes);
                adapter.setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        final View viewFoto = getActivity().getLayoutInflater().inflate(R.layout.ver_imagen_recycler, null);
                        ImageView verImagen = viewFoto.findViewById(R.id.ver_imagen_grid);

                        Picasso.get()
                                .load(imagenes.get(recyclerView.getChildAdapterPosition(v)).getPath())
                                .fit()
                                .centerCrop()
                                .into(verImagen);
                        alert.setCancelable(false);

                        alert.setView(viewFoto)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        if (viewFoto.getParent() != null) {
                                            ((ViewGroup) viewFoto.getParent()).removeView(viewFoto);
                                        }
                                    }
                                });
                        alert.show();
                    }
                });
                adapter.setLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {
                        if(fabCamara.getVisibility() == View.VISIBLE)
                        {
                            final ProgressDialog progressDialogo = new ProgressDialog(getContext());

                            progressDialogo.setMessage(getString(R.string.borrando_imagen));

                            AlertDialog dialogo = new AlertDialog.Builder(getContext())
                                    .setTitle("")
                                    .setMessage(getString(R.string.estas_seguro_borrar_imagen))
                                    .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ((MainActivity) getActivity()).apiRest.borrarImagen(imagenes.get(recyclerView.getChildAdapterPosition(v)).getId(), new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    progressDialogo.dismiss();
                                                    if (response.isSuccessful()) {
                                                        Toast.makeText(getContext(), getString(R.string.borrar_imagen_exito), Toast.LENGTH_LONG).show();
                                                        imagenes.remove(recyclerView.getChildAdapterPosition(v));
                                                        recyclerView.getAdapter().notifyItemRemoved(recyclerView.getChildAdapterPosition(v));
                                                        recyclerView.getAdapter().notifyDataSetChanged();
                                                        recyclerView.getAdapter().notifyItemRangeChanged(recyclerView.getChildAdapterPosition(v), imagenes.size());

                                                    } else {
                                                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();

                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton(getString(R.string.no), null)
                                    .show();
                            return true;
                        }
                        return false;
                    }
                });
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        } else {
            Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<Animal> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        File imagen = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA) {
                if (data != null) {
                    imagen = FileUtils.fileFromBitmap((Bitmap) data.getExtras().get("data"), 0, getContext());
                }
            } else if (requestCode == GALERIA) {
                if (data != null) {
                    imagen = FileUtils.getFile(getContext(), (Uri) data.getData());
                }
            }
            try {
                progressDialog.show();
                ((MainActivity) getActivity()).apiRest.subirImagen(imagen, Integer.parseInt(animal.getId()), new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), getString(R.string.imagen_exito), Toast.LENGTH_LONG).show();
                            ((MainActivity) getActivity()).apiRest.getAnimales(idAnimal, FragmentVistaAnimal.this);
                            if(recyclerView.getVisibility() == View.GONE)
                            {
                                recyclerView.setVisibility(View.VISIBLE);
                                sinImagenes.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
        }

    }
}
