package com.petinder.petinder.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.petinder.petinder.activity.SelecaoActivity;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.util.HttpConnection;

/**
 * Created by Carla on 14/07/2017.
 */

public class BuscaPetsUsuarioTask extends AsyncTask {
    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/buscar_pets_usuario_json.php";
    private final String method = "buscar_pets_usuario-json";
    private ProgressDialog progress;
    private Context context;
    private Boolean editar;

    public BuscaPetsUsuarioTask(Context context, Boolean editar) {
        this.context = context;
        this.editar = editar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(context, "", "Aguarde...", true, true);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String answer = HttpConnection.getSetDataWeb(this.url, this.method, "");
        Log.i("Resposta = ", answer);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Pet>>(){}.getType();
        ArrayList<Pet> pets = gson.fromJson(answer, listType);
        return pets;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progress.dismiss();
        try {
            ArrayList<Pet> pets = (ArrayList<Pet>) o;
            ((SelecaoActivity) context).AtualizaListaPets(pets, editar);
        } catch (Exception ex) {
            Log.i("erro", ex.toString());
        }
    }
}
