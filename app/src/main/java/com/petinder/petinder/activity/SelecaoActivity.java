package com.petinder.petinder.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.petinder.petinder.R;
import com.petinder.petinder.adapter.GridViewSelecaoAdapter;
import com.petinder.petinder.fragment.ConversasFragment;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.task.BuscaPetsUsuarioTask;
import com.petinder.petinder.util.Constantes;
import com.petinder.petinder.util.Utils;

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

    private Boolean editar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_pets);

        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {
        context = getBaseContext();

        gridview = (GridView) findViewById(R.id.selecao_gv_pet);

        ListaRacas(false);

        toolbar = (Toolbar) findViewById(R.id.selecao_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        editar = false;
    }

    private void inicializarAcoes() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Constantes.PET_SELECIONADO = (int) id;

                if (!editar) {
                    SharedPreferences prefs = SelecaoActivity.this.getSharedPreferences("Configuracoes", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("pet", Constantes.PET_SELECIONADO);
                    editor.commit();

                    Intent intent = new Intent(SelecaoActivity.this, MainActivity.class);
                    intent.putExtra("pet", Constantes.PET_SELECIONADO);
                    startActivity(intent);

                    finish();
                } else {
                    Intent intent = new Intent(SelecaoActivity.this, CadastroPetActivity.class);
                    intent.putExtra("editar", true);
                    startActivity(intent);
                }
            }
        });
    }

    private void ListaRacas(Boolean editar) {
        Pet pet = new Pet();
        pet.setProprietario(Constantes.EMAIL_PROPRIETARIO);

        BuscaPetsUsuarioTask task = new BuscaPetsUsuarioTask(this, editar, pet);
        task.execute();
    }

    public void AtualizaListaPets(ArrayList<Pet> pets, Boolean editar) {
        this.pets = pets;

        adapter_pets = new GridViewSelecaoAdapter(
                R.layout.item_pet_selecao,
                context,
                pets,
                editar
        );

        gridview.setAdapter(adapter_pets);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.editar) {
            String sTituloBarra = toolbar.getTitle().toString();

            if (sTituloBarra == getString(R.string.editar)) {
                toolbar.setTitle(R.string.selecionePet);
                editar = false;
            } else {
                toolbar.setTitle(R.string.editar);
                editar = true;
            }

            ListaRacas(editar);
        }
        return super.onOptionsItemSelected(item);
    }
}
