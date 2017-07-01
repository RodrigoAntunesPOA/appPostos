package com.example.rodrigoantunes.abasteceaqui.model;

import java.io.Serializable;

/**
 * Created by RodrigoAntunes on 21/06/17.
 */

public class Produto implements Serializable{

    public int codigo;
    public float valor;
    public boolean ativo;

    @Override
    public String toString() {
        String preco = new String();

        if (codigo==1)
            preco="Gasolina Comum"; //     R$ " + String.format("%.3f",valor);

        if (codigo==2)
            preco="Gasolina Aditivada"; // R$ " + String.format("%.3f",valor);

        if (codigo==3)
            preco="Etanol"; //             R$ " + String.format("%.3f",valor);

        return preco.toString();
    }
}
