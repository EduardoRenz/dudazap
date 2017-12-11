package br.com.eduardo.dudazap.dudazap;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.eduardo.dudazap.adapter.TabAdapter;
import br.com.eduardo.dudazap.helper.Base64Custom;
import br.com.eduardo.dudazap.helper.ConfigFirebase;
import br.com.eduardo.dudazap.helper.Preferencias;
import br.com.eduardo.dudazap.helper.SlidingTabLayout;
import br.com.eduardo.dudazap.model.Contato;
import br.com.eduardo.dudazap.model.Usuario;

public class PrincipalActivity extends AppCompatActivity {
    private Button logout;
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private FirebaseUser usuarioFirebase;

    private  String IdContato;

    private DatabaseReference firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("DudaZap");

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.viwer_pagina) ;

        //Configurar o adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        slidingTabLayout.setDistributeEvenly(true);
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);

        //Configurar as tabs

        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));


        setSupportActionBar(toolbar);
        auth = ConfigFirebase.getFirebaseAuth();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_adicionar:
                abrirAdicionarUsuario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void abrirAdicionarUsuario() {
        AlertDialog.Builder addDialog = new AlertDialog.Builder(PrincipalActivity.this);
        addDialog.setTitle("Novo contato");
        addDialog.setMessage("Email do usuário");
        addDialog.setCancelable(false);

        final EditText editText = new EditText(PrincipalActivity.this);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        addDialog.setView(editText);

        addDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String emailContato = editText.getText().toString();
                if(emailContato.isEmpty()){
                    Toast.makeText(PrincipalActivity.this,"Preencha  o email",Toast.LENGTH_LONG).show();
                    return;
                }




                //Recuperar dados de contato do user a ser adicionado





                //Verificar se contato é cadastrado
                IdContato = Base64Custom.codificarBase64(emailContato);

                firebase = ConfigFirebase.getFirebase();
                firebase = firebase.child("usuarios").child(IdContato);
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot != null){

                            Usuario contatodados = dataSnapshot.getValue(Usuario.class);


                            Contato contato = new Contato();
                            contato.setId(IdContato);
                            contato.setEmail(contatodados.getEmail());
                            contato.setNome(contatodados.getNome());

                            //Recuperar id do usuario logado
                            Preferencias preferencias = new Preferencias(PrincipalActivity.this);
                            String idUsuario  = preferencias.getIDentificador();

                            auth.getCurrentUser().getEmail();
                            firebase = ConfigFirebase.getFirebase();
                            firebase = firebase.child("contatos").child(idUsuario).child(IdContato);

                            firebase.setValue(contato);

                        }
                        else {
                            Toast.makeText(PrincipalActivity.this,"Cadastro não existe",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Log.d("CADAS","cadastrou");
            }
        });

        addDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("CADAS","cancelou");
            }
        });
        addDialog.create();
        addDialog.show();

    }

    private void deslogarUsuario() {
        auth.signOut();
        startActivity(new Intent(PrincipalActivity.this,MainActivity.class));
        finish();
    }
}
