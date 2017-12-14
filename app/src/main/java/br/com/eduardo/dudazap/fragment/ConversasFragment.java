package br.com.eduardo.dudazap.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import br.com.eduardo.dudazap.adapter.ConversaAdapter;
import br.com.eduardo.dudazap.dudazap.ConversaActivity;
import br.com.eduardo.dudazap.dudazap.R;
import br.com.eduardo.dudazap.helper.Base64Custom;
import br.com.eduardo.dudazap.helper.ConfigFirebase;
import br.com.eduardo.dudazap.helper.Preferencias;
import br.com.eduardo.dudazap.model.Contato;
import br.com.eduardo.dudazap.model.Conversa;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;

    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;

    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Preferencias preferencias = new Preferencias(getActivity());
        String idUserLogado = preferencias.getIDentificador();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);
        conversas = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.list_conversas);
        adapter = new ConversaAdapter(getActivity(),conversas);
        listView.setAdapter(adapter);

        //Recuperar conversas do firebase
        firebase = ConfigFirebase.getFirebase().child("conversas").child(idUserLogado);


        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversas.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Conversa conversa = dados.getValue(Conversa.class);
                    conversas.add(conversa);
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
                Conversa conversa = conversas.get(i);
                intent.putExtra("nome",conversa.getNome());
                String email = Base64Custom.decodificarBase64(conversa.getIdUsuario());
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }


}
