package com.example.guimoye.ledyagenda;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guimoye.ledyagenda.Agenda.ConsultaFragment;
import com.example.guimoye.ledyagenda.Asistencia.AsistenciaFragment;
import com.example.guimoye.ledyagenda.Loggin.LoginActivity;
import com.example.guimoye.ledyagenda.Perfil.PerfilFragment;
import com.example.guimoye.ledyagenda.Usuarios.UsursFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String tipo,usuario;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Bundle bolsa = getIntent().getExtras();
        usuario =   bolsa.getString("usuario");
        tipo    =   bolsa.getString("tipo");


        // para abrir fragment automaticamente
        navigationView.setCheckedItem(R.id.nav_usuarios);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new UsursFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        salirSistema();
    }


    public void salirSistema(){

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Mensaje de Alerta");
        ab.setMessage("¿Deseas Cerrar Sesion?");
        ab.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = ab.create();
        ad.show();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getFragmentManager();
        bundle       = new Bundle();

        if (id == R.id.nav_perfil) {

            bundle.putString("correo",usuario);
            Fragment fragobj = new PerfilFragment();
            fragobj.setArguments(bundle);
            fm.beginTransaction().replace(R.id.content_frame, fragobj).commit();

        } else if (id == R.id.nav_usuarios) {
            fm.beginTransaction().replace(R.id.content_frame, new UsursFragment()).commit();
        }else if (id == R.id.nav_consultar) {
           fm.beginTransaction().replace(R.id.content_frame, new ConsultaFragment()).commit();
        }else if (id == R.id.nav_asistencia) {
            fm.beginTransaction().replace(R.id.content_frame, new AsistenciaFragment()).commit();
        } else if (id == R.id.nav_cerrar) {
            salirSistema();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
