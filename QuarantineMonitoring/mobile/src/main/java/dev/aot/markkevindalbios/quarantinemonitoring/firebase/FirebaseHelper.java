package dev.aot.markkevindalbios.quarantinemonitoring.firebase;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import dev.aot.markkevindalbios.quarantinemonitoring.model.Person;

/**
 * Created by mark.kevin.d.albios on 2/2/2018.
 */

public class FirebaseHelper {

    private DatabaseReference dbRef;
    private Boolean saved;
    private ArrayList<Person> personList = new ArrayList<Person>();

    public FirebaseHelper(DatabaseReference ref){
        this.dbRef = ref;
    }

    public Boolean addPerson(Person person){
        if(person == null){
            saved = false;
        }else{
            try{
                dbRef.child("Quarantines").push().setValue(person);
                saved = true;
            }catch(DatabaseException e){
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }

}
