package br.com.eduardo.dudazap.dudazap;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.eduardo.dudazap.helper.Base64Custom;
import br.com.eduardo.dudazap.helper.ConfigFirebase;
import br.com.eduardo.dudazap.helper.Preferencias;
import br.com.eduardo.dudazap.model.Usuario;

public class MainActivity extends AppCompatActivity {
    Button cadastrar,acessar ;
    Usuario usuario;
    private EditText email,senha;
    private DatabaseReference refDatabase;
    private FirebaseAuth auth;
    String idUsuarioLogado;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cadastrar = (Button)findViewById(R.id.bt_cadastrar);
        acessar = (Button)findViewById(R.id.bt_acessar);
        email = (EditText)findViewById(R.id.txt_email) ;
        senha = (EditText)findViewById(R.id.txt_senha) ;
        refDatabase = ConfigFirebase.getFirebase();


        auth = ConfigFirebase.getFirebaseAuth();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,PrincipalActivity.class));
            finish();
        }

        acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CadastroEmailActivity.class));
            }
        });

    }

    private void validarLogin() {

        auth = ConfigFirebase.getFirebaseAuth();
        if(!usuario.getSenha().isEmpty()  && !usuario.getEmail().isEmpty()){
            Log.d("Login","Tentar logar");
        auth.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    idUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());

                    firebase = ConfigFirebase.getFirebase()
                            .child("usuarios").child(idUsuarioLogado);

                    valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Usuario usuarioRecuperado = dataSnapshot.getValue(Usuario.class);

                            Preferencias preferencias = new Preferencias(MainActivity.this);
                            preferencias.salvarDados(idUsuarioLogado,usuarioRecuperado.getNome());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    firebase.addListenerForSingleValueEvent(valueEventListener);


                    startActivity(new Intent(MainActivity.this,PrincipalActivity.class));
                    finish();
                }
                else {
                    try{
                        throw task.getException();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,"Erro:" + e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        }
    }


}
