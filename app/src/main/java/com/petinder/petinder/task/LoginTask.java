package com.petinder.petinder.task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.petinder.petinder.R;
import com.petinder.petinder.activity.LoginActivity;
import com.petinder.petinder.activity.MainActivity;
import com.petinder.petinder.modelo.Usuario;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.LoginJson;

/**
 * Created by Carla Nescara on 16/01/2018.
 */

public class LoginTask extends AsyncTask {
    private final String url = "http://www.4runnerapp.com.br/AppJobs/Ctrl/login_json.php";
    private final String method = "login-json";
    private final MainActivity activity;
    private String data;
    private ProgressDialog progress;

    public LoginTask(String data, MainActivity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(activity, "Aguarde...", "Envio de dados para web", true, true);
    }

    @Override
    protected Object doInBackground(Object... params) {

        if (HttpConnection.isNetworkAvailable(activity)) {
            Log.i("CONEXAO", "CONECTADO");

            String answer = HttpConnection.getSetDataWeb(this.url, this.method, this.data);
            Log.i("Script", "ANSWER: " + answer);

            if (answer.contains("senhaincorreta")) {
                String resposta = "senhaIncorreta";
                return resposta;
            } else if (answer.contains("naolocalizado")) {
                String resposta2 = "naoLocalizado";
                return resposta2;
            } else if (!answer.equals("")) {
                Object resposta = new LoginJson().loginToString(answer);


                Usuario usuarioLogado;
                usuarioLogado = (Usuario) resposta;

                if (!usuarioLogado.getEmail().equals("")) {

                    SharedPreferences prefs = activity.getSharedPreferences("Configuracoes", activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("logado", true);
                    editor.putString("nome", usuarioLogado.getNome());
                    editor.putString("email", usuarioLogado.getEmail());
                    editor.putString("imagemperfil", usuarioLogado.getImagemPerfil());

                    editor.commit();
                    Log.i("preferencias ==== ", prefs.getString("perfil", ""));

                    return usuarioLogado;
                } else {
                    return "erro";
                }


            } else {
                Log.i("CONECXAO", "DESCONECTADO");
                return "erroConexao";
            }

        } else {
            Log.i("CONECXAO", "DESCONECTADO");
            return "erroConexao";
        }

    }

    @Override
    protected void onPostExecute(Object resultado) {
        super.onPostExecute(resultado);
        progress.dismiss();

        if (resultado.equals("erroConexao")) {
            Toast.makeText(activity, R.string.conexaoIndosponivel, Toast.LENGTH_LONG).show();

        } else if (resultado.equals("senhaIncorreta") || resultado.equals("naoLocalizado")) {
            Toast.makeText(activity, R.string.dadosInvalidos, Toast.LENGTH_SHORT).show();

        } else if (resultado instanceof Usuario) {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            activity.finish();
        }


    }
}