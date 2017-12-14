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
import br.com.eduardo.dudazap.model.Conversa;

/**
 * Created by Eduardo on 14/12/2017.
 */

public class ConversaAdapter  extends ArrayAdapter<Conversa> {
    private ArrayList<Conversa> conversas;
    private  Context context;
    public ConversaAdapter(Context c,  ArrayList<Conversa> objects) {
        super(c, 0, objects);
        this.conversas = objects;
        this.context = c;
    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        if(conversas == null){
            return null;
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.lista_conversas,parent,false);


        final Conversa conversa = conversas.get(position);

        TextView nome = (TextView) view.findViewById(R.id.conversas_nome);
        TextView email = (TextView) view.findViewById(R.id.conversas_ultima);

        email.setText(conversa.getNome());
        nome.setText(conversa.getMensagem());


        return view;
    }
}
