package dev.aot.markkevindalbios.quarantinemonitoring;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dev.aot.markkevindalbios.quarantinemonitoring.model.Person;

public class AddPersons extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private EditText fNameView;
    private EditText mNameView;
    private EditText lNameView;
    private EditText ageView;
    private ToggleButton genderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_persons);

        initViews();
        database = FirebaseDatabase.getInstance();

    }

    private void initViews(){
        fNameView = (EditText) findViewById(R.id.fName);
        lNameView = (EditText) findViewById(R.id.lName);
        mNameView = (EditText) findViewById(R.id.mName);
        ageView = (EditText) findViewById(R.id.age);
        genderView = (ToggleButton) findViewById(R.id.gender);
    }

    public void addPerson(View view){

        String fName = fNameView.getText().toString();
        String mName = mNameView.getText().toString();
        String lName = lNameView.getText().toString();
        int age = Integer.parseInt(ageView.getText().toString());
        String gender = genderView.getText().toString();

        Person person = new Person(fName, mName, lName, age, gender);

        dbRef = database.getReference("Quarantines").push();
        dbRef.setValue(person);
    }

}
