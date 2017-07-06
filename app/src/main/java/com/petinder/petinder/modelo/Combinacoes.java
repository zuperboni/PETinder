package com.petinder.petinder.modelo;

/**
 * Created by Mônica on 14/06/2017.
 */

public class Combinacoes {
    private int codPetAtual;
    private Pet PetFila;
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
