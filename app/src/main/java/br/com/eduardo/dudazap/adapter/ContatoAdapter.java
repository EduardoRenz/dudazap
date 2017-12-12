package br.com.eduardo.dudazap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.eduardo.dudazap.dudazap.R;
import br.com.eduardo.dudazap.model.Contato;

/**
 * Created by Eduardo on 11/12/2017.
 */

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private  ArrayList<Contato> contatos;
    private  Context context;

    public ContatoAdapter(@NonNull Context c,  ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.contatos = objects;
        this.context = c;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        if(contatos == null){
            return null;
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.lista_contatos,parent,false);

        Contato contato = contatos.get(position);

        TextView nome = (TextView) view.findViewById(R.id.contato_nome);
        TextView email = (TextView) view.findViewById(R.id.contato_email);

        email.setText(contato.getEmail());
        nome.setText(contato.getNome());
        return view;
    }
}
