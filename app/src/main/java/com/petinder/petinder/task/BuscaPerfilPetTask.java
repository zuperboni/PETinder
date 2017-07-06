package com.petinder.petinder.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.petinder.petinder.fragment.MainFragment;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.modelo.Usuario;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.PetJson;

/**
 * Created by Mônica on 03/07/2017.
 */

public class BuscaPerfilPetTask extends AsyncTask {
    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/lista_feed_pet_json.php";
    private String method = "busca_perfil_pet-json";
    private MainFragment fragment;
    private Pet pet;

    public BuscaPerfilPetTask(MainFragment fragment, Pet pet) {
        this.fragment = fragment;
        this.pet = pet;

    }
    @Override
    protected Object doInBackground(Object[] objects) {
        String answer = "";
        Gson gson = new Gson();
        //String data = json.PettoJson(pet);
        String data = gson.toJson(pet);
        answer = HttpConnection.getSetDataWeb(this.url, this.method, data);
        Log.i("Resposta", answer);
        Pet pet= gson.fromJson(answer, Pet.class);

        return pet;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

    }

}
