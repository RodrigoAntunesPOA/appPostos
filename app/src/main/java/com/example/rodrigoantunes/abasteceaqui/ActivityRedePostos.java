package com.example.rodrigoantunes.abasteceaqui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rodrigoantunes.abasteceaqui.adapter.Adapter;
import com.example.rodrigoantunes.abasteceaqui.core.Ciclodevida;
import com.example.rodrigoantunes.abasteceaqui.localizacao.Geolocalizacao;
import com.example.rodrigoantunes.abasteceaqui.model.ContainerPostos;
import com.example.rodrigoantunes.abasteceaqui.model.Posto;
import com.example.rodrigoantunes.abasteceaqui.model.Produto;
import com.example.rodrigoantunes.abasteceaqui.model.Servico;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRedePostos extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ContainerPostos container, copiaContainer;
    static int MY_REQUEST_CODE=89;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    private Location lastLocation;
    private Geolocalizacao geolocalizacao;
    private Intent it;
    private boolean mostrafavorito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redepostos);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Se o usuário retirou a permissão pro ACCESS_FINE_LOCATION
            //nós requisitamos novamente;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_REQUEST_CODE);
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setLayoutManager(mLayoutManager);

        //Pega posicao atual
        geolocalizacao=new Geolocalizacao(ActivityRedePostos.this);
        geolocalizacao.getLastLocation();
        lastLocation= geolocalizacao.ultLocation;


        //Busca a Intent que chamou
        it=getIntent();

        if (it!=null) {
            mostrafavorito = it.getBooleanExtra("favorito", false);

            Log.e ( "Parametro",  String.valueOf(it.getBooleanExtra("favorito", false)));

        }


        Call<ContainerPostos> call =
                Ciclodevida.service.listaPostos();

        call.enqueue(new Callback<ContainerPostos>() {

            @Override
            public void onResponse(
                    Call<ContainerPostos> call,
                    Response<ContainerPostos> response) {


                final List<Posto> listaSelecao;
                listaSelecao = new ArrayList<>();
                container=new ContainerPostos();

                copiaContainer = response.body();

                //Valida se o posto deve ser considerado
                for (Posto posto : copiaContainer.postos){
                    Log.e("POSTORede", posto.nome);
                    Log.e("POSTORede", posto.latitude.toString());
                    Log.e("POSTORede", posto.longitude.toString());

                    //Atualiza distancia entre o ponto atual e o posto

                    if (lastLocation!=null) {

                        Log.e("Achou localizacao", posto.latitude.toString());

                        float[] resultados = new float[1];

                        Location.distanceBetween(
                                lastLocation.getLatitude(),
                                lastLocation.getLongitude(),
                                posto.latitude,
                                posto.longitude,
                                resultados);

                        posto.distanciaKM = resultados[0] / 1000;
                    } else
                        posto.distanciaKM=0;


                    for (Produto prod : posto.produtos){
                        Log.e("POSTOSProd", prod.toString());
                    }


                    for (Servico serv : posto.servicos){
                        Log.e("POSTOServ", serv.servico);
                    }

                    /*
                    if (!mostrafavorito){
                        //Todos
                        container.postos.add(posto);
                    }
                    else
                        if (mostrafavorito && posto.favorito) {
                            copiaContainer.postos.add(posto);
                        }

                        */

                    if (mostrafavorito ) {
                        if (posto.favorito)
                            listaSelecao.add(posto);
                    }
                    else
                    {
                        //todos
                        listaSelecao.add(posto);
                    }

                    container.postos=listaSelecao;
                }

                mAdapter = new Adapter(ActivityRedePostos.this, container.postos,1);

                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<ContainerPostos> call, Throwable t) {}
        });

        //listarPostos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        abrirMapa();
        return true;
    }

    public void abrirMapa (){
        //Abre o mapa com todos os postos filtrados
        Intent it = new Intent(this, MapsActivity.class);
        it.putExtra("postos", container);
        startActivity(it);
    }


    public void listarPostos(){

        //Pega posicao atual
        geolocalizacao=new Geolocalizacao(ActivityRedePostos.this);
        geolocalizacao.getLastLocation();
        lastLocation= geolocalizacao.ultLocation;



        Call<ContainerPostos> call =
                Ciclodevida.service.listaPostos();

        call.enqueue(new Callback<ContainerPostos>() {

            @Override
            public void onResponse(
                    Call<ContainerPostos> call,
                    Response<ContainerPostos> response) {

                container = response.body();

                //Valida se o posto deve ser considerado
                for (Posto posto : container.postos){
                    Log.e("POSTORede", posto.nome);
                    Log.e("POSTORede", posto.latitude.toString());
                    Log.e("POSTORede", posto.longitude.toString());

                    //Atualiza distancia entre o ponto atual e o posto

                    if (lastLocation!=null) {
                        float[] resultados = new float[1];

                        Location.distanceBetween(
                                lastLocation.getLatitude(),
                                lastLocation.getLongitude(),
                                posto.latitude,
                                posto.longitude,
                                resultados);

                        posto.distanciaKM = resultados[0] / 1000;
                    } else
                        posto.distanciaKM=0;


                    for (Produto prod : posto.produtos){
                        Log.e("POSTOSProd", prod.toString());
                    }


                    for (Servico serv : posto.servicos){
                        Log.e("POSTOServ", serv.servico);
                    }

                }

                mAdapter = new Adapter(ActivityRedePostos.this, container.postos,1);

                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<ContainerPostos> call, Throwable t) {}
        });


    }

}
