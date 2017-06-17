package com.petinder.petinder.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

import com.petinder.petinder.R;
import com.petinder.petinder.activity.CadastroUsuarioActivity;
import com.petinder.petinder.activity.MainActivity;
import com.petinder.petinder.modelo.Usuario;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.UsuarioJson;

import java.io.ByteArrayOutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mônica on 15/06/2017.
 */

public class CadastraUsuarioTask extends AsyncTask {

    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/cadastra_usuario_json.php";
    private final String method = "cadastra_usuario-json";
    private Bundle usuarioBundle;
    private ProgressDialog progress;
    private Activity activity;
    private Usuario usuario;
    private Bitmap imagemPerfil;
    private String imagemDecodificada = "";

    public CadastraUsuarioTask(Activity activity, Usuario usuario, Bitmap imagemPerfil){
        this.activity = activity;
        this.usuario = usuario;
        this.imagemPerfil = imagemPerfil;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(activity, "Aguarde...", "Envio de dados para web", true, true);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        //Tratar ImageViwew
        //localiza e transforma em um array de bytes
        if (imagemPerfil != null) {
            ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
            imagemPerfil.compress(Bitmap.CompressFormat.JPEG, 50, bAOS);
            byte[] imagemArrayBytes = bAOS.toByteArray();

            //decodifica com a classe BASE64 e transforma em string
            imagemDecodificada = Base64.encodeToString(imagemArrayBytes, Base64.DEFAULT);
            usuario.setImagemPerfil(imagemDecodificada);
        } else {
            usuario.setImagemPerfil("");
        }

        UsuarioJson json = new UsuarioJson();
        String jsonUsuario = json.UsuarioToJson(usuario);

        String answer = HttpConnection.getSetDataWeb(this.url, this.method, jsonUsuario);

        usuarioBundle = new Bundle();
        usuarioBundle.putSerializable("usuario", usuario);

        return answer;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progress.dismiss();


        Log.i("resultado Json", o.toString());

        if (o.toString().equals("Sucesso")) {

            //AtualizaPrefs
            SharedPreferences prefs = activity.getSharedPreferences("Configuracoes", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("logado", true);
            editor.putString("nome", usuario.getNome());
            editor.putString("email", usuario.getEmail());
            editor.putString("imagemperfil", usuario.getImagemPerfil());
            editor.putString("fcmId", usuario.getFcmIDList().get(0));
            Log.i("fcm> ", usuario.getFcmIDList().get(0));
            editor.commit();

            new AlertDialog.Builder(activity).setTitle(R.string.bemvindo)
                    .setMessage(R.string.cadastrado)
                    .setCancelable(false)
                    .setPositiveButton(R.string.continuar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    })
                    .show();



        } else if (o.toString().equals("EmailJaCadastrado")) {

            new AlertDialog.Builder(activity).setTitle(R.string.atencao)
                    .setMessage(R.string.emailjacadastrado)
                    .setPositiveButton(R.string.okentendi, null)
                    .show();
        } else {
            ((CadastroUsuarioActivity) activity).onErroCadastro();
        }
    }
}
