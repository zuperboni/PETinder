package com.petinder.petinder.modelo;

/**
 * Created by Mônica on 14/06/2017.
 */

public class Combinacoes {
    private int codPetAtual;
    private Pet petFila;
    private String likeStatus;

    public int getCodPetAtual() {
        return codPetAtual;
    }

    public void setCodPetAtual(int codPetAtual) {
        this.codPetAtual = codPetAtual;
    }

    public Pet getPetFila() {
        return petFila;
    }

    public void setPetFila(Pet petFila) {
        this.petFila = petFila;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }
}
