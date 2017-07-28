package com.example.rodrigoantunes.abasteceaqui.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigoantunes.abasteceaqui.AbastecerActivity;
import com.example.rodrigoantunes.abasteceaqui.ActivityRedePostos;
import com.example.rodrigoantunes.abasteceaqui.DetPostoActivity;
import com.example.rodrigoantunes.abasteceaqui.MapsActivity;
import com.example.rodrigoantunes.abasteceaqui.R;
import com.example.rodrigoantunes.abasteceaqui.localizacao.Geolocalizacao;
import com.example.rodrigoantunes.abasteceaqui.model.ContainerPostos;
import com.example.rodrigoantunes.abasteceaqui.model.Posto;
import com.example.rodrigoantunes.abasteceaqui.model.Produto;
import com.example.rodrigoantunes.abasteceaqui.model.Servico;

import java.util.ArrayList;
import java.util.List;


public class Adapter extends
        RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Posto> mDataset;
    private List<Produto> mDatasetPro;
    private List<Servico> mDatasetSer;

    private Context ctx;
    private int intTipoLista;
    //1 - POSTOS, 2 - PRODUTOS, 3 - SERVICOS

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter(Context ctx, List myDataset, int intTipoLista) {

        this.intTipoLista=intTipoLista;
        this.ctx = ctx;

        if (intTipoLista==1) {this.mDataset = myDataset;}
        if (intTipoLista==2) {this.mDatasetPro = myDataset;}
        if (intTipoLista==3) {this.mDatasetSer = myDataset;}

    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {

        ViewHolder vh;
        View v;

        if (intTipoLista==1) {
            // create a new view
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.detalhe_redepostos, parent, false);
            // set the view's size, margins, paddings and layout parameters

            vh = new ViewHolder(
                (View) v
                );

        return vh;}
        else
        {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_text_view, parent, false);
            // set the view's size, margins, paddings and layout parameters

            vh = new ViewHolder(
                    (View) v
            );

            return vh;

        }

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //if (mDataset.get(position) instanceof Posto)

        if (intTipoLista==1){

            Log.e("Instancia", "Posto");

            TextView mTextPosto , mTextEnd, mTextFuncionamento, mTextVerMapa, mTextDistancia;
            final ImageView mImageFavorito, mImagePosto, mImageMapa;
            double distancia;
            RatingBar ratingBar;

            mTextPosto = (TextView) holder.mView.findViewById(R.id.textNome);
            mTextEnd = (TextView) holder.mView.findViewById(R.id.textEndereco);
            mTextFuncionamento = (TextView) holder.mView.findViewById(R.id.textFuncionamento) ;
            mImageFavorito = (ImageView) holder.mView.findViewById(R.id.imgFavorito);
            mImagePosto = (ImageView) holder.mView.findViewById(R.id.imgPosto);
            mImageMapa = (ImageView) holder.mView.findViewById(R.id.imgMapa);
            //mTextVerMapa = (TextView) holder.mView.findViewById(R.id.textVerMapa);
            ratingBar = (RatingBar) holder.mView.findViewById(R.id.rating);
            mTextDistancia = (TextView) holder.mView.findViewById(R.id.textDistancia);


            Geolocalizacao geo = new Geolocalizacao(ctx);
            geo.getLastLocation();

            //Atualiza distancia entre o ponto atual e o posto

            float[] resultados = new float[1];

            Location.distanceBetween(
                    geo.ultLocation.getLatitude(),
                    geo.ultLocation.getLongitude(),
                    mDataset.get(position).latitude,
                    mDataset.get(position).longitude,
                    resultados);

            //mDataset.get(position).distancia = String.format("A %.2f KM", resultados[0] / 1000);

            //distancia=geo.calculaDistancia( geo.ultLocation.getLatitude(),
            //                                geo.ultLocation.getLongitude(),
            //                                mDataset.get(position).latitude,
            //                                mDataset.get(position).longitude);

            //if (distancia>1)
            //    mDataset.get(position).distancia = String.format("Aproximadamente %.2f KM", distancia);
            //else
            //    mDataset.get(position).distancia = String.format("Aproximadamente %.0f m", distancia*1000);



            if ( resultados[0] / 1000 >1)
                mDataset.get(position).distancia = String.format("Aproximadamente %.2f KM",  resultados[0] / 1000);
            else
                mDataset.get(position).distancia = String.format("Aproximadamente %.0f m",  (resultados[0]));


            /*
            if ( mDataset.get(position).distanciaKM  >1)
                mDataset.get(position).distancia = String.format("Aproximadamente %.2f KM",  mDataset.get(position).distanciaKM  / 1000);
            else
                mDataset.get(position).distancia = String.format("Aproximadamente %.0f m",  (mDataset.get(position).distanciaKM ));
            */

            mTextPosto.setText(mDataset.get(position).nome.toString());
            mTextEnd.setText(mDataset.get(position).endereco.toString());
            mTextFuncionamento.setText(mDataset.get(position).funcionamento.toString());
            mTextDistancia.setText(mDataset.get(position).distancia.toString());
            ratingBar.setRating(mDataset.get(position).rating);
            Log.e("Bandeira", String.valueOf(mDataset.get(position).bandeira));

            if (mDataset.get(position).bandeira == 1)
                mImagePosto.setImageResource(R.mipmap.logo_br);

            if (mDataset.get(position).bandeira == 2)
                mImagePosto.setImageResource(R.mipmap.logo_shell);

            if (mDataset.get(position).bandeira == 3)
                mImagePosto.setImageResource(R.mipmap.logo_ipi);

            Log.e("Favorito", String.valueOf(mDataset.get(position).favorito));
            if (mDataset.get(position).favorito) {
                mImageFavorito.setImageResource(R.mipmap.coracao);}
            else
                mImageFavorito.setImageResource(R.mipmap.coracao_vazio);




        //if (mDataset.get(position) instanceof Posto) {

            final List<Posto> listaSelecao;
            listaSelecao = new ArrayList<>();
            listaSelecao.add(mDataset.get(position));

            mImagePosto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ContainerPostos container;

                    Intent intent = new Intent(ctx, DetPostoActivity.class);

                    container = new ContainerPostos();

                    container.postos = listaSelecao;

                    intent.putExtra("postos", container);

                    ctx.startActivity(intent);
                }
            });


            mImageMapa.setOnClickListener(new ImageView.OnClickListener() {

                @Override
                public void onClick(View v) {

                    ContainerPostos container;

                    Intent intent = new Intent(ctx, MapsActivity.class);

                    container = new ContainerPostos();

                    container.postos = listaSelecao;

                    intent.putExtra("postos", container);

                    ctx.startActivity(intent);

                }
            });


            mImageFavorito.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Muda o coraçao

                    if (mDataset.get(position).favorito) {
                        mDataset.set(position, mDataset.get(position)).favorito=false;
                        mImageFavorito.setImageResource(R.mipmap.coracao_vazio);}
                    else
                    {mDataset.set(position, mDataset.get(position)).favorito=true;
                        mImageFavorito.setImageResource(R.mipmap.coracao);}

                }
            });
        }
        else
        {
            ImageView imgAbastecer;
            TextView mTextView, mTextViewPreco;
            String texto;

            mTextView  = (TextView) holder.mView.findViewById(R.id.txtProdServ);
            mTextViewPreco  = (TextView) holder.mView.findViewById(R.id.textPreco);
            imgAbastecer = (ImageView) holder.mView.findViewById(R.id.imgAbastecer);

            if (intTipoLista==2)
            {   Log.e("Produto", mDatasetPro.get(position).toString());
                //holder.mTextView.setText(mDatasetPro.get(position).toString());
                Log.e("lendo texto" , mTextView.getText().toString());

                texto=mDatasetPro.get(position).toString();
                mTextView.setText(texto);

                texto=String.format("R$ %.3f",mDatasetPro.get(position).valor);
                mTextViewPreco.setText(texto);

                imgAbastecer.setVisibility(View.VISIBLE);

                if (mDatasetPro.get(position).codigo==3)
                    imgAbastecer.setImageResource(R.mipmap.bico_azul);
                else
                    imgAbastecer.setImageResource(R.mipmap.bico_vermelho);

            }

            if (intTipoLista==3)
            {
                Log.e("Servico", mDatasetSer.get(position).servico);
                //holder.mTextView.setText(mDatasetSer.get(position).servico);

                texto=mDatasetSer.get(position).servico;

                mTextView.setText(texto);
            }

            imgAbastecer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ctx, "Funcionalidade não implementada!", Toast.LENGTH_LONG).show();

                    //Chama tela de abastecer
                    Intent it = new Intent(ctx, AbastecerActivity.class);
                    it.putExtra("PrecoUnitario", mDatasetPro.get(position).valor);
                    it.putExtra("Combustivel", mDatasetPro.get(position).toString());

                    ctx.startActivity(it);
                }
            });

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (intTipoLista==1) {return mDataset.size();}
        if (intTipoLista==2) {return mDatasetPro.size();}
        if (intTipoLista==3) {return mDatasetSer.size();}
        else return 0;
    }
}