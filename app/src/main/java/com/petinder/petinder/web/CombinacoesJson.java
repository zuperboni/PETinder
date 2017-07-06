package com.petinder.petinder.web;


import android.util.Log;

import com.petinder.petinder.modelo.Combinacoes;
import com.petinder.petinder.modelo.Pet;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Mônica on 29/06/2017.
 */

public class CombinacoesJson {
    public String CombinacoesToJson(Combinacoes combinacao, int page) {
        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object()
                    .key("cod_pet_atual").value(combinacao.getCodPetAtual())
                  //  .key("cod_pet_fila").value(combinacao.getCodPetFila())
                    .key("like_status").value(combinacao.getLikeStatus())
                    .endObject();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        Log.i("Json = ", jsonStringer.toString());
        return jsonStringer.toString();

    }
    public Combinacoes JsonToCombinacoes(String data) throws JSONException {
        Combinacoes combinacoes= new Combinacoes();
        try {
            JSONObject jo = new JSONObject(data);
                combinacoes.setCodPetAtual(jo.getInt("cod_pet_atual"));
             //   combinacoes.setCodPetFila(jo.getInt("cod_pet_fila"));
                combinacoes.setLikeStatus(jo.getString("like_status"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return combinacoes;
    }
}
