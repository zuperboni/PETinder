package com.petinder.petinder.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.petinder.petinder.R;
import com.petinder.petinder.modelo.Pet;

import de.hdodenhof.circleimageview.CircleImageView;


public class MatchFragment extends Fragment {

    private CircleImageView ivMacho;
    private CircleImageView ivFemea;
    private Button btnChat;
    private Button btnContinuar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_match, container, false);
        View fragment = inflater.inflate(R.layout.fragment_match, container, false);
        ivMacho = (CircleImageView) fragment.findViewById(R.id.match_img_macho);
        ivFemea = (CircleImageView) fragment.findViewById(R.id.match_img_femea);
        btnChat = (Button) fragment.findViewById(R.id.match_btn_mensagem);
        btnContinuar = (Button) fragment.findViewById(R.id.match_btn_depois);

       // Pega dados do pet
        Bundle bundle = this.getArguments();
        if (bundle != null) {
           Pet pet = (Pet) bundle.getSerializable("pet");
            Glide.with(getActivity()).load(getActivity().getResources().getString(R.string.imageservermini) + "bug.JPG").into(ivMacho);
            Glide.with(getActivity()).load(getActivity().getResources().getString(R.string.imageservermini) + pet.getFotoPerfil()).into(ivFemea);
        }

        return fragment;
    }

}
