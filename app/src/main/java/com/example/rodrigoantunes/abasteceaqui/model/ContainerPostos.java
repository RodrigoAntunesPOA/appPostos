package com.example.rodrigoantunes.abasteceaqui.model;

import android.util.Log;

import com.example.rodrigoantunes.abasteceaqui.ActivityRedePostos;
import com.example.rodrigoantunes.abasteceaqui.adapter.Adapter;
import com.example.rodrigoantunes.abasteceaqui.core.Ciclodevida;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
As classes ContainerClientes e Cliente servem
para espelhar o JSON que será lido pela retrofit, e,
posteriormente, serializado e transformado em
uma instância de ContainerClientes
 */

public class ContainerPostos implements Serializable{

    public List<Posto> postos;

}
