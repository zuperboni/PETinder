package com.petinder.petinder.modelo;

import android.app.Activity;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.petinder.petinder.task.BuscaConversasTask;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mônica on 14/06/2017.
 */

public class Pet implements Serializable{
    @SerializedName("codPet")
    private int codPet;
    @SerializedName("nome")
    private String nome;
    @SerializedName("idade")
    private int idade;
    @SerializedName("sexo")
    private String sexo;
    @SerializedName("sobre")
    private String sobre;
    @SerializedName("raca")
    //private Raca raca;
    private int raca;
    @SerializedName("proprietario")
    private String proprietario;
    @SerializedName("fotoPerfil")
    private String fotoPerfil;
    @SerializedName("album")
    private List<PetAlbum> album;


    public int getCodPet() {
        return codPet;
    }

    public void setCodPet(int codPet) {
        this.codPet = codPet;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public int getRaca() {

        return raca;
    }

    public void setRaca(int raca) {
        this.raca = raca;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public List<PetAlbum> getAlbum() {
        return album;
    }

    public void setAlbum(List<PetAlbum> album) {
        this.album = album;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public void buscaConversas(Activity activity, android.support.v4.app.Fragment fragment) {
        BuscaConversasTask task = new BuscaConversasTask(activity, this, fragment);
        task.execute();
    }
}