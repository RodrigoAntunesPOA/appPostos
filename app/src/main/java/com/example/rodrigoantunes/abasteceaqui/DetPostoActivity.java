package com.example.rodrigoantunes.abasteceaqui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rodrigoantunes.abasteceaqui.adapter.Adapter;
import com.example.rodrigoantunes.abasteceaqui.model.ContainerPostos;
import com.example.rodrigoantunes.abasteceaqui.model.Posto;
import com.example.rodrigoantunes.abasteceaqui.model.Produto;
import com.example.rodrigoantunes.abasteceaqui.model.Servico;

public class DetPostoActivity extends AppCompatActivity {

    private Intent it;
    private ContainerPostos containerPostos;

    private RecyclerView mRecyclerViewPosto;
    private RecyclerView mRecyclerViewServ;
    private RecyclerView.Adapter mAdapterPosto;
    private RecyclerView.Adapter mAdapaterServ;
    private RecyclerView.LayoutManager mLayoutManagerPosto;
    private RecyclerView.LayoutManager mLayoutManagerServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det_posto);

        mRecyclerViewPosto = (RecyclerView) findViewById(R.id.recycler_viewComb);
        mRecyclerViewServ = (RecyclerView) findViewById(R.id.recycler_viewServ);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewPosto.setHasFixedSize(false);
        mRecyclerViewServ.setHasFixedSize(false);

        mRecyclerViewPosto.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerViewServ.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        mLayoutManagerPosto = new LinearLayoutManager(DetPostoActivity.this);
        mLayoutManagerServ = new LinearLayoutManager(DetPostoActivity.this);

        mRecyclerViewPosto.setLayoutManager(mLayoutManagerPosto);
        mRecyclerViewServ.setLayoutManager(mLayoutManagerServ);


        //Busca a Intent que chamou
        it=getIntent();

        containerPostos = (ContainerPostos) it.getSerializableExtra("postos");

        for (Posto posto : containerPostos.postos) {

            //Preenche os dados
            TextView mTextPosto , mTextEnd, mTextFuncionamento, mTextVerMapa, mTextDistancia;
            final ImageView mImageFavorito, mImagePosto, mImageMapa;
            RatingBar ratingBar;

            mTextPosto = (TextView) findViewById(R.id.textNome);
            mTextEnd = (TextView) findViewById(R.id.textEndereco);
            mTextFuncionamento = (TextView) findViewById(R.id.textFuncionamento) ;
            mImageFavorito = (ImageView) findViewById(R.id.imgFavorito);
            mImagePosto = (ImageView) findViewById(R.id.imgPosto);
            //mTextVerMapa = (TextView) findViewById(R.id.textVerMapa);
            ratingBar = (RatingBar) findViewById(R.id.rating);
            mTextDistancia = (TextView) findViewById(R.id.textDistancia);

            mTextPosto.setText(posto.nome.toString());
            mTextEnd.setText(posto.endereco.toString());
            mTextFuncionamento.setText(posto.funcionamento.toString());
            mTextDistancia.setText(posto.distancia.toString());
            ratingBar.setRating(posto.rating);

            if (posto.favorito) {
                mImageFavorito.setImageResource(R.mipmap.coracao);}
            else
                mImageFavorito.setImageResource(R.mipmap.coracao_vazio);

            if (posto.bandeira == 1)
                mImagePosto.setImageResource(R.mipmap.logo_br);

            if (posto.bandeira == 2)
                mImagePosto.setImageResource(R.mipmap.logo_shell);

            if (posto.bandeira == 3)
                mImagePosto.setImageResource(R.mipmap.logo_ipi);


            //Carrega Precos
            for (Produto produto : posto.produtos){
                Log.e("AdapterProd", produto.toString());
            }

            mAdapterPosto = new Adapter(this, posto.produtos, 2);
            mRecyclerViewPosto.setAdapter(mAdapterPosto);


            //Carrega Servicos
            for (Servico servico : posto.servicos){
                Log.e("AdapterServ", servico.toString());
            }

            mAdapaterServ = new Adapter(this, posto.servicos, 3);
            mRecyclerViewServ.setAdapter(mAdapaterServ);

        }




    }

    public void AbrirMapa(View view){

        ContainerPostos container;

        Intent intent = new Intent(this, MapsActivity.class);

        container = new ContainerPostos();

        intent.putExtra("postos", containerPostos);

        startActivity(intent);

    }

}
