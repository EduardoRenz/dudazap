package br.com.eduardo.dudazap.dudazap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.eduardo.dudazap.helper.ConfigFirebase;

public class MainActivity extends AppCompatActivity {
    Button cadastrar ;
    private DatabaseReference refDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cadastrar = (Button)findViewById(R.id.bt_cadastrar);

        refDatabase = ConfigFirebase.getFirebase();
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CadastroEmailActivity.class));
            }
        });

    }



}
