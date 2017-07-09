package com.petinder.petinder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.petinder.petinder.R;
import com.petinder.petinder.modelo.Raca;
import com.petinder.petinder.task.ListaRacasTask;

import java.util.ArrayList;
import java.util.List;

public class CadastroPetActivity extends AppCompatActivity {
    private Spinner spraca;
    List<Raca> racas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);

        spraca = (Spinner)findViewById(R.id.spinner_raca);
        spraca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Raca raca = racas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ListaRacas();
    }
    private void ListaRacas(){
        ListaRacasTask task = new ListaRacasTask(this);
        task.execute();
    }
    public void AtualizaListaPets(List<Raca>racas){
    this.racas = racas;

        ArrayAdapter<Raca> spinnerRacaAdapter = new ArrayAdapter<Raca>(this, android.R.layout.simple_list_item_1, android.R.id.text1, racas);
        spraca.setAdapter(spinnerRacaAdapter);


    }
}
