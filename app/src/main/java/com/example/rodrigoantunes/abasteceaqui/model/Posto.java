package com.example.rodrigoantunes.abasteceaqui.model;

import java.io.Serializable;
import java.util.List;

public class Posto implements Serializable{

    public int codigo;
    public String nome;
    public String endereco;
    public Float latitude;
    public Float longitude;
    public int bandeira;
    public String funcionamento;
    public boolean favorito;
    public int rating;
    public List<Produto> produtos;
    public List<Servico> servicos;
    public float distanciaKM;
    public String distancia;

    @Override
    public String toString() {
        return nome;
    }

}
