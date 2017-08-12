package com.example.rodrigoantunes.abasteceaqui;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigoantunes.abasteceaqui.core.Ciclodevida;
import com.example.rodrigoantunes.abasteceaqui.model.RegistroServico;
import com.example.rodrigoantunes.abasteceaqui.model.Veiculo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicosActivity extends AppCompatActivity {

    //EditText editData, editOdometro;
    //RadioButton optRevisao, optTroca;
    //CheckBox chkOleoMotor, chkCalibragem, chkPalhetas, chkFiltrodeAr, chkFiltroOleo,
    //        chkFiltroCombustivel,chkAguaRadiador, chkBateria, chkGssolinaPartida;

    @BindView(R.id.optRevisao) RadioButton optRevisao;
    //@BindView(R.id.optTroca) RadioButton optTroca;

    @BindView(R.id.editDataServico) EditText editData;
    @BindView(R.id.editOdometro) EditText editOdometro;

    @BindView(R.id.chkOleoMotor) CheckBox chkOleoMotor;
    @BindView(R.id.chkCalibragem) CheckBox chkCalibragem;

    @BindView(R.id.chkPalhetas) CheckBox chkPalhetas;
    @BindView(R.id.chkFiltrodeAr) CheckBox chkFiltrodeAr;
    @BindView(R.id.chkFiltroOleo) CheckBox chkFiltroOleo;
    @BindView(R.id.chkFiltroCombustivel) CheckBox chkFiltroCombustivel;
    @BindView(R.id.chkAguaRadiador) CheckBox chkAguaRadiador;
    @BindView(R.id.chkBateria) CheckBox chkBateria;
    @BindView(R.id.chkGasolinaPartida) CheckBox chkGasolinaPartida;
    @BindView(R.id.chkFLuidoFreio) CheckBox chkFluidoFreio;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);

        //Carrega os objetos
        ButterKnife.bind(this);

        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");

        editData.setText(simpleFormat.format( new Date( System.currentTimeMillis() )));

    }


    public void salvarServico(View view){



        RegistroServico registroServico= new RegistroServico();

        //Popula o objeto de serviço

        if (optRevisao.isChecked()) {
            registroServico.tipoServico = 0;
            }  else
            registroServico.tipoServico = 1;

        registroServico.diaServico=editData.getText().toString();

        if (!editOdometro.getText().toString().equals("")) {
            registroServico.odometro = Float.parseFloat(editOdometro.getText().toString());
        } else
            registroServico.odometro= Float.valueOf(0);


        registroServico.aguaradiador=chkAguaRadiador.isChecked();
        registroServico.bateria=chkBateria.isChecked();
        registroServico.calibragem=chkCalibragem.isChecked();
        registroServico.filtrocombustivel=chkFiltroCombustivel.isChecked();
        registroServico.filtrooleo=chkFiltroOleo.isChecked();
        registroServico.fitrodear=chkFiltrodeAr.isChecked();
        registroServico.gasolinadepartida=chkGasolinaPartida.isChecked();
        registroServico.palhetas=chkPalhetas.isChecked();
        registroServico.fluidofreio=chkFluidoFreio.isChecked();
        registroServico.oleoMotor=chkOleoMotor.isChecked();


        Ciclodevida.myRef.child("usuario_123@gmail/servico").setValue(
                registroServico,
                new DatabaseReference.CompletionListener() {

                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        //teste
                        Log.e("Erro ao ler dados", "erro");

                    }

                })
        ;

        Toast.makeText(this, "Serviço regitrado!", Toast.LENGTH_LONG).show();

        finish();
    }

    public void sairServico(View view){

        finish();
    }
}
