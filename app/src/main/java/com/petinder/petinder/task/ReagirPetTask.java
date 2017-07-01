package com.petinder.petinder.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.petinder.petinder.fragment.MainFragment;
import com.petinder.petinder.modelo.Combinacoes;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.CombinacoesJson;
import com.petinder.petinder.web.PetJson;

import org.json.JSONException;

/**
 * Created by Mônica on 29/06/2017.
 */

public class ReagirPetTask extends AsyncTask {
    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/reage_pet_json.php";
    private String method = "";
    private ProgressDialog progress;
    private int page = 1;
    private Activity activity;
    private MainFragment fragment;
    private Combinacoes combinacao;

    public ReagirPetTask(MainFragment fragment,int page, Combinacoes combinacao) {
        this.activity= activity;
        this.fragment = fragment;
        this.combinacao=combinacao;
        this.page=page;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String answer = "";

        method = "combinacao_pet-json";

        CombinacoesJson json = new CombinacoesJson();
        String data = json.CombinacoesToJson(combinacao,page);
        answer = HttpConnection.getSetDataWeb(this.url, this.method, data);
        Log.i("Resposta", answer);
      /*
        CombinacoesJson combinacoesJson = new CombinacoesJson();
        try {
            combinacao = combinacoesJson.JsonToCombinacoes(answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    */
        return answer;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
       // Verificar aqui se deu match
        String resposta =   (String) o;
        if(resposta.equals("match")){
           fragment.notificaMatch(resposta,combinacao.getCodPetFila());
        }
        else if (resposta.equals("naoMatch")){
            
        }
        else{
            // Ocorreu um erro!
        }
    }

}
