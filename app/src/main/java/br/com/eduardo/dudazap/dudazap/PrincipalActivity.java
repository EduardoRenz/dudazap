package br.com.eduardo.dudazap.dudazap;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import br.com.eduardo.dudazap.adapter.TabAdapter;
import br.com.eduardo.dudazap.helper.ConfigFirebase;
import br.com.eduardo.dudazap.helper.SlidingTabLayout;

public class PrincipalActivity extends AppCompatActivity {
    private Button logout;
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void deslogarUsuario() {
        auth.signOut();
        startActivity(new Intent(PrincipalActivity.this,MainActivity.class));
        finish();
    }
}
