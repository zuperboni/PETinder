package com.petinder.petinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.petinder.petinder.R;
import com.petinder.petinder.modelo.Pet;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.resource;

/**
 * Created by Carla on 14/07/2017.
 */

public class GridViewSelecaoAdapter  extends BaseAdapter {
    private LayoutInflater mInflater;
    private int resource;

    private Context context;
    private ArrayList<Pet> dados;;

    private Boolean editar;

    public GridViewSelecaoAdapter(int resource, Context context, ArrayList<Pet> dados, Boolean editar) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.resource = resource;
        this.dados = dados;
        this.editar = editar;
    }

    public int getCount() {
        return dados.size();
    }

    public Object getItem(int position) {
        return dados.get(position);
    }

    public long getItemId(int position) {
        Pet pet = dados.get(position);

        return pet.getCodPet();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
        }

        CircleImageView iv_pet = (CircleImageView) convertView.findViewById(R.id.selecao_iv_pet);
        CircleImageView iv_cor_cinza = (CircleImageView) convertView.findViewById(R.id.selecao_iv_cor_cinza);
        TextView tv_nomepet = (TextView) convertView.findViewById(R.id.selecao_nome_pet);
        ImageView iv_editar = (ImageView) convertView.findViewById(R.id.selecao_iv_editar);


        Pet pet = dados.get(position);

        if (editar) {
            iv_editar.setVisibility(View.VISIBLE);
            iv_cor_cinza.setVisibility(View.VISIBLE);
        }else{
            iv_cor_cinza.setVisibility(View.GONE);
            iv_editar.setVisibility(View.GONE);
        }

        if (pet.getFotoPerfil() == null) {
            Glide.with(context).load(R.drawable.dog_red)
                    .into(iv_pet);
        } else {
            Glide.with(context).load(context.getResources().getString(R.string.imageservermini) + pet.getFotoPerfil())
                    .into(iv_pet);
        }

        tv_nomepet.setText(pet.getNome().toUpperCase());

        return convertView;
    }



}