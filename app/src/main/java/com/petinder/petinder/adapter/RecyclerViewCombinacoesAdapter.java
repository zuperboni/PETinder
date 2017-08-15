package com.petinder.petinder.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.petinder.petinder.R;
import com.petinder.petinder.modelo.Combinacoes;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mônica on 09/07/2017.
 */

public class RecyclerViewCombinacoesAdapter extends RecyclerView.Adapter<RecyclerViewCombinacoesAdapter.MyViewHolder> {
    private static Activity activity;
    private static List<Combinacoes> combinacoes;

    public RecyclerViewCombinacoesAdapter(Activity activity, List<Combinacoes> combinacoes) {
        this.activity = activity;
        this.combinacoes = combinacoes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_list_combinacoes, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(combinacoes.get(position).getPetFila().getFotoPerfil()==null){
            Glide.with(activity).load(R.drawable.dog_red)
                    .into(holder.imageThumb);
        }
        else {
            Glide.with(activity).load(activity.getResources().getString(R.string.imageservermini) + combinacoes.get(position).getPetFila().getFotoPerfil())
                    .into(holder.imageThumb);
        }
    }

    @Override
    public int getItemCount() {
        if(combinacoes != null ){
            return combinacoes.size();
        }
        return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageThumb;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageThumb = (CircleImageView) itemView.findViewById(R.id.imageThumb);
        }


        @Override
        public void onClick(View v) {
            /*
            Combinacoes vitrineSelecionada = combinacoes.get(getPosition());
            Intent intent = new Intent(activity, VitrineActivity.class);
            intent.putExtra("vitrine", vitrineSelecionada);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(activity,imageThumb,"capaVitrine");
                activity.startActivity(intent,options.toBundle());
            } else {
                activity.startActivity(intent);
            }
            */
        }
    }




}
