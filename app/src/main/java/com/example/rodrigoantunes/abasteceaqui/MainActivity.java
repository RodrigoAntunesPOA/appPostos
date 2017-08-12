package com.example.rodrigoantunes.abasteceaqui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rodrigoantunes.abasteceaqui.model.RegistroServico;

public class MainActivity extends AppCompatActivity {

    static  int MY_REQUEST_CODE = 79;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //Se o usuário retirou a permissão pro ACCESS_FINE_LOCATION
            //nós requisitamos novamente;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_REQUEST_CODE);
        }


    }

    public void abrirRedePostos(View view){

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Por favor habilite a localização.", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_REQUEST_CODE);
        }

        else
        {
            Intent it = new Intent(this, ActivityRedePostos.class);
            startActivity(it);
        }
    }

    public void abrirAbastecer(View view){

        Intent it = new Intent(this, ActivityRedePostos.class);
        it.putExtra("favorito", true);
        startActivity(it);

        //Toast.makeText(this, "Funcionalidade não implementada!", Toast.LENGTH_LONG).show();
    }

    public void abrirServicos(View view){

        Intent it = new Intent(this, ServicosActivity.class);
        startActivity(it);

        //Toast.makeText(this, "Funcionalidade não implementada!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.meusdados) {

            Intent it = new Intent(this, UsuarioActivity.class);
            startActivity(it);
        }


        if (item.getItemId()==R.id.veiculos) {

            Intent it = new Intent(this, VeiculoActivity.class);
            startActivity(it);
        }

        if (item.getItemId()==R.id.favoritos)
        {
            Intent it = new Intent(this, ActivityRedePostos.class);
            it.putExtra("favorito", true);
            startActivity(it);
        }

        return true;
    }

}
