package br.com.eduardo.dudazap.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Eduardo on 04/12/2017.
 */

public final class ConfigFirebase {
    private static DatabaseReference databaseReference ;
    private static FirebaseAuth auth;

    public static DatabaseReference getFirebase(){

        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }


    public static  FirebaseAuth getFirebaseAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return  auth;
    }

}
