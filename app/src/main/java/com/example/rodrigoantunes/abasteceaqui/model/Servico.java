package com.example.rodrigoantunes.abasteceaqui.model;

import java.io.Serializable;

/**
 * Created by RodrigoAntunes on 25/06/17.
 */

public class Servico implements Serializable{
    public String servico;

    @Override
    public String toString() {
        return servico.toString();
    }
}
