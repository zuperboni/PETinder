package com.petinder.petinder.modelo;

import java.util.Date;

/**
 * Created by Mônica on 14/06/2017.
 */

public class Mensagem {
    private int codMensagem;
    private int codPetRemetente;
    private int codPetDestinatario;
    private Date dthrEnviada = new Date();
    private Date dthrRecebida = new Date();
    private Date dthrVisualizada = new Date();
    private String situacao;

    public int getCodMensagem() {
        return codMensagem;
    }

    public void setCodMensagem(int codMensagem) {
        this.codMensagem = codMensagem;
    }

    public int getCodPetRemetente() {
        return codPetRemetente;
    }

    public void setCodPetRemetente(int codPetRemetente) {
        this.codPetRemetente = codPetRemetente;
    }

    public int getCodPetDestinatario() {
        return codPetDestinatario;
    }

    public void setCodPetDestinatario(int codPetDestinatario) {
        this.codPetDestinatario = codPetDestinatario;
    }

    public Date getDthrEnviada() {
        return dthrEnviada;
    }

    public void setDthrEnviada(Date dthrEnviada) {
        this.dthrEnviada = dthrEnviada;
    }

    public Date getDthrRecebida() {
        return dthrRecebida;
    }

    public void setDthrRecebida(Date dthrRecebida) {
        this.dthrRecebida = dthrRecebida;
    }

    public Date getDthrVisualizada() {
        return dthrVisualizada;
    }

    public void setDthrVisualizada(Date dthrVisualizada) {
        this.dthrVisualizada = dthrVisualizada;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
