package br.com.eduardo.dudazap.dudazap;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

import br.com.eduardo.dudazap.helper.Base64Custom;
import br.com.eduardo.dudazap.helper.ConfigFirebase;
import br.com.eduardo.dudazap.helper.Preferencias;
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
                    String identificador = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificador); // pega o uid do user
                    usuario.setNome(usuario.getNome());
                    usuario.salvarDados();



                    //auth.signOut(); // Desloga o usuario assim que cadastrar


                    Preferencias preferencias = new Preferencias(CadastroEmailActivity.this);
                    String idUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());
                    preferencias.salvarDados(idUsuarioLogado,usuario.getNome());


                    startActivity(new Intent(CadastroEmailActivity.this,MainActivity.class));
                    finish();

                }
                else{
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        Toast.makeText(CadastroEmailActivity.this,"Erro ao cadastrar: Senha muito ruim!",Toast.LENGTH_SHORT).show();
                    }
                    catch (FirebaseAuthEmailException e) {
                        Toast.makeText(CadastroEmailActivity.this,"Erro ao cadastrar: E-mail inválido!",Toast.LENGTH_SHORT).show();
                    }
                    catch (FirebaseAuthUserCollisionException e) {
                        Toast.makeText(CadastroEmailActivity.this,"Usuário já é cadastrado!",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(CadastroEmailActivity.this,"Erro!"+e.getMessage(),Toast.LENGTH_SHORT).show();;
                    }
                }
            }
        });
    }

}
