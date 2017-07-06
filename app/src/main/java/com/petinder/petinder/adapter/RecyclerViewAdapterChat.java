package com.petinder.petinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.petinder.petinder.R;
import com.petinder.petinder.modelo.Mensagem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class RecyclerViewAdapterChat extends RecyclerView.Adapter<RecyclerViewAdapterChat.MyViewHolder> {

    private static int selectedItem = -1;
    private Context context;
    private List<Mensagem> mList;
    private int remetente;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");



    public RecyclerViewAdapterChat(Context context, List<Mensagem> mList, int remetente){
        this.context = context;
        this.mList = mList;
        this.remetente = remetente;
    }

    public void setSelectedItem(int position)
    {
        selectedItem = position;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

           Log.i("2", "2");
           View v = LayoutInflater.from(context).inflate(R.layout.item_list_mensagem, parent, false);
           MyViewHolder myViewHolder = new MyViewHolder(v);
           return myViewHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterChat.MyViewHolder holder, int position) {

        if(selectedItem == position)
            holder.itemView.setSelected(true);


        //se eu sou o remetente
        if(remetente == mList.get(position).getCodPetRemetente()){

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            holder.content.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams layoutParamsBalao = (LinearLayout.LayoutParams) holder.contentBalao.getLayoutParams();
            layoutParamsBalao.gravity = Gravity.RIGHT;
            holder.contentBalao.setLayoutParams(layoutParamsBalao);

            holder.content.setGravity(Gravity.RIGHT);
            holder.contentBalao.setBackgroundResource(R.drawable.bg_msg_from);
            holder.contentBalao.setGravity(Gravity.RIGHT);
            holder.txtMensagem.setGravity(Gravity.RIGHT);
            holder.txtInfo.setGravity(Gravity.RIGHT);

        //se eu nao sou o remetente
        } else{
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            holder.content.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams layoutParamsBalao = (LinearLayout.LayoutParams) holder.contentBalao.getLayoutParams();
            layoutParamsBalao.gravity = Gravity.LEFT;
            holder.contentBalao.setLayoutParams(layoutParamsBalao);

            holder.content.setGravity(Gravity.LEFT);
            holder.contentBalao.setBackgroundResource(R.drawable.bg_msg_you);
            holder.contentBalao.setGravity(Gravity.LEFT);
            holder.txtMensagem.setGravity(Gravity.LEFT);
            holder.txtInfo.setGravity(Gravity.LEFT);
        }

        holder.txtMensagem.setText(mList.get(position).getMsg());


        holder.txtInfo.setText(dateFormat.format(mList.get(position).getDthrEnviada()));

        if(mList.get(position).getCodMensagem() == 0){
            holder.progress.setVisibility(View.VISIBLE);
            holder.check.setVisibility(View.GONE);
        } else {
            holder.progress.setVisibility(View.GONE);
            holder.check.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




    //ViewHolder  //Aqui se declara e vincula os componentes de cada item da lista
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtMensagem;
        public LinearLayout content;
        public LinearLayout contentBalao;
        public TextView txtInfo;
        public ImageView check;
        public ProgressBar progress;

        public MyViewHolder(View itemView) {
            super(itemView);

            content = (LinearLayout)itemView.findViewById(R.id.layout);
            contentBalao = (LinearLayout)itemView.findViewById(R.id.layoutBalao);
            txtMensagem = (TextView)itemView.findViewById(R.id.txtMensagem);
            txtInfo = (TextView)itemView.findViewById(R.id.txtInfo);
            check = (ImageView)itemView.findViewById(R.id.check);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);

        }
    }


}
