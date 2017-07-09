package com.petinder.petinder.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mônica on 14/06/2017.
 */

public class PetAlbum implements Serializable {
    @SerializedName("codPet")
    private int codPet;
    @SerializedName("foto")
    private String foto;
    @SerializedName("isProfile")
    private String isProfile;
    @SerializedName("dataUpload")
    private Date dataUpload = new Date();

    public int getCodPet() {
        return codPet;
    }

    public void setCodPet(int codPet) {
        this.codPet = codPet;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIsProfile() {
        return isProfile;
    }

    public void setIsProfile(String isProfile) {
        this.isProfile = isProfile;
    }

    public Date getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(Date dataUpload) {
        this.dataUpload = dataUpload;
    }
}
