package com.petinder.petinder.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.petinder.petinder.R;
import com.petinder.petinder.fragment.LoginFragment;
import com.petinder.petinder.modelo.Usuario;

/**
 * Created by Carla Nescara on 16/01/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction login = getSupportFragmentManager().beginTransaction();
        login.replace(R.id.frameLayout, new LoginFragment());
        login.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void Entrar() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main, new LoginFragment());
        //  transaction.addToBackStack(null);
        transaction.commit();
    }

    /*
    public void RecuperarSenha() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main, new RecuperarSenhaFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void CadastroComplementarFirebase(Usuario usuario) {
        this.usuarioLogado = usuario;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main, new CadastroComplementarFirebase());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    */
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}