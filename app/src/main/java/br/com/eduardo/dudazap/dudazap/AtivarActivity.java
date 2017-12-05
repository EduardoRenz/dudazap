package br.com.eduardo.dudazap.dudazap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

import br.com.eduardo.dudazap.helper.Preferencias;

public class AtivarActivity extends AppCompatActivity {
    private Button validar;
    private EditText codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        validar = (Button) findViewById(R.id.bt_validar);
        codigo = (EditText) findViewById(R.id.txt_codigo);
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(codigo,simpleMaskTelefone);
        codigo.addTextChangedListener(maskTelefone);


        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recuperar os dados salvos
                Preferencias preferencias = new Preferencias(AtivarActivity.this);
                HashMap<String,String> usuario = preferencias.getDadosUsuario();
                String token = usuario.get("token");
                String tokenDigitado = codigo.getText().toString();


                if(token.equals(tokenDigitado)){
                    Toast.makeText(AtivarActivity.this,"Sucesso!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AtivarActivity.this,"Codigo inválido!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
