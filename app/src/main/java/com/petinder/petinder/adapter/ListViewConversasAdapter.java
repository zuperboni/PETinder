package com.petinder.petinder.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.petinder.petinder.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mônica on 02/07/2017.
 */

public class ListViewConversasAdapter extends BaseAdapter {
    private List<Bundle> mList;
    private Activity activity;

    public ListViewConversasAdapter(List mList, Activity activity) {
        this.activity = activity;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_list_conversas, parent, false);

        CircleImageView imagemPerfil = (CircleImageView) view.findViewById(R.id.circleView);
        TextView txtNome = (TextView) view.findViewById(R.id.txtNome);
        TextView txtData = (TextView) view.findViewById(R.id.txtData);
        TextView icMsgRecebida = (TextView) view.findViewById(R.id.icMsgRecebida);

        if ((!mList.get(position).getString("foto").equals("")) && (mList.get(position).getString("foto") != null)) {
            Glide.with(activity).load(activity.getResources().getString(R.string.imageservermini) + mList.get(position).getString("foto")).into(imagemPerfil);
        }
        txtNome.setText(mList.get(position).getString("nome", ""));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        txtData.setText(dateFormat.format(mList.get(position).getSerializable("data")));

        if(mList.get(position).getInt("qtdMsg") > 0){
            icMsgRecebida.setVisibility(View.VISIBLE);
            icMsgRecebida.setText(Integer.toString(mList.get(position).getInt("qtdMsg")));
        } else {
            icMsgRecebida.setVisibility(View.GONE);
        }


        return view;
    }
}
