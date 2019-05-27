package savepet.example.com.savepet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import savepet.example.com.savepet.api.ApiRest;
import savepet.example.com.savepet.fragments.FragmentAnimales;
import savepet.example.com.savepet.fragments.FragmentEventos;
import savepet.example.com.savepet.fragments.FragmentInicioSesion;
import savepet.example.com.savepet.fragments.FragmentMensajes;
import savepet.example.com.savepet.fragments.FragmentRecyclerUsuarios;
import savepet.example.com.savepet.fragments.FragmentRegistro;
import savepet.example.com.savepet.modelos.Usuario;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int CAMERA = 1;
    public static int GALERIA = 2;
    public ApiRest apiRest;
    public boolean sesionIniciada = false;
    public Toolbar toolbar;
    public static Usuario usuario = null;
    public NavigationView navigationView;
    static final int FINE_LOCATION_PERMISOS = 1;
    public static final int MAPS = 3;
    static int LOCALIZACION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        apiRest = new ApiRest(getString(R.string.url), this);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ponerFragment(new FragmentAnimales(), "animales", false, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            compruebaPermisos();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {
            ponerFragment(new FragmentInicioSesion(), "login", false, null);
        } else if (id == R.id.cerrar_sesion) {
            sesion_cerrada();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle args = new Bundle();

        if (id == R.id.animales) {
            ponerFragment(new FragmentAnimales(), "recycler_animales", true, null);
        } else if (id == R.id.usuarios) {
            ponerFragment(new FragmentRecyclerUsuarios(), "recycler_usuarios", false, null);
        } else if (usuario != null) {
            if (id == R.id.eventos) {
                ponerFragment(new FragmentEventos(), "recycler_eventos", true, null);
            } else if (id == R.id.preferencias_cuenta) {
                if (getUsuario() != null) args.putParcelable("usuario", getUsuario());
                ponerFragment(new FragmentRegistro(), "recycler_preferencias", true, args); //TODO
            } else {
                ponerFragment(new FragmentMensajes(), "recycler_mensajes", true, null);
            }
        } else {
            generarSnackBar(getString(R.string.necesitas_login));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean comprobarAcceso(String tag) {
        if (!sesionIniciada && (tag.equalsIgnoreCase(getString(R.string.fragment_alta_animales)))) {
            generarSnackBar(getString(R.string.necesitas_login));
            return false;
        } else {
            return true;
        }
    }

    public void ponerFragment(Fragment fragment, String tag, boolean limpiarFragments, Bundle argumentos) {
        if (comprobarAcceso(tag)) {

            FragmentManager FM = getSupportFragmentManager();
            if (limpiarFragments) FM.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction FT = FM.beginTransaction();
            if (argumentos != null) fragment.setArguments(argumentos);
            FT.replace(R.id.fragment_container, fragment, tag);
            FT.addToBackStack(null);
            FT.commit();
        }
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public void sesion_iniciada(Usuario usuario) {
        ImageView fotoNavigation;
        TextView nombreNavigation, emailNavigation;
        View navigation = navigationView.getHeaderView(0);
        sesionIniciada = true;
        toolbar.getMenu().getItem(0).setVisible(false);
        toolbar.getMenu().getItem(1).setTitle(usuario.getNombre_usuario());
        toolbar.getMenu().getItem(1).setVisible(true);
        Toast.makeText(this, getString(R.string.bienvenido) + " " + usuario.getNombre_usuario(), Toast.LENGTH_LONG).show();

        fotoNavigation = navigation.findViewById(R.id.foto_navigation);
        nombreNavigation = navigation.findViewById(R.id.nombre_navigation);

        fotoNavigation.setVisibility(View.VISIBLE);
        nombreNavigation.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(usuario.getImagen_perfil())
                .fit()
                .placeholder(R.drawable.animal_default)
                .error(R.drawable.not_found)
                .centerCrop()
                .into(fotoNavigation);

        nombreNavigation.setText(usuario.getNombre());
        this.usuario = usuario;
    }

    public void sesion_cerrada() {
        ImageView fotoNavigation;
        TextView nombreNavigation;
        View navigation = navigationView.getHeaderView(0);
        sesionIniciada = false;
        usuario = null;
        toolbar.getMenu().getItem(0).setVisible(true);
        toolbar.getMenu().getItem(1).setVisible(false);

        fotoNavigation = navigation.findViewById(R.id.foto_navigation);
        nombreNavigation = navigation.findViewById(R.id.nombre_navigation);

        fotoNavigation.setImageResource(android.R.color.transparent);
        nombreNavigation.setText("");
        fotoNavigation.setVisibility(View.GONE);
        nombreNavigation.setVisibility(View.GONE);

        ponerFragment(new FragmentAnimales(),"fragment_animales",true,null);
    }

    public void generarSnackBar(String mensaje) {
        Snackbar.make(findViewById(android.R.id.content), mensaje, Snackbar.LENGTH_LONG)
                .show();
    }

    private void compruebaPermisos() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    },
                    1052);

        }

    }
}
