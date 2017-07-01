package com.example.rodrigoantunes.abasteceaqui;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigoantunes.abasteceaqui.core.Ciclodevida;
import com.example.rodrigoantunes.abasteceaqui.model.Usuario;
import com.example.rodrigoantunes.abasteceaqui.model.Veiculo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class VeiculoActivity extends AppCompatActivity {

    EditText textMarca, textModelo, textPlaca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo);
        Ciclodevida.myRef.child("usuario_123@gmail/veiculo").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Log.e("veiculo", "Entrou na rotina");

                Veiculo veiculo = dataSnapshot.getValue(Veiculo.class);

                textMarca = (EditText) findViewById(R.id.textMarca);
                textModelo = (EditText) findViewById(R.id.textModelo);
                textPlaca = (EditText) findViewById(R.id.textPlaca);

                textMarca.setText(veiculo.marca);
                textModelo.setText(veiculo.modelo);
                textPlaca.setText(veiculo.placa);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Erro", "Failed to read value.", error.toException());
            }
        });

    }

    public void salvarVeiculo(View view){

        Veiculo veiculo= new Veiculo();

        veiculo.marca = textMarca.getText().toString();
        veiculo.modelo = textModelo.getText().toString();
        veiculo.placa = textPlaca.getText().toString();

        Ciclodevida.myRef.child("usuario_123@gmail/veiculo").setValue(
                veiculo,
                new DatabaseReference.CompletionListener() {

                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        //teste
                        Log.e("Erro ao ler dados", "erro");

                    }

                })
        ;

        Toast.makeText(this, "Veículo atualizado!", Toast.LENGTH_LONG).show();

        finish();
    }

    public void cancelar(View view){

        finish();
    }
}
