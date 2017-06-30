package com.petinder.petinder.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.petinder.petinder.R;
import com.petinder.petinder.activity.MainActivity;
import com.petinder.petinder.fragment.MainFragment;
import com.petinder.petinder.modelo.Combinacoes;
import com.petinder.petinder.modelo.Pet;
import com.petinder.petinder.task.ReagirPetTask;
import com.petinder.petinder.util.Utils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Mônica on 17/06/2017.
 */

    @Layout(R.layout.pet_card_view)
    public class PetCard {
    private Bitmap imagem;

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private Pet mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public PetCard(Context context, Pet profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        Glide.with(mContext).load("http://4runnerapp.com.br/Petinder/img/"+ mProfile.getFotoPerfil())
                .bitmapTransform(new RoundedCornersTransformation(mContext, Utils.dpToPx(7), 0,
                        RoundedCornersTransformation.CornerType.TOP))
                .into(profileImageView);
        nameAgeTxt.setText(mProfile.getNome() + ", " + mProfile.getIdade());
       // locationNameTxt.setText(mProfile.getLocation());
    }

    @Click(R.id.profileImageView)
    private void onClick(){
        Log.d("EVENT", "profileImageView click");
        mSwipeView.addView(this);
    }

    @SwipeOut
    private void onSwipedOut(){
        Combinacoes combinacao = new Combinacoes();
        combinacao.setCodPetAtual(1); // Pegar o codigo do pet que esta visualizando
        combinacao.setCodPetFila(mProfile.getCodPet());
        combinacao.setLikeStatus("yes");
        ReagirPetTask task = new ReagirPetTask( 1, combinacao);
        task.execute();
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.i("ENVIANDO", "onSwipedIn");
        Combinacoes combinacao = new Combinacoes();
        combinacao.setCodPetAtual(1); // Pegar o codigo do pet que esta visualizando
        combinacao.setCodPetFila(mProfile.getCodPet());
        combinacao.setLikeStatus("no");
        ReagirPetTask task = new ReagirPetTask( 1, combinacao);
        task.execute();
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}
