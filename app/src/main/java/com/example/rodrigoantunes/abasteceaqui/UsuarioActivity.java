package com.example.rodrigoantunes.abasteceaqui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodrigoantunes.abasteceaqui.core.Ciclodevida;
import com.example.rodrigoantunes.abasteceaqui.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsuarioActivity extends AppCompatActivity
{

    private EditText textNome, textUF, textCidade;
    private Spinner spinnerUF, spinnerCidade;
    private List<String> UF = new ArrayList<>();
    private List<String> Cidade = new ArrayList<>();

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        //Carrega UF
        spinnerUF = (Spinner) findViewById(R.id.spinner_UF);
        carregarUFs();
        carregarCidades();

    }

    @Override
    protected void onStart() {
        super.onStart();


        Ciclodevida.myRef.child("usuario_123@gmail/dados").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                int i;

                usuario = dataSnapshot.getValue(Usuario.class);

                textNome = (EditText) findViewById(R.id.textNome);
                textUF = (EditText) findViewById(R.id.textUF);
                textCidade = (EditText) findViewById(R.id.textCidade);

                textNome.setText(usuario.nome);
                textCidade.setText(usuario.cidade);
                textUF.setText(usuario.UF);

                for (i = 0; i<= UF.size(); i++) {
                    if ( UF.get(i).equals(usuario.UF))
                    {
                        spinnerUF.setSelection(i);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Erro", "Failed to read value.", error.toException());
            }
        });

    }

    public void salvarUsuario(View view){

        Usuario usuario= new Usuario();

        usuario.nome = textNome.getText().toString();
        usuario.cidade = textCidade.getText().toString();
        usuario.UF = textUF.getText().toString();

        usuario.cidade=spinnerCidade.getSelectedItem().toString();
        usuario.UF=spinnerUF.getSelectedItem().toString();

        Ciclodevida.myRef.child("usuario_123@gmail/dados").setValue(
                usuario,
                new DatabaseReference.CompletionListener() {

                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        //teste
                        Log.e("Erro ao ler dados", "erro");

                    }

                })
        ;

        Toast.makeText(this, "Usuário atualizado!", Toast.LENGTH_LONG).show();

        finish();
    }

    public void cancelar(View view){

        finish();
    }

    public void carregarUFs () {

        UF.add("");
        UF.add("PR");
        UF.add("RS");
        UF.add("SC");

        //Identifica o Spinner no layout

        //spinnerUF = (Spinner) findViewById(R.id.spinner_UF);

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, UF);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUF.setAdapter(spinnerArrayAdapter);
        spinnerUF.setSelection(0);


        spinnerUF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("click spinner","vai carregar cidade");
                    carregarCidades();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
        });

    };


    public void carregarCidades () {

        Log.e("CARREGACIDADE", "ENTROU");


        Cidade.clear();
        Cidade.add("");

        if (!spinnerUF.getSelectedItem().equals("")) {
            //Carrega as cidades conforme UF
            if (spinnerUF.getSelectedItem().equals("RS")) {
                Cidade.add("PORTO ALEGRE");
                Cidade.add("CANOAS");

            }
            ;

            if (spinnerUF.getSelectedItem().equals("SC")) {
                Cidade.add("BLUMENAU");
                Cidade.add("JOINVILLE");

            }
            ;

            if (spinnerUF.getSelectedItem().equals("PR")) {
                Cidade.add("CURITIBA");
                Cidade.add("PATO BRANCO");

            }
            ;
        }

        //Identifica o Spinner no layout
        spinnerCidade = (Spinner) findViewById(R.id.spinner_Cidade);

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Cidade);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidade.setAdapter(spinnerArrayAdapter);
        spinnerCidade.setSelection(0);

        if (usuario != null) {
            for (int j = 0; j < Cidade.size(); j++) {

                Log.e("CidadeTotal", String.valueOf(Cidade.size()));

                if (Cidade.get(j).equals(usuario.cidade)) {
                    spinnerCidade.setSelection(j);

                    break;

                }
            }
        }

    };

}
