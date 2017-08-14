package com.petinder.petinder.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.petinder.petinder.R;
import com.petinder.petinder.activity.MainActivity;
import com.petinder.petinder.activity.PerfilPetActivity;
import com.petinder.petinder.adapter.PetCard;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.task.ListaPetsTask;
import com.petinder.petinder.util.Constantes;


import java.util.List;


public class MainFragment extends Fragment {


    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private TextView nenhumPetCadastrado;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View fragment = inflater.inflate(R.layout.fragment_main, container, false);
        mSwipeView = (SwipePlaceHolderView) fragment.findViewById(R.id.swipeView);
        mContext = getActivity().getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));
        nenhumPetCadastrado = (TextView) getActivity().findViewById(R.id.nenhumPetCadastrado);
        ListaPetsTask task = new ListaPetsTask(getActivity(), this, 1,Constantes.CODIGO_PET_ATUAL);
        task.execute();
        if(Constantes.CODIGO_PET_ATUAL>0){
          //  ListaPetsTask task = new ListaPetsTask(getActivity(), this, 1);
            //task.execute();
        }
        else
        {
           // nenhumPetCadastrado.setVisibility(View.VISIBLE);
        }

        return fragment;
    }

    public void updateScreen(List<Pet> pets){

        for (Pet pet : pets) {
            mSwipeView.addView(new PetCard(mContext,this, pet, mSwipeView));
        }

        getActivity().findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        getActivity().findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });
    }
    public void notificaMatch(Pet pet){

            Fragment newFragment = new MatchFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable("pet", pet);
            newFragment.setArguments(bundle);


        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.frameLayout, newFragment);
                    transaction.addToBackStack(null);


            transaction.commit();
        }
    public void exibePerfil(Pet pet) {

        Intent intent = new Intent(getActivity(), PerfilPetActivity.class);
        intent.putExtra("pet",pet);
        startActivity(intent);

    }
}
