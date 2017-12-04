package br.com.eduardo.dudazap.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 04/12/2017.
 */

public class Permissao {

    public  static  boolean validaPermissoes(int requestCode,Activity activity,String[] permissoes){
        if(Build.VERSION.SDK_INT >= 23){
            List<String> listaPermissoes = new ArrayList<String>();
            for(String permissao : permissoes){
                // verifica a permissao
                if(!(ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_GRANTED)){
                    listaPermissoes.add(permissao);
                }
            }

            if(listaPermissoes.isEmpty()){
                return true;
            }

            String[] perms = new String[listaPermissoes.size()];
            listaPermissoes.toArray(perms);

            //SolicitaPErmissao
            ActivityCompat.requestPermissions(activity,perms,requestCode);

        }


        return true;
    }

}
