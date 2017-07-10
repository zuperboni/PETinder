package com.petinder.petinder.web;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.modelo.PetAlbum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mônica on 26/06/2017.
 */

public class PetGson {
    //Utilizado no Gson
    private ArrayList<Pet> pets;

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    DateFormat sqlDateHrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //**

    //Utilizados no Json
    public String PettoJson(Pet pet, int page) {

        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object()
              .key("codPet").value(pet.getCodPet())
                    .key("nome").value(pet.getNome())
                    .key("idade").value(pet.getIdade())
                    .key("sexo").value(pet.getSexo())
                    .key("sobre").value(pet.getSobre())
                    .key("raca").value(pet.getRaca())
                    .key("proprietario").value(pet.getProprietario())
                    .key("album").array();

            List<PetAlbum> album = pet.getAlbum();
/*
            for (PetAlbum item : album) {

                jsonStringer.object()
                        .key("foto").value(item)
                        .endObject();
            }
*/

            jsonStringer.endArray()

                    .endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Json = ", jsonStringer.toString());
        return jsonStringer.toString();
    }


    public Pet JsonToPet(String data) throws JSONException {
        Pet pet = new Pet();
        try {
            JSONObject jo = new JSONObject(data);
           // cod_pet, nome, idade, sexo, sobre, cod_raca, email_proprietario, dthr_cadastro, foto, cod_raca, raca
            pet.setCodPet(jo.getInt("codPet"));
            pet.setNome(jo.getString("nome"));
            pet.setIdade(jo.getInt("idade"));
            pet.setSexo(jo.getString("sexo"));
            pet.setSobre(jo.getString("sobre"));
            pet.setFotoPerfil(jo.getString("fotoPerfil"));
           // pet.setRaca(jo.getInt("raca"));
            //pet.setProprietario(jo.getString("email_proprietario"));
            //pet.setAlbum(jo.getString("album"));

        } catch (JSONException e) {
            e.printStackTrace();

        }JSONObject jo = new JSONObject(data);
        return pet;
    }

    public List<Pet> JsonArrayToListaPets(String data) {
        List<Pet> list = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(data);
            for (int i = 0; i < json.length(); i++) {
                Pet pet = JsonToPet(json.getJSONObject(i).toString());
                //Pet pet = new Gson().fromJson(json.getJSONObject(i).toString(),Pet.class);
                list.add(pet);
                Log.i("Pet" + i + " ", String.valueOf(pet.getCodPet()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
