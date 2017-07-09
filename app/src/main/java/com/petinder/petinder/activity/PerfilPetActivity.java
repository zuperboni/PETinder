package com.petinder.petinder.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.petinder.petinder.R;
import com.petinder.petinder.adapter.PageAdapterAlbum;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.modelo.PetAlbum;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class PerfilPetActivity extends AppCompatActivity {
    private TextView perfil_nome_idade;
    private TextView perfil_raio_localizacao;
    private TextView perfil_descricao_pet;
    private static ViewPager mPager;
    private ArrayList<PetAlbum> PetArray = new ArrayList<PetAlbum>();

    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_pet);

        perfil_nome_idade= (TextView)  findViewById(R.id.perfil_nome_idade);
        perfil_raio_localizacao= (TextView)  findViewById(R.id.perfil_raio_localizacao);
        perfil_descricao_pet= (TextView)  findViewById(R.id.perfil_descricao_pet);

        //Recebendo os dados do pet
        Intent intent = getIntent();
        pet = (Pet) intent.getSerializableExtra("pet");
        perfil_nome_idade.setText(pet.getNome()+", " + pet.getIdade());
        perfil_descricao_pet.setText(pet.getSobre());
        slideAlbum();
    }

    private void slideAlbum() {
        for (int i = 0; i < pet.getAlbum().size(); i++)
            PetArray.add(pet.getAlbum().get(i));

        mPager = (ViewPager) findViewById(R.id.perfil_fotos_pet);
        mPager.setAdapter(new PageAdapterAlbum(PerfilPetActivity.this, PetArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
    }
}
