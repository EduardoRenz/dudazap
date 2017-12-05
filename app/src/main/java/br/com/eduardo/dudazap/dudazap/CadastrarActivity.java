package br.com.eduardo.dudazap.dudazap;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import br.com.eduardo.dudazap.helper.Permissao;
import br.com.eduardo.dudazap.helper.Preferencias;

public class CadastrarActivity extends AppCompatActivity {
    private EditText nome,telefone;
    private Button cadastrar;

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);


        Permissao.validaPermissoes(1,this,permissoesNecessarias);


        nome = (EditText) findViewById(R.id.txt_nome);
        telefone = (EditText) findViewById(R.id.txt_telefone);
        cadastrar = (Button) findViewById(R.id.bt_cadastrar);
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone,simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = nome.getText().toString();
                String telefoneUsuario = telefone.getText().toString();
                telefoneUsuario = telefoneUsuario.replace("(","");
                telefoneUsuario = telefoneUsuario.replace(")","");
                telefoneUsuario = telefoneUsuario.replace("-","");
                telefoneUsuario = telefoneUsuario.replace(" ","");
                telefoneUsuario = "5555215554";
                Log.i("Telefone sem formatacao",telefoneUsuario);

                //Gerar Token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000 + 1000);

                String token = String.valueOf(numeroRandomico);
                //Log.i("Token",token);

                Preferencias preferencias = new Preferencias(getApplicationContext());
                preferencias.salvarUserPreferencias(nomeUsuario,telefoneUsuario,token);


                HashMap<String,String> usuario = preferencias.getDadosUsuario();

                Log.i("Token",usuario.get("token"));

                //Envio do SMS
                boolean enviadoSMS = enviaSMS("+" + telefoneUsuario,"Codigo de confirmaçao:" + token);

                if(enviadoSMS){
                    startActivity(new Intent(CadastrarActivity.this,AtivarActivity.class));
                    finish();
                }

            }
        });

    }

    private boolean enviaSMS(String telefone, String mensagem){


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,mensagem,null,null);
            return  true;
        } catch (Exception e){
            e.printStackTrace();
            return  false;
        }

    }

    public  void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        for(int resultado : grantResults){
            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidaPermissao();
            }
        }

    }

    private void alertaValidaPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissão necessaria");
        builder.setMessage("Cara, pra ti poder usar o app, tem que aceitar a permissão!");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
