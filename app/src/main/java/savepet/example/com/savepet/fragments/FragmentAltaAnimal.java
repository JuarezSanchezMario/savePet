package savepet.example.com.savepet.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.Utilidades;
import savepet.example.com.savepet.maps;
import savepet.example.com.savepet.modelos.Animal;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static savepet.example.com.savepet.MainActivity.GALERIA;
import static savepet.example.com.savepet.MainActivity.MAPS;

public class FragmentAltaAnimal extends Fragment {
    private Button registrarme, imagenCamara, imagenGaleria;
    private ImageView imagenPerfil;
    private ProgressDialog progressDialog;
    private EditText nombre, fechaNacimiento, descripcionLarga, descripcionCorta, raza,localizacion;
    private Spinner tipoAnimal;
    private boolean fotoCambiada;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro_animal, container, false);
        registrarme = view.findViewById(R.id.registro);
        imagenCamara = view.findViewById(R.id.imagen_camara);
        descripcionLarga = view.findViewById(R.id.descripcion_larga);
        descripcionCorta = view.findViewById(R.id.descripcion_corta);
        imagenGaleria = view.findViewById(R.id.imagen_galeria);
        imagenPerfil = view.findViewById(R.id.imagen_perfil);
        localizacion = view.findViewById(R.id.localizacion);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.mensaje_alta_animal));
        nombre = view.findViewById(R.id.nombre);
        fechaNacimiento = view.findViewById(R.id.fecha_nacimiento);
        raza = view.findViewById(R.id.raza);

        tipoAnimal = view.findViewById(R.id.tipo_animal);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tipo_animal));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoAnimal.setAdapter(dataAdapter);

        localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), maps.class), MAPS);
            }
        });
        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "-" + month + "-" + year;
                fechaNacimiento.setText(date);
            }
        };
        final Uri uriImagen = null;

        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().isEmpty()) {
                    ((MainActivity) getActivity()).generarSnackBar(getString(R.string.nombre_necesario));
                } else {
                    File f = null;
                    if (fotoCambiada) {

                        if (uriImagen == null) {

                            f = Utilidades.BitmapDrawableAFile((BitmapDrawable) imagenPerfil.getDrawable(), getContext(), nombre.getText().toString().trim());
                        } else {
                            f = new File(uriImagen.getPath());
                        }
                        SimpleDateFormat stringData = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date fecha = new Date();
                        try{
                            fecha = stringData.parse(fechaNacimiento.getText().toString().trim());
                        }catch (ParseException e)
                        {

                        }
                        Map<String, String> mapAnimal = new HashMap<>();
                        mapAnimal.put("raza", raza.getText().toString().trim());
                        mapAnimal.put("nombre", nombre.getText().toString().trim());
                        mapAnimal.put("fecha_nacimiento",stringData.format(fecha).trim());
                        mapAnimal.put("tipo", tipoAnimal.getSelectedItem().toString().trim());
                        mapAnimal.put("estado", "adopcion");
                        mapAnimal.put("descripcion_corta", descripcionCorta.getText().toString().trim());
                        mapAnimal.put("descripcion_larga", descripcionLarga.getText().toString().trim());
                        if (!localizacion.getText().toString().equals("")) {
                            mapAnimal.put("lat", localizacion.getText().toString().trim().split(",")[0]);
                            mapAnimal.put("lng", localizacion.getText().toString().trim().split(",")[1]);

                        }

                        progressDialog.show();
                        ((MainActivity) getActivity()).apiRest.registrarAnimal(f, mapAnimal, new Callback<Animal>() {
                            @Override
                            public void onResponse(Call<Animal> call, Response<Animal> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getContext(), "Animal creado con éxito", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Animal> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });
                    } else {
                        ((MainActivity) getActivity()).generarSnackBar(getString(R.string.imagen_necesaria));
                    }

                }
            }
        });
        imagenGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(abrirGaleria, GALERIA);
            }
        });
        imagenCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camara, CAMERA);
            }
        });
        return view;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImage;
        if (resultCode == RESULT_OK) {
            fotoCambiada = true;
            if (requestCode == CAMERA) {
                if (data != null) {
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    imagenPerfil.setImageBitmap(img);
                }
            } else if (requestCode == GALERIA) {
                if (data != null) {
                    selectedImage = data.getData();

                    InputStream imageStrem = null;
                    try {
                        imageStrem = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap img = BitmapFactory.decodeStream(imageStrem);
                    Bitmap compressImg = Bitmap.createScaledBitmap(img, 95, 95, true);
                    imagenPerfil.setImageBitmap(compressImg);
                }
            } else {
                localizacion.setText(data.getExtras().get("lat") + "," + data.getExtras().get("lng"));
            }
        }
    }

}
