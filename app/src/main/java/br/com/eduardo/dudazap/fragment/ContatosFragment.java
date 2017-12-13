package br.com.eduardo.dudazap.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.eduardo.dudazap.adapter.ContatoAdapter;
import br.com.eduardo.dudazap.dudazap.ConversaActivity;
import br.com.eduardo.dudazap.dudazap.R;
import br.com.eduardo.dudazap.helper.ConfigFirebase;
import br.com.eduardo.dudazap.helper.Preferencias;
import br.com.eduardo.dudazap.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private  ArrayList<Contato> contatos;

    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        contatos = new ArrayList<>();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contatos, container, false);

        listView = (ListView) view.findViewById(R.id.contatos);

        adapter = new ContatoAdapter(getActivity(),contatos);
        listView.setAdapter(adapter);



          Preferencias preferencias = new Preferencias(getActivity());

        firebase = ConfigFirebase.getFirebase().child("contatos").child(preferencias.getIDentificador());
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                contatos.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),ConversaActivity.class);
                startActivity(intent);
            }
        });


        return  view;
    }


    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListener);
    }
    @Override
    public  void onStop(){
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }

}
