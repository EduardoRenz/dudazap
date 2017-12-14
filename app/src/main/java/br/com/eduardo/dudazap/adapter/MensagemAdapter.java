package br.com.eduardo.dudazap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.eduardo.dudazap.dudazap.R;
import br.com.eduardo.dudazap.helper.Preferencias;
import br.com.eduardo.dudazap.model.Mensagem;

/**
 * Created by Eduardo on 14/12/2017.
 */

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private  ArrayList<Mensagem> mensagens;
    public MensagemAdapter(Context c,  @NonNull ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }



    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View view;

        if(mensagens == null){
            return  null;
        }


        Preferencias preferencias = new Preferencias(context);
        String idUserRemetente = preferencias.getIDentificador();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        Mensagem msn = getItem(position);

        if(idUserRemetente.equals(msn.getIdUsuario())){
            //Montar a view a partir do xml
            view = inflater.inflate(R.layout.item_mensagem_direita,parent,false);
        }
        else {
            view = inflater.inflate(R.layout.item_mensagem_esquerda,parent,false);
        }


        //Recuperar elemento para exibir



        TextView txtMensagem = (TextView) view.findViewById(R.id.mensagem_esquerda);
        txtMensagem.setText(msn.getMensagem().toString());
        Log.d("Text menage",txtMensagem.getText().toString());

        return view;
    }
}
