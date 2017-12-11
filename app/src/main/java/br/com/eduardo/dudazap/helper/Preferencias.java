package br.com.eduardo.dudazap.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Eduardo on 04/12/2017.
 */

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "Dudazap.preferencias";
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "idUsuarioLogado";

    public  Preferencias(Context contexto){
        this.contexto = contexto;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO,Context.MODE_PRIVATE);
        editor = preferences.edit();


    }

    public  void salvarDados(String idUsuario){
        editor.putString(CHAVE_IDENTIFICADOR,idUsuario);

        editor.commit();
    }

    public HashMap<String,String> getDadosUsuario(){
        HashMap<String,String> dadossuario = new HashMap<>();
        dadossuario.put("nome",preferences.getString("nome",null));
        dadossuario.put("telefone",preferences.getString("telefone",null));
        dadossuario.put("token",preferences.getString("token",null));
        return dadossuario;
    }



    public String getIDentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR,null);
    }

}
