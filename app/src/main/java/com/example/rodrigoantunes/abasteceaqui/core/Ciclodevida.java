package com.example.rodrigoantunes.abasteceaqui.core;

import android.app.Application;

import com.example.rodrigoantunes.abasteceaqui.interfaces.ServicoApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Ciclodevida extends Application {

    /*
    Público e estático para ter acesso ao service das outras classes,
    como a MainActivity.
     */
    public static ServicoApp service;
    public static DatabaseReference myRef;

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        O Retrofit trata da base da URL e do conversor, no nosso caso
        o GSON. O seu método estático create cria uma instância da
        interface, onde acessaremos o método da Call.
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ServicoApp.class);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

    }
}
