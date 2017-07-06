package com.petinder.petinder.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.petinder.petinder.activity.ChatActivity;
import com.petinder.petinder.modelo.Mensagem;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.MensagemJson;

import java.util.Calendar;

/**
 * Created by Mônica on 02/07/2017.
 */

public class EnviaMensagemTask extends AsyncTask {

    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/chat_json.php";
    private final String method = "EnviaMensagem-json";

    private int codPetRemetente;
    private int codPEtDestinatario;
    private String msg;
    private Context context;

    private String data;


    public EnviaMensagemTask(int codPetRemetente, int codPEtDestinatario, String msg, Context context){
        this.codPetRemetente = codPetRemetente;
        this.codPEtDestinatario = codPEtDestinatario;
        this.msg = msg;
        this.context = context;
    }


    @Override
    protected Object doInBackground(Object[] params) {

        Mensagem mensagem = new Mensagem();
        mensagem.setCodPetRemetente(codPetRemetente);
        mensagem.setCodPetDestinatario(codPEtDestinatario);
        mensagem.setMsg(msg);
        mensagem.setDthrEnviada(Calendar.getInstance().getTime());

        MensagemJson json = new MensagemJson();
        data = json.MensagemToJson(mensagem);

        String answer = HttpConnection.getSetDataWeb(this.url, this.method, data);

        Log.i("testechat", answer);

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

      //  ((ChatActivity)context).receberMensagens();


    }
}
