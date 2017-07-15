package com.petinder.petinder.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.petinder.petinder.R;
import com.petinder.petinder.adapter.GridViewSelecaoAdapter;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.task.BuscaPetsUsuarioTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carla on 14/07/2017.
 */

public class SelecaoActivity extends AppCompatActivity {

    private GridView gridview;

    private Context context;

    private ListView lv_nomes;
    private ArrayList<Pet> pets;
    private GridViewSelecaoAdapter adapter_pets;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_pets);

        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {
        context = getBaseContext();

        gridview = (GridView) findViewById(R.id.selecao_gv_pet);

        ListaRacas();

    }

    private void inicializarAcoes() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(
                        SelecaoActivity.this,
                        "" + id,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void ListaRacas(){
        BuscaPetsUsuarioTask task = new BuscaPetsUsuarioTask(this);
        task.execute();
    }

    public void AtualizaListaPets(ArrayList<Pet> pets){
        this.pets = pets;

        adapter_pets = new GridViewSelecaoAdapter(
                R.layout.item_pet_selecao,
                context,
                pets
        );

        gridview.setAdapter(adapter_pets);

    }
}
