package com.petinder.petinder.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mônica on 14/06/2017.
 */

public class Usuario implements Serializable {
    private String email;
    private String nome;
    private String imagemPerfil;
    private String localizacao;
    private String senha;
    private String fcmIdAtual;
    private int raioBusca;
    private Date dtNasc = new Date();
    private List<String> fcmIDList = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagemPerfil() {
        return imagemPerfil;
    }

    public void setImagemPerfil(String imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getRaioBusca() {
        return raioBusca;
    }

    public void setRaioBusca(int raioBusca) {
        this.raioBusca = raioBusca;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public void addGcmId(String fcmId){
        if(!fcmId.equals("")){
            getFcmIDList().add(fcmId);
        }
    }

    public String getFcmIdAtual() {
        return fcmIdAtual;
    }

    public void setFcmIdAtual(String fcmIdAtual) {
        this.fcmIdAtual = fcmIdAtual;
    }

    public List<String> getFcmIDList() {
        return fcmIDList;
    }

    public void setFcmIDList(List<String> fcmIDList) {
        this.fcmIDList = fcmIDList;
    }

}
