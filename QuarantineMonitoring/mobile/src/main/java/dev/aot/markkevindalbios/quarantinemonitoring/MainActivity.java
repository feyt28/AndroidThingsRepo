package dev.aot.markkevindalbios.quarantinemonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

        database = FirebaseDatabase.getInstance();

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Person person = (Person) adapterView.getItemAtPosition(position);
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                profileIntent.putExtra("profile", person);
                startActivity(profileIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                break;
            case R.id.logout:
                Intent i = new Intent(this, LoginActivity.class);
                this.finish();
                startActivity(i);
                break;
                default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadQuarantineList();
    }

    private void loadQuarantineList(){

        dbRef = database.getReference("Quarantines");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<Person>();
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
