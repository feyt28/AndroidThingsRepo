package dev.aot.markkevindalbios.quarantinemonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dev.aot.markkevindalbios.quarantinemonitoring.adapter.QuarantineListAdapter;
import dev.aot.markkevindalbios.quarantinemonitoring.model.Person;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private ListView listView;
    private ArrayList<Person> list;
    private QuarantineListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddPersons.class);
                startActivity(addIntent);
            }
        });

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance();

        loadQuarantineList();
        adapter = new QuarantineListAdapter(this, list);

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    private void loadQuarantineList(){
        list = new ArrayList<Person>();
        dbRef = database.getReference("Quarantines");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Person person = postSnapshot.getValue(Person.class);
                    list.add(person);
                }
                adapter = new QuarantineListAdapter(getApplicationContext(), list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
