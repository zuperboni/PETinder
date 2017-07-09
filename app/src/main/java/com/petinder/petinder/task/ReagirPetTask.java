package com.petinder.petinder.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
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
        Gson gson = new Gson();
        method = "combinacao_pet-json";

        //String data = json.CombinacoesToJson(combinacao,page);
        String data = gson.toJson(combinacao);
        answer = HttpConnection.getSetDataWeb(this.url, this.method, data);
        Log.i("Resposta", answer);
        Pet pet= gson.fromJson(answer, Pet.class);

        return pet;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
       // Verificar aqui se deu match
        Pet pet =   (Pet) o;
        if(pet.getCodPet()>0){
           fragment.notificaMatch(pet);
        }

        else{

        }
    }

}
