package com.petinder.petinder.web;

import android.util.Log;

import com.petinder.petinder.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Mônica on 15/06/2017.
 */

public class UsuarioJson {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    DateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String UsuarioToJson(Usuario usuario){

        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object()
                    .key("nome").value(usuario.getNome())
                    .key("email").value(usuario.getEmail())
                    .key("imagemPerfil").value(usuario.getImagemPerfil())
                    .key("localizacao").value(usuario.getLocalizacao())
                    .key("senha").value(usuario.getSenha())
                    .key("raioBusca").value(usuario.getRaioBusca())
                    .key("dtNasc").value(usuario.getDtNasc())
                    .key("fcmIdAtual").value(usuario.getFcmIdAtual());

            List<String> fcmIds=usuario.getFcmIDList();
                    for(String item: fcmIds){
                        jsonStringer.object()
                        .key("fcmId").value(item)
                        .endObject();
                    }

            jsonStringer.endArray()
                    .endObject();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        Log.i("Json = ", jsonStringer.toString());
        return jsonStringer.toString();
    }
    public Usuario JsonToUsuario(String data) {
        Usuario usuario = new Usuario();

        try {
            JSONObject jo = new JSONObject(data);
            usuario.setEmail(jo.getString("email"));
            usuario.setNome(jo.getString("nome"));
            usuario.setImagemPerfil(jo.getString("imagemPerfil"));
            usuario.setSenha(jo.getString("senha"));
            usuario.setDtNasc(sqlDateFormat.parse(jo.getString("dtNasc")));
            usuario.setFcmIdAtual(jo.getString("fcmIdAtual"));
            usuario.setRaioBusca(jo.getInt("raioBusca"));
            usuario.setLocalizacao(jo.getString("localizacao"));
            //Verificar se eh necessario receber o array de fcm aqui

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    return usuario;
    }

}
