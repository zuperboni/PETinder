package com.petinder.petinder.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.petinder.petinder.activity.CadastroPetActivity;
import com.petinder.petinder.modelo.Raca;
import com.petinder.petinder.util.HttpConnection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mônica on 09/07/2017.
 */

public class ListaRacasTask extends AsyncTask {
    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/consulta_racas_json.php";
    private final String method = "lista_racas-json";
    private ProgressDialog progress;
    private Context context;

    public ListaRacasTask(Context context) {
        this.context = context;
    }
    @Override
protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(context, "","Aguarde...", true, true);
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        String answer = HttpConnection.getSetDataWeb(this.url, this.method, "");
        Log.i("Resposta = ", answer);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Raca>>(){}.getType();
        List<Raca> racas = gson.fromJson(answer, listType);
        return racas;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progress.dismiss();
        try {
            List<Raca> racas = (List<Raca>) o;
            ((CadastroPetActivity) context).AtualizaListaPets(racas);
        }
        catch (Exception ex) {Log.i("erro", ex.toString());}
    }
}
