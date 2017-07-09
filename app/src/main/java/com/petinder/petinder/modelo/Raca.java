package com.petinder.petinder.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mônica on 14/06/2017.
 */

public class Raca implements Serializable {
    @SerializedName("codRaca")
    private int codRaca;
    @SerializedName("raca")
    private String raca;

    @Override
    public String toString() {
        return raca;
    }
    public int getCodRaca() {
        return codRaca;
    }

    public void setCodRaca(int codRaca) {
        this.codRaca = codRaca;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
