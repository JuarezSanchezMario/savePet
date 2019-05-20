package savepet.example.com.savepet;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import savepet.example.com.savepet.api.ApiRest;
import savepet.example.com.savepet.fragments.FragmentAnimales;
import savepet.example.com.savepet.fragments.Fragment_inicio_sesion;
import savepet.example.com.savepet.modelos.Usuario;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int CAMERA = 1;
    public static int GALERIA = 2;
    public ApiRest apiRest;
    public boolean sesionIniciada = false;
    public Toolbar toolbar;
    public Usuario usuario = null;
    public NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        apiRest = new ApiRest(getString(R.string.url),this);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.animales);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ponerFragment(new FragmentAnimales(),"animales",false);
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
            ponerFragment( new Fragment_inicio_sesion(),"login",false);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.animales) {
            //FragmentRecycler_animales animales = new FragmentRecycler_animales();
           ponerFragment(new FragmentAnimales(),"recycler_animales",false);
        } else if (id == R.id.usuarios) {

        } else if (id == R.id.eventos) {

        } else if (id == R.id.mensajes) {

        } else if (id == R.id.preferencias_cuenta) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean comprobarAcceso(String tag)
    {
        if(!sesionIniciada && (tag.equalsIgnoreCase(getString(R.string.fragment_alta_animales))))
        {
            generarSnackBar(getString(R.string.necesitas_login));
            return false;
        }
        else{
            return true;
        }
    }

    public void ponerFragment(Fragment fragment, String tag, boolean limpiarFragments) {
        if (comprobarAcceso(tag)) {

            FragmentManager FM = getSupportFragmentManager();
            if (limpiarFragments) FM.getFragments().clear();
            FragmentTransaction FT = FM.beginTransaction();
            FT.replace(R.id.fragment_container, fragment, tag);
            FT.addToBackStack(null);
            FT.commit();
        }
    }
    public Usuario getUsuario()
    {
        return usuario;
    }
    public void sesion_iniciada(Usuario usuario)
    {
        ImageView fotoNavigation;
        TextView nombreNavigation,emailNavigation;
        View navigation =  navigationView.getHeaderView(0);
        sesionIniciada = true;
        toolbar.getMenu().getItem(0).setVisible(false);
        toolbar.getMenu().getItem(1).setTitle(usuario.getNombre_usuario());
        toolbar.getMenu().getItem(1).setVisible(true);
        Toast.makeText(this,getString(R.string.bienvenido)+" "+ usuario.getNombre_usuario(),Toast.LENGTH_LONG).show();

        fotoNavigation = navigation.findViewById(R.id.foto_navigation);
        nombreNavigation = navigation.findViewById(R.id.nombre_navigation);
        emailNavigation = navigation.findViewById(R.id.email_navigation);

        Picasso.get()
                .load(usuario.getImagen_perfil())
                .fit()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.not_found)
                .centerCrop()
                .into(fotoNavigation);

        nombreNavigation.setText(usuario.getNombre());
        emailNavigation.setText(usuario.getEmail());
        this.usuario = usuario;


    }
    public void generarSnackBar(String mensaje)
    {
        Snackbar.make(getWindow().getDecorView().getRootView(),mensaje, Snackbar.LENGTH_LONG)
                .show();
    }
}
