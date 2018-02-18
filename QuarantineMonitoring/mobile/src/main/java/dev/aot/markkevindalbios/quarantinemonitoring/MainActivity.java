package dev.aot.markkevindalbios.quarantinemonitoring;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import java.util.zip.CheckedOutputStream;

import dev.aot.markkevindalbios.quarantinemonitoring.adapter.QuarantineAdapter;
import dev.aot.markkevindalbios.quarantinemonitoring.adapter.QuarantineListAdapter;
import dev.aot.markkevindalbios.quarantinemonitoring.adapter.RecyclerItemTouchHelper;
import dev.aot.markkevindalbios.quarantinemonitoring.model.Person;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private static final String TAG = MainActivity.class.getCanonicalName();
    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private ListView listView;
    private ArrayList<Person> list;
    private QuarantineAdapter adapter;

    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;

    boolean isReadyToBeDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        //list = new ArrayList<Person>();
        //adapter = new QuarantineAdapter(this, list);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddPersons.class);
                startActivity(addIntent);
            }
        });

        database = FirebaseDatabase.getInstance();
        loadQuarantineList();
//        loadRecycler();

//        listView = (ListView) findViewById(R.id.list);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Person person = (Person) adapterView.getItemAtPosition(position);
//                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
//                profileIntent.putExtra("profile", person);
//                startActivity(profileIntent);
//            }
//        });
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

    private void loadRecycler(){
//        adapter = new QuarantineAdapter(this, list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void loadQuarantineList(){

        Log.i(TAG, "Loading Quarantine list");
        dbRef = database.getReference("Quarantines");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<Person>();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Person person = postSnapshot.getValue(Person.class);
                    list.add(person);
                }
                adapter = new QuarantineAdapter(getApplicationContext(), list);
                //listView.setAdapter(adapter);
                loadRecycler();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        isReadyToBeDeleted = true;

        if (viewHolder instanceof QuarantineAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            Person person = (Person) list.get(viewHolder.getAdapterPosition());
            String name = person.getfName() + " " + person.getlName();

            // backup of removed item for undo purpose
            final Person deletedItem = list.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                    isReadyToBeDeleted = false;
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
//
//            if(isReadyToBeDeleted){
//                dbRef = database.getReference("Quarantines");
//                dbRef.child(person.getId()).removeValue();
//            }
        }


    }

}
