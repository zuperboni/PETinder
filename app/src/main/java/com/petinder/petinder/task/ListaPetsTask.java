package com.petinder.petinder.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.petinder.petinder.fragment.MainFragment;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.PetJson;

import java.util.List;

/**
 * Created by Mônica on 26/06/2017.
 */

public class ListaPetsTask extends AsyncTask {
    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/lista_feed_pet_json.php";
    private String method = "";
    private ProgressDialog progress;
    private int page = 1;
    private Activity activity;
    private MainFragment fragment;
    List<Pet> pets;

    public ListaPetsTask(Activity activity, MainFragment fragment, int page) {
        this.activity = activity;
        this.fragment = fragment;
        this.page = page;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        String answer = "";

        method = "lista_feed_pet-json";

        Pet pet = new Pet();
        PetJson json = new PetJson();
        String data = json.PettoJson(pet,page);
        answer = HttpConnection.getSetDataWeb(this.url, this.method, data);
        Log.i("Resposta", answer);
        PetJson petJson = new PetJson();
        pets = petJson.JsonArrayToListaPets(answer);

        return pets;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(page>1){
           // fragment.updateRecyclerView(pets);
        }else{
            fragment.updateScreen(pets);
        }
    }


}
