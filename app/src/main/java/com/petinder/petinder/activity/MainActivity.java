package com.petinder.petinder.activity;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.petinder.petinder.R;
import com.petinder.petinder.fragment.LoginFragment;
import com.petinder.petinder.fragment.MainFragment;
import com.petinder.petinder.modelo.Usuario;
import com.petinder.petinder.task.BuscaDadosUsuarioTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    //Navigation
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private View frameLayout;
    private CircleImageView imagemPerfil;
    private TextView txtNome;
    private TextView txtEmail;

    // Login
    private boolean logado;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentTransaction login = getSupportFragmentManager().beginTransaction();
        login.replace(R.id.frameLayout, new LoginFragment());
        login.commit();

        //Tratar usuario previamente logado
        Boolean logado = EstaLogado();
        if (logado) {
            //Configura Navigation Drawer
            frameLayout = findViewById(R.id.frameLayout);
            configuraNavigationView();
            FragmentTransaction main = getSupportFragmentManager().beginTransaction();
            main.replace(R.id.frameLayout, new MainFragment());
            main.commit();
        }
    }

    public void configuraNavigationView() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        imagemPerfil = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        txtNome = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtNome);
        txtEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtEmail);

        Glide.with(this).load(getResources().getString(R.string.imageservermini) + usuario.getImagemPerfil()).into(imagemPerfil);
        txtNome.setText(usuario.getNome());
        txtEmail.setText(usuario.getEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.chat).setVisible(true);

        if (!logado) {
           menu.findItem(R.id.chat).setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean EstaLogado() {
        try {
            SharedPreferences prefs = getSharedPreferences("Configuracoes", MODE_PRIVATE);
            if (prefs != null) {
                logado = prefs.getBoolean("logado", false);
                usuario = new Usuario();
                usuario.setNome(prefs.getString("nome", ""));
                usuario.setEmail(prefs.getString("email", ""));
                usuario.setImagemPerfil(prefs.getString("imagemperfil", ""));
                buscaDadosPerfil();
                return logado;
            } else {
                logado = false;
                return logado;
            }
        } catch (NullPointerException exception) {
        }
        return false;
    }

    public void buscaDadosPerfil() {
        BuscaDadosUsuarioTask taskDados = new BuscaDadosUsuarioTask(this, usuario);
        taskDados.execute();
    }
}
