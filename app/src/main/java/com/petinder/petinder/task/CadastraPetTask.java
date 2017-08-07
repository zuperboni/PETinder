package com.petinder.petinder.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.petinder.petinder.R;
import com.petinder.petinder.activity.CadastroPetActivity;
import com.petinder.petinder.activity.MainActivity;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.util.HttpConnection;

import java.io.ByteArrayOutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Carla on 02/07/2017.
 */

public class CadastraPetTask extends AsyncTask {

    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/cadastra_pet_json.php";
    private final String method = "cadastra_pet-json";
    private ProgressDialog progress;
    private Activity activity;
    private Pet pet;
    private Bitmap imagemPerfilPet;
    private String imagemDecodificada = "";

    public CadastraPetTask(Activity activity, Pet pet, Bitmap imagemPerfilPet){
        this.activity = activity;
        this.pet = pet;
        this.imagemPerfilPet = imagemPerfilPet;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(activity, "Aguarde...", "Envio de dados para web", true, true);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        //Tratar ImageViwew
        //localiza e transforma em um array de bytes
        if (imagemPerfilPet != null) {
            ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
            imagemPerfilPet.compress(Bitmap.CompressFormat.JPEG, 50, bAOS);
            byte[] imagemArrayBytes = bAOS.toByteArray();

            //decodifica com a classe BASE64 e transforma em string
            imagemDecodificada = Base64.encodeToString(imagemArrayBytes, Base64.DEFAULT);
            pet.setFotoPerfil(imagemDecodificada);
        } else {
            pet.setFotoPerfil("");
        }

        Gson gson = new Gson();

        String data = gson.toJson(pet);
        String answer = HttpConnection.getSetDataWeb(this.url, this.method, data);

        Log.i("Resposta", answer);

        return answer;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progress.dismiss();

        Log.i("resultado Json", o.toString());

        if (o.toString().equals("Sucesso")) {

            //AtualizaPrefs
            SharedPreferences prefs = activity.getSharedPreferences("Configuracoes", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("pet", pet.getCodPet());
            editor.commit();

            new AlertDialog.Builder(activity).setTitle(R.string.prontinho)
                    .setMessage(R.string.cadastrado)
                    .setCancelable(false)
                    .setPositiveButton(R.string.continuar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    })
                    .show();
        } else {
            ((CadastroPetActivity) activity).onErroCadastro();
        }
    }
}
