package com.petinder.petinder.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.petinder.petinder.fragment.ConversasFragment;
import com.petinder.petinder.modelo.Combinacoes;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.MensagemJson;
import com.petinder.petinder.web.PetJson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mônica on 02/07/2017.
 */

public class BuscaConversasTask extends AsyncTask<Object, Object, Object> {
    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/chat_json.php";
    private String method1;
    private String method2;
    private String data;
    private Activity activity;
    private Pet pet;
    private Fragment fragment;
    private List[] lista;

    public BuscaConversasTask(Activity activity, Pet pet, Fragment fragment) {
        this.activity = activity;
        this.pet = pet;
        this.fragment = fragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object... params) {
        PetJson petJson = new PetJson();
        data = petJson.PettoJson(pet);
        method1 = "busca_conversas-json";
        method2="busca_combinacoes-json";

        String answer = HttpConnection.getSetDataWeb(this.url, this.method1, this.data);
        Log.i("Resposta ===", answer);
        MensagemJson msgJson = new MensagemJson();
        List<Bundle> bundles = msgJson.JsonToListaConversas(answer);

        String answer2 = HttpConnection.getSetDataWeb(this.url, this.method2, this.data);
        Log.i("Resposta2 ===", answer2);
        Gson gson= new Gson();
        Type listType = new TypeToken<ArrayList<Combinacoes>>(){}.getType();
        List<Combinacoes> bundlecombinacoes = gson.fromJson(answer2, listType);


        lista = new List[] { bundles, bundlecombinacoes };
        return lista;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(fragment instanceof ConversasFragment){
            ((ConversasFragment) fragment).atualizaLista(lista[0],lista[1]);
        }
    }
}
