package br.com.eduardo.dudazap.dudazap;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.eduardo.dudazap.helper.ConfigFirebase;
import br.com.eduardo.dudazap.model.Usuario;

public class CadastroEmailActivity extends AppCompatActivity {
    private EditText nome, email,senha,confirmaSenha;
    private Button cadastrar;
    private Usuario usuario;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_email);
        email = (EditText)findViewById(R.id.txt_email);
        senha = (EditText)findViewById(R.id.txt_senha);
        confirmaSenha = (EditText)findViewById(R.id.txt_confirm_senha);
        cadastrar = (Button)findViewById(R.id.bt_email_cadastrar);
        nome = (EditText)findViewById(R.id.txt_nome);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();

            }
        });



    }


    private void cadastrarUsuario(){
        auth = ConfigFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(CadastroEmailActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroEmailActivity.this,"Sucesso!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CadastroEmailActivity.this,"Erro ao cadastrar!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}