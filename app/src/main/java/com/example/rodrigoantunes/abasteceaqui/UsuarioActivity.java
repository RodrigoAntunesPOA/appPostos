package com.example.rodrigoantunes.abasteceaqui;

import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigoantunes.abasteceaqui.R;
import com.example.rodrigoantunes.abasteceaqui.core.Ciclodevida;
import com.example.rodrigoantunes.abasteceaqui.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UsuarioActivity extends AppCompatActivity {

    private EditText textNome, textUF, textCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        Ciclodevida.myRef.child("usuario_123@gmail/dados").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Log.e("usuario", "Entrou na rotina");

                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                textNome = (EditText) findViewById(R.id.textNome);
                textUF = (EditText) findViewById(R.id.textUF);
                textCidade = (EditText) findViewById(R.id.textCidade);

                textNome.setText(usuario.nome);
                textCidade.setText(usuario.cidade);
                textUF.setText(usuario.UF);

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

}
