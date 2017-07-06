package com.petinder.petinder.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.petinder.petinder.fragment.ConversasFragment;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.MensagemJson;
import com.petinder.petinder.web.PetJson;

import java.util.List;

/**
 * Created by Mônica on 02/07/2017.
 */

public class BuscaConversasTask extends AsyncTask<Object, Object, Object> {
    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/chat_json.php";
    private String method;
    private String data;
    private Activity activity;
    private Pet pet;
    private Fragment fragment;

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
    protected List<Bundle> doInBackground(Object... params) {
        PetJson petJson = new PetJson();
        data = petJson.PettoJson(pet);
        method = "busca_conversas-json";

        String answer = HttpConnection.getSetDataWeb(this.url, this.method, this.data);
        Log.i("Resposta ===", answer);
        MensagemJson msgJson = new MensagemJson();
        List<Bundle> bundles = msgJson.JsonToListaConversas(answer);

        return bundles;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(fragment instanceof ConversasFragment){
            ((ConversasFragment) fragment).atualizaLista((List<Bundle>)o);
        }
    }
}
