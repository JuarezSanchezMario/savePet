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
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.TimerPickerFragment;
import savepet.example.com.savepet.FileUtilidades.Utilidades;
import savepet.example.com.savepet.maps;
import savepet.example.com.savepet.modelos.Evento;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static savepet.example.com.savepet.MainActivity.GALERIA;
import static savepet.example.com.savepet.MainActivity.MAPS;

public class FragmentAltaEvento extends Fragment {
    private Button registrarme, imagenCamara, imagenGaleria;
    private ImageView imagen;
    private ProgressDialog progressDialog;
    private EditText nombre, fechaEvento, localizacion, aforo,descripcion;
    private boolean fotoCambiada;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private boolean esActualizar;
    private Evento eventoActualizar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro_eventos, container, false);

        registrarme = view.findViewById(R.id.registro);
        imagenCamara = view.findViewById(R.id.imagen_camara);
        imagenGaleria = view.findViewById(R.id.imagen_galeria);
        descripcion = view.findViewById(R.id.descripcion);
        imagen = view.findViewById(R.id.imagen);
        localizacion = view.findViewById(R.id.localizacion);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.registrando_evento));
        nombre = view.findViewById(R.id.nombre);
        fechaEvento = view.findViewById(R.id.fecha);
        aforo = view.findViewById(R.id.aforo);

        localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), maps.class), MAPS);
            }
        });
        if (getArguments() != null) {
            eventoActualizar = getArguments().getParcelable("actualizar");
            esActualizar = true;
            fotoCambiada = true;
            registrarme.setText(getString(R.string.actualizar));
            Picasso.get()
                    .load(eventoActualizar.getImagen())
                    .fit()
                    .placeholder(R.drawable.usuario_default)
                    .error(R.drawable.error_imagen)
                    .centerCrop()
                    .into(imagen);
            aforo.setText(eventoActualizar.getAforo());
            nombre.setText(eventoActualizar.getNombre());
            descripcion.setText(eventoActualizar.getDescripcion());
            localizacion.setText(eventoActualizar.getLat() + "," + eventoActualizar.getLng());
            SimpleDateFormat stringFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha_mensaje = new Date();
            try {
                fecha_mensaje = stringFecha.parse(eventoActualizar.getFecha());
            } catch (ParseException e) {

            }
            stringFecha = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            fechaEvento.setText(stringFecha.format(fecha_mensaje));
        }


        fechaEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaEvento.setText("");
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

                String mes = month + "";
                String dia = day + "";

                if (mes.length() < 2) mes = "0" + month;
                if (dia.length() < 2) dia = "0" + day;

                fechaEvento.setText(dia + "-" + mes + "-" + year);

                DialogFragment timePicker = new TimerPickerFragment(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hora = hourOfDay + "";
                        String minutos = minute + "";

                        if (hora.length() < 2) hora = "0" + hourOfDay;
                        if (minutos.length() < 2) minutos = "0" + minute;
                        fechaEvento.append(" " + hora + ":" + minutos + ":00");
                    }
                });
                timePicker.show(getFragmentManager(), "time picker");
            }
        };
        final Uri uriImagen = null;

        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().isEmpty()) {
                    ((MainActivity) getActivity()).generarSnackBar(getString(R.string.nombre_necesario));
                } else if (fechaEvento.getText().toString().isEmpty()) {
                    ((MainActivity) getActivity()).generarSnackBar(getString(R.string.fecha_necesaria));
                } else if (localizacion.getText().toString().isEmpty()) {
                    ((MainActivity) getActivity()).generarSnackBar(getString(R.string.localizacion_necesaria));
                } else if (aforo.getText().toString().isEmpty()) {
                    ((MainActivity) getActivity()).generarSnackBar(getString(R.string.aforo_necesario));
                } else {
                    File f = null;
                    if (fotoCambiada) {

                        if (uriImagen == null) {

                            f = Utilidades.BitmapDrawableAFile((BitmapDrawable) imagen.getDrawable(), getContext(), nombre.getText().toString().trim());
                        } else {
                            f = new File(uriImagen.getPath());
                        }
                        SimpleDateFormat stringData = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                        Date fecha = new Date();
                        try {
                            fecha = stringData.parse(fechaEvento.getText().toString().trim());
                        } catch (ParseException e) {

                        }
                        stringData = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Map<String, String> mapEvento = new HashMap<>();
                        mapEvento.put("nombre", nombre.getText().toString().trim());
                        mapEvento.put("fecha", stringData.format(fecha).trim());
                        mapEvento.put("aforo", aforo.getText().toString().trim());
                        mapEvento.put("descripcion", descripcion.getText().toString().trim());
                        if (!localizacion.getText().toString().isEmpty()) {
                            mapEvento.put("lat", localizacion.getText().toString().trim().split(",")[0]);
                            mapEvento.put("lng", localizacion.getText().toString().trim().split(",")[1]);

                        }

                        progressDialog.show();
                        if (!esActualizar) {
                            ((MainActivity) getActivity()).apiRest.registrarEvento(f, mapEvento, new Callback<Evento>() {
                                @Override
                                public void onResponse(Call<Evento> call, Response<Evento> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), getString(R.string.evento_creado_exito), Toast.LENGTH_LONG).show();
                                        ((MainActivity) getActivity()).ponerFragment(new FragmentEventos(), "fragment_eventos", true, null);
                                    } else {
                                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<Evento> call, Throwable t) {
                                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            });
                        } else {
                            ((MainActivity) getActivity()).apiRest.modificarEvento(f, Integer.parseInt(eventoActualizar.getId()), mapEvento, new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), getString(R.string.evento_actualizado), Toast.LENGTH_LONG).show();
                                        ((MainActivity) getActivity()).ponerFragment(new FragmentEventos(), "fragment_eventos", true, null);
                                    } else {
                                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }

                    } else {
                        ((MainActivity) getActivity()).generarSnackBar(getString(R.string.imagen_necesaria));
                        progressDialog.dismiss();
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
                    imagen.setImageBitmap(img);
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
                    imagen.setImageBitmap(compressImg);
                }
            } else {
                localizacion.setText(data.getExtras().get("lat") + "," + data.getExtras().get("lng"));
            }
        }
    }


}
