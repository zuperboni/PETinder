package com.petinder.petinder.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mônica on 14/06/2017.
 */

public class Combinacoes {
    @SerializedName("cod_pet_atual")
    private int codPetAtual;
    @SerializedName("cod_pet_fila")
    private Pet PetFila;
    @SerializedName("like_status")
    private String likeStatus;

    public int getCodPetAtual() {
        return codPetAtual;
    }

    public void setCodPetAtual(int codPetAtual) {
        this.codPetAtual = codPetAtual;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public Pet getPetFila() {
        return PetFila;
    }

    public void setPetFila(Pet PetFila) {
        this.PetFila = PetFila;
    }
}
