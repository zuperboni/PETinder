package com.petinder.petinder.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.petinder.petinder.R;
import com.petinder.petinder.adapter.PetCard;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.task.ListaPetsTask;


import java.util.List;


public class MainFragment extends Fragment {


    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getActivity().setContentView(R.layout.activity_main);
        View fragment = inflater.inflate(R.layout.fragment_main, container, false);
        mSwipeView = (SwipePlaceHolderView) fragment.findViewById(R.id.swipeView);
        mContext = getActivity().getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));

        ListaPetsTask task = new ListaPetsTask(getActivity(), this, 1);
        task.execute();
        return fragment;
    }

    public void updateScreen(List<Pet> pets){

        for (Pet pet : pets) {
            mSwipeView.addView(new PetCard(mContext, pet, mSwipeView));
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
}
