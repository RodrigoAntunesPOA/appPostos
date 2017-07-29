package com.example.rodrigoantunes.abasteceaqui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigoantunes.abasteceaqui.core.Ciclodevida;
import com.example.rodrigoantunes.abasteceaqui.model.Abastecimento;
import com.example.rodrigoantunes.abasteceaqui.model.Veiculo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Timer;
import java.util.TimerTask;

public class AbastecerActivity extends AppCompatActivity {

    Intent it;
    int postoID;
    float preco, volume;
    double  valor;
    EditText editValor;
    TextView textLitros, textPreco, textProduto;
    String combustivel;
    int iValor=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abastecer);

        it=getIntent();
        preco=it.getFloatExtra("PrecoUnitario",0);
        combustivel=it.getStringExtra("Combustivel");
        postoID=it.getIntExtra("PostoID",0);

        textPreco = (TextView) findViewById(R.id.textPreco);
        textProduto= (TextView) findViewById(R.id.textProduto) ;

        textPreco.setText(String.format("%.3f",preco));
        textProduto.setText(combustivel);

    }


    public void abastecer (View view)
    {
        //Captura o valor informado

        valor=0;

        editValor=(EditText) findViewById(R.id.editValor);
        textLitros=(TextView) findViewById(R.id.textVolume);

        if (!editValor.getText().toString().equals(""))
           valor= Double.parseDouble(editValor.getText().toString());

        if (valor== 0)
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_LONG).show();
        else
            //Calcula o volume
        {
            //volume = (valor / preco);

            //textLitros.setText(String.format("%.3f", volume));

            final Timer timer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    movimentaBomba(iValor);

                    if (iValor >= (valor * 100))
                    {
                        timer.cancel();
                        volume = Float.parseFloat(""+ valor)/preco;
                    }
                }
            };
            timer.schedule(task, 0, 2);

        }

    }

    public boolean movimentaBomba(final int iAbastecendo){

        textLitros.post(new Runnable() {
            @Override
            public void run() {

                Log.e("iAbs -->", String.valueOf(iAbastecendo));

                Log.e("iAbs -->", String.valueOf(preco));

                Log.e("Abastecendo -->", String.valueOf(volume));

                textLitros.setText(String.format("%.3f", (iAbastecendo/100)/preco));

                iValor++;
            }
        });

        return true;
    }


    public void salvarabastecimento(View view )
    {

        if (volume!=0)
        {
            //Registra o abastecimento
            Abastecimento abastecimento= new Abastecimento();

            abastecimento.combustivel=combustivel;
            abastecimento.litragem=volume;
            abastecimento.odometro=456789;
            abastecimento.preco=preco;
            abastecimento.total=valor;
            abastecimento.postoID=postoID;

            Ciclodevida.myRef.child("usuario_123@gmail/abastecimento").setValue(
                    abastecimento,
                    new DatabaseReference.CompletionListener() {

                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            //teste
                            Log.e("Erro ao ler dados", "erro");

                        }

                    })
            ;

            Toast.makeText(this, "Abastecimento salvo!", Toast.LENGTH_LONG).show();

            finish();
        }
        else
            Toast.makeText(this, "Registre um abastecimento!", Toast.LENGTH_LONG).show();

    }

    public void cancelar(View view)
    {
        finish();

    }


}
