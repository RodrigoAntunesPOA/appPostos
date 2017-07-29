package com.example.rodrigoantunes.abasteceaqui.interfaces;

import com.example.rodrigoantunes.abasteceaqui.model.ContainerPostos;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ServicoApp {

    /*
    Define o método do protocolo HTTP que fará a
    requisição ao servidor e ao endpoint
    declarado dentro do GET. O retorno do método
    listaClientes será a classe que o REtrofit
    serializa o JSON de retorno.
     */

    @GET("v2/5950297e1200002d058c78b8")
    Call<ContainerPostos> listaPostos();


}
