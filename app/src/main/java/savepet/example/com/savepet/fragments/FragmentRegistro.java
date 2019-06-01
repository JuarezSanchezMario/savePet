package savepet.example.com.savepet.fragments;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import savepet.example.com.savepet.MainActivity;
import savepet.example.com.savepet.R;
import savepet.example.com.savepet.FileUtilidades.Utilidades;
import savepet.example.com.savepet.modelos.Usuario;

import static android.app.Activity.RESULT_OK;
import static savepet.example.com.savepet.MainActivity.CAMERA;
import static savepet.example.com.savepet.MainActivity.GALERIA;

public class FragmentRegistro extends Fragment {
    Button registrarme, imagenCamara, imagenGaleria, borrar;
    ImageView imagenPerfil;
    EditText nombreUsuario, nombre, telefono, contraseña, contraseñaRepetida, email;
    boolean fotoCambiada;
    Usuario usuario;
    boolean esActualizar = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro_usuario, container, false);
        Bundle args = getArguments();
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
        borrar = view.findViewById(R.id.eliminar);
        if (args != null) {
            if (args.containsKey("usuario")) {
                esActualizar = true;
                registrarme.setText(getString(R.string.actualizar));
                email.setVisibility(View.GONE);
                usuario = getArguments().getParcelable("usuario");

            }
        }
        final Uri uri_imagen = null;
        if (usuario != null) {
            borrar.setVisibility(View.VISIBLE);
            telefono.setText(usuario.getTelefono());
            nombreUsuario.setText(usuario.getNombre_usuario());
            nombre.setText(usuario.getNombre());
            registrarme.setText(getString(R.string.usuario_actualizar));
            fotoCambiada = false;
        }
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).apiRest.borrarUsuario(usuario.getId(), new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), getString(R.string.usuario_borrado), Toast.LENGTH_LONG).show();
                            ((MainActivity) getActivity()).sesion_cerrada();
                            ((MainActivity) getActivity()).ponerFragment(new FragmentAnimales(), "fragment_animales", true, null);
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
        });
        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!esActualizar) {
                    if (nombre.getText().toString().isEmpty()) {
                        ((MainActivity) getActivity()).generarSnackBar(getString(R.string.nombre_necesario));
                    } else if (nombreUsuario.getText().toString().isEmpty()) {
                        ((MainActivity) getActivity()).generarSnackBar(getString(R.string.nombre_usuario_necesario));
                    } else if (!contraseña.getText().toString().equals(contraseñaRepetida.getText().toString())) {
                        ((MainActivity) getActivity()).generarSnackBar(getString(R.string.contraseñas_no_coinciden));
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        ((MainActivity) getActivity()).generarSnackBar(getString(R.string.email_invalido));
                    } else if (contraseña.length() < 6) {
                        ((MainActivity) getActivity()).generarSnackBar(getString(R.string.contraseña));
                    } else {
                        File f = null;
                        if (fotoCambiada) {

                            if (uri_imagen == null) {
                                f = Utilidades.BitmapDrawableAFile((BitmapDrawable) imagenPerfil.getDrawable(), getContext(), nombreUsuario.getText().toString().trim());

                            } else {
                                f = new File(uri_imagen.getPath());
                            }
                            Map<String, String> mapUsuario = new HashMap<>();
                            mapUsuario.put("nombre_usuario", nombreUsuario.getText().toString().trim());
                            mapUsuario.put("nombre", nombre.getText().toString().trim());
                            mapUsuario.put("password", contraseña.getText().toString().trim());
                            mapUsuario.put("email", email.getText().toString().trim());
                            mapUsuario.put("telefono", nombreUsuario.getText().toString().trim());
                            if (usuario != null) {
                                ((MainActivity) getActivity()).apiRest.modificarUsuario(f, usuario.getId(), mapUsuario, new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(getContext(), getString(R.string.usuario_modificado), Toast.LENGTH_LONG).show();
                                        } else {
                                            try {
                                                Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                ((MainActivity) getActivity()).apiRest.registrarUsuario(f, mapUsuario, new Callback<Usuario>() {
                                    @Override
                                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(getContext(), getString(R.string.usuario_exito), Toast.LENGTH_LONG).show();
                                            ((MainActivity)getActivity()).sesion_iniciada(response.body());
                                            ((MainActivity)getActivity()).ponerFragment(new FragmentAnimales(),"fragment_animales",true,null);
                                        } else {
                                            try {
                                                Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Usuario> call, Throwable t) {
                                        Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        } else {
                            ((MainActivity) getActivity()).generarSnackBar(getString(R.string.imagen_necesaria));
                        }
                    }
                } else {
                    File f = null;
                    Map<String, String> map = new HashMap<>();
                    if (fotoCambiada) {
                        if (uri_imagen == null) {
                            f = Utilidades.BitmapDrawableAFile((BitmapDrawable) imagenPerfil.getDrawable(), getContext(), nombreUsuario.getText().toString().trim());

                        } else {
                            f = new File(uri_imagen.getPath());
                        }
                    }
                    if (!nombre.getText().toString().isEmpty()) {
                        map.put("nombre", nombre.getText().toString().trim());
                    }
                    if (!telefono.getText().toString().isEmpty()) {
                        map.put("telefono", telefono.getText().toString().trim());
                    }
                    if (!contraseña.getText().toString().isEmpty()) {
                        if (!contraseña.getText().toString().equals(contraseñaRepetida.getText().toString())) {
                            ((MainActivity) getActivity()).generarSnackBar(getString(R.string.contraseñas_no_coinciden));
                        } else {
                            map.put("password", contraseña.getText().toString().trim());
                        }
                    }
                    if (!email.getText().toString().isEmpty()) {
                        map.put("email", email.getText().toString().trim());
                    }
                    Callback<ResponseBody> callback = new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), getString(R.string.usuario_actualizado), Toast.LENGTH_LONG).show();
                                ((MainActivity) getActivity()).ponerFragment(new FragmentAnimales(), "fragment_animales", true, null);
                            } else {
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    };
                    if (fotoCambiada) {
                        ((MainActivity) getActivity()).apiRest.modificarUsuario(f, MainActivity.getUsuario().getId(), map, callback);
                    } else {
                        ((MainActivity) getActivity()).apiRest.modificarUsuario(MainActivity.getUsuario().getId(), map, callback);
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
            }
        }
    }
}
