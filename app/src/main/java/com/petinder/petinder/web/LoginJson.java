package com.petinder.petinder.web;

import android.util.Log;

import com.petinder.petinder.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Carla Nescara on 16/01/2018.
 */

public class LoginJson {

    private String resposta = "";

    public String loginToJson(String email, String senha, String gcmId) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("email", email);
            jo.put("senha", senha);
            jo.put("gcmId", gcmId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Json = ", jo.toString());
        return jo.toString();
    }

    public Object loginToString(String data) {

        Usuario usuario = new Usuario();

        try {
            JSONObject jo = new JSONObject(data);

            usuario.setEmail(jo.getString("email"));
            usuario.setNome(jo.getString("nome"));
            usuario.setImagemPerfil(jo.getString("foto"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usuario;


    }

    public String logoffToJson(String email, String gcmId) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("email", email);
            jo.put("gcmId", gcmId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }


}
