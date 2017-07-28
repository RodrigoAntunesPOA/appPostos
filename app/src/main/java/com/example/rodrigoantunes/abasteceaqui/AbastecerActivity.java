package com.example.rodrigoantunes.abasteceaqui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AbastecerActivity extends AppCompatActivity {

    Intent it;
    float preco, volume, valor;
    EditText editValor;
    TextView textLitros, textPreco, textProduto;
    String combustivel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abastecer);

        it=getIntent();
        preco=it.getFloatExtra("PrecoUnitario",0);
        combustivel=it.getStringExtra("Combustivel");

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
           valor= Float.parseFloat(editValor.getText().toString());

        if (valor== 0 )
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_LONG).show();
        else
            //Calcula o volume
        {
            volume = valor / preco;

            textLitros.setText(String.format("%.3f", volume));
        }


    }

    public void finalizar(View view )
    {
        //Registra o abastecimento
        Abastecimento abastecimento= new Abastecimento();

        abastecimento.combustivel=combustivel;
        abastecimento.litragem=volume;
        abastecimento.odometro=0;
        abastecimento.preco=preco;
        abastecimento.total=valor;


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


}
