package savepet.example.com.savepet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.modelos.Usuario;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static savepet.example.com.savepet.MainActivity.CAMARA;
import static savepet.example.com.savepet.MainActivity.GALERIA;

public class Fragment_registro extends Fragment {
    Button registrarme, imagenCamara, imagenGaleria;
    ImageView imagenPerfil;
    EditText nombreUsuario, nombre, telefono, contraseña, contraseñaRepetida, email;
    boolean fotoCambiada;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro_usuario, container, false);
        registrarme = view.findViewById(R.id.registro);
        imagenCamara = view.findViewById(R.id.imagen_camara);
        imagenGaleria = view.findViewById(R.id.imagen_galeria);
        imagenPerfil = view.findViewById(R.id.imagen_perfil);
        nombre = view.findViewById(R.id.nombre);
        email = view.findViewById(R.id.email);
        nombreUsuario = view.findViewById(R.id.nombre_usuario);
        telefono = view.findViewById(R.id.telefono);
        contraseña = view.findViewById(R.id.contraseña);
        contraseñaRepetida = view.findViewById(R.id.contraseña_repetida);
        final Uri uri_imagen = null;

        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().isEmpty()) {
                    ((MainActivity) getActivity()).generarSnackBar(getString(R.string.nombre_necesario));
                } else if (nombreUsuario.getText().toString().isEmpty()) {
                    ((MainActivity) getActivity()).generarSnackBar(getString(R.string.nombre_usuario_necesario));
                } else if (!contraseña.getText().toString().equals(contraseñaRepetida.getText().toString())) {
                    ((MainActivity) getActivity()).generarSnackBar(getString(R.string.contraseñas_no_coinciden));
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    ((MainActivity) getActivity()).generarSnackBar(getString(R.string.email_invalido));
                } else {
                    File f = null;
                    if (fotoCambiada) {

                        if (uri_imagen == null) {
                            f = new File(getContext().getCacheDir(), nombreUsuario.getText().toString().trim() + ".png");

                            try {
                                f.createNewFile();
                                Bitmap bitmap = ((BitmapDrawable) imagenPerfil.getDrawable()).getBitmap();
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                                byte[] bitmapdata = bos.toByteArray();

                                FileOutputStream fos = new FileOutputStream(f);
                                fos.write(bitmapdata);
                                fos.flush();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            f = new File(uri_imagen.getPath());
                        }
                    }
                    Map<String,String> mapUsuario = new HashMap<>();
                    mapUsuario.put("nombreUsuario", nombreUsuario.getText().toString().trim());
                    mapUsuario.put("nombre",nombre.getText().toString().trim());
                    mapUsuario.put("password",contraseña.getText().toString().trim());
                    mapUsuario.put("email",email.getText().toString().trim());
                    mapUsuario.put("telefono", nombreUsuario.getText().toString().trim());

                    ((MainActivity)getActivity()).apiRest.registrarUsuario(f,mapUsuario, new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if (response.isSuccessful())
                            {
                                Toast.makeText(getContext(),"Usuario creado con éxito",Toast.LENGTH_LONG).show();
                            }
                            else{
                                try {
                                    Toast.makeText(getContext(),response.errorBody().string(),Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {

                        }
                    });
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
            if (requestCode == CAMARA) {
                if (data != null) {
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    imagenPerfil.setImageBitmap(img);
                    /*File file = FileUtils.fileFromBitmap(img, nImage, getContext());
                    nImage++;
                    filesList.add(file);
                    recyclerView.getAdapter().notifyDataSetChanged();*/

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
            }
        }
    }
}
