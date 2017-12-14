package br.com.eduardo.dudazap.dudazap;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.eduardo.dudazap.adapter.MensagemAdapter;
import br.com.eduardo.dudazap.helper.Base64Custom;
import br.com.eduardo.dudazap.helper.ConfigFirebase;
import br.com.eduardo.dudazap.helper.Preferencias;
import br.com.eduardo.dudazap.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Bundle extra ;
    private ImageButton enviar;
    private EditText mensagem;
    private DatabaseReference firebase;
    ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagem;
    //Dados de envio
    private String idUserRemetente,idUserDestinatario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        enviar = (ImageButton) findViewById(R.id.bt_enviar_mensagem);
        mensagem = (EditText) findViewById(R.id.txt_enviar_mensagem);
        extra = getIntent().getExtras();
        Log.d("Iniciao emi",extra.getString("email"));
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        listView = (ListView)findViewById(R.id.lv_conversas);
        if(extra != null){
            toolbar.setTitle(extra.getString("nome"));
        }
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);


        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUserRemetente = preferencias.getIDentificador();
        String emailDestinatario = extra.getString("email");
        idUserDestinatario = Base64Custom.codificarBase64(emailDestinatario) ;


        // Montar o adapter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this,mensagens);
        listView.setAdapter(adapter);

        //Recuperar as mensagens do adapter
        firebase = ConfigFirebase.getFirebase()
                .child("mensagens")
                .child(idUserRemetente)
                .child(idUserDestinatario);


        //Listener das mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensagens.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Mensagem mensagem =  dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener(valueEventListenerMensagem);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoMensagem = mensagem.getText().toString();
                if(textoMensagem.isEmpty()){
                    Toast.makeText(ConversaActivity.this,"Digite uma mensagem",Toast.LENGTH_LONG).show();
                    return;
                }



                Mensagem mensageiro = new Mensagem();
                mensageiro.setIdUsuario(idUserRemetente);
                mensageiro.setMensagem(textoMensagem);
                salvarMensagem(idUserRemetente,idUserDestinatario,mensageiro);
                salvarMensagem(idUserDestinatario,idUserRemetente,mensageiro);
                mensagem.setText("");
            }
        });


    }

    private boolean salvarMensagem(String idRemetente,String idDestinatario,Mensagem mensagem) {
        try {
            firebase = ConfigFirebase.getFirebase().child("mensagens");
            Log.d("Remetente",idRemetente);
            Log.d("Destinatario",idDestinatario);
            Log.d("Mensagem",mensagem.getMensagem());
            firebase.child(idRemetente).child(idDestinatario).push().setValue(mensagem);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMensagem);
    }
}
