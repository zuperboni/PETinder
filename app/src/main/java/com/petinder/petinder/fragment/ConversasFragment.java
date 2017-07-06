package com.petinder.petinder.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.petinder.petinder.R;
import com.petinder.petinder.activity.ChatActivity;
import com.petinder.petinder.adapter.ListViewConversasAdapter;
import com.petinder.petinder.modelo.Pet;

import java.util.List;

public class ConversasFragment extends Fragment {
    ListView mListView;
    ListViewConversasAdapter adapter;
    private TextView txtSemConversas;
    private Pet pet;
    //listas
    List<Bundle> mList;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View fragment = inflater.inflate(R.layout.fragment_conversas, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("Configuracoes", Context.MODE_PRIVATE);

        pet = new Pet();
        pet.setCodPet(prefs.getInt("codPet", 1)); // ARMAZENAR O CODIGO DO PET

        mListView = (ListView)fragment.findViewById(R.id.listViewConversas);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pet petSelec = new Pet();
                petSelec.setCodPet(mList.get(position).getInt("codPet",0));
                petSelec.setNome(mList.get(position).getString("nome", ""));
                petSelec.setFotoPerfil(mList.get(position).getString("foto",""));

                Intent intent= new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("origem","icNav");
                intent.putExtra("codRemetente",petSelec.getCodPet());
                intent.putExtra("profPicRemetente",petSelec.getFotoPerfil());
                intent.putExtra("nomeRemetente",petSelec.getNome());
                startActivity(intent);
            }
        });

        txtSemConversas = (TextView) fragment.findViewById(R.id.txtSemConversas);
        progressBar = (ProgressBar) fragment.findViewById(R.id.progress);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        listaConversas();

    }

    public void atualizaLista(List<Bundle> list){
        mList = list;
        adapter = new ListViewConversasAdapter(mList,getActivity());
        mListView.setAdapter(adapter);

        if(mList.size() < 1){
            txtSemConversas.setVisibility(View.VISIBLE);
        } else {
            txtSemConversas.setVisibility(View.GONE);
        }

        progressBar.setVisibility(View.GONE);

    }

    public void listaConversas(){
        pet.buscaConversas(getActivity(), this);
    }
}
