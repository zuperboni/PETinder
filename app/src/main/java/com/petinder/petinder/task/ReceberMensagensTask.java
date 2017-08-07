package com.petinder.petinder.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;


import com.petinder.petinder.activity.ChatActivity;
import com.petinder.petinder.modelo.Mensagem;
import com.petinder.petinder.util.HttpConnection;
import com.petinder.petinder.web.MensagemJson;

import java.util.List;


public class ReceberMensagensTask extends AsyncTask {

    private final String url = "http://www.4runnerapp.com.br/Petinder/Ctrl/chat_json.php";
    private final String method = "SolicitaMensagensNew-json";

    private int codPetRemetente;
    private int codPetDestinatario;
    private String leitor;
    private Context context;

    private int page = 1;

    List<Mensagem> mensagens;


    //OLD
    public ReceberMensagensTask(int codPetRememente, int codPetDestinatario, Context context){
        this.codPetRemetente = codPetRememente;
        this.codPetDestinatario = codPetDestinatario;
        this.context = context;
    }

    public ReceberMensagensTask(int codPetRememente, int codPetDestinatario, Context context, int page){
        this.codPetRemetente = codPetDestinatario;
        this.codPetDestinatario = codPetRememente;
        this.context = context;
        this.page = page;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        SharedPreferences prefs = context.getSharedPreferences("Configuracoes", Context.MODE_PRIVATE);
        leitor = prefs.getString("email","");

        MensagemJson mensagemJson = new MensagemJson();
        String data = mensagemJson.SolicitaMensagem(codPetRemetente, codPetDestinatario,leitor, page);
        String answer = HttpConnection.getSetDataWeb(this.url, this.method, data);
        Log.i("testeMensagem",answer);
        mensagens = mensagemJson.JsonArrayToListaMensagem(answer);
        return mensagens;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(page > 1)
            ((ChatActivity)context).updateRecyclerView(mensagens);
        else
            ((ChatActivity)context).atualizarTela(mensagens);


    }
}
