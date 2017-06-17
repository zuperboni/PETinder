package com.petinder.petinder.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.petinder.petinder.activity.CadastroUsuarioActivity;
import com.petinder.petinder.activity.MainActivity;
import com.petinder.petinder.modelo.Usuario;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.UsuarioJson;

/**
 * Created by Mônica on 15/06/2017.
 */

public class BuscaDadosUsuarioTask extends AsyncTask {
    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/busca_dados_usuario_json.php";
    private String method = "busca_dados-json";
    private Activity activity;
    private Usuario usuario;


    public BuscaDadosUsuarioTask(MainActivity activity, Usuario usuario) {
        this.usuario = usuario;
        this.activity = activity;
    }

    public BuscaDadosUsuarioTask(CadastroUsuarioActivity activity, Usuario usuario) {
        this.usuario = usuario;
        this.activity = activity;
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        UsuarioJson json = new UsuarioJson();
        String data = json.UsuarioToJson(usuario);

        String answer = HttpConnection.getSetDataWeb(this.url, this.method, data);
        Log.i("RespDadosUsuario", answer);

        UsuarioJson usuarioJson = new UsuarioJson();
        Usuario usuarioresp = usuarioJson.JsonToUsuario(answer);

        return usuarioresp;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
/*
        Usuario usuarioresp = (Usuario) o;
        if (activity instanceof CadastroUsuarioActivity) {
            ((CadastroUsuarioActivity) activity).PreencheCampos(usuarioresp);
        } else if (activity instanceof MainActivity) {
            ((MainActivity) activity).recebeDadosPerfil(usuarioresp);
        }
        */
    }

}
