package br.com.eduardo.dudazap.dudazap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

public class CadastrarActivity extends AppCompatActivity {
    private EditText nome,telefone;
    private Button cadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
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
                Log.i("Telefone sem formatacao",telefoneUsuario);

                //Gerar Token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000 + 1000);

                String token = String.valueOf(numeroRandomico);
                Log.i("Token",token);
            }
        });

    }
}
