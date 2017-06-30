package com.petinder.petinder.modelo;

import com.google.gson.annotations.SerializedName;

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
    private Raca raca;
    @SerializedName("proprietario")
    private Usuario proprietario;
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

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public Usuario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Usuario proprietario) {
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
}
