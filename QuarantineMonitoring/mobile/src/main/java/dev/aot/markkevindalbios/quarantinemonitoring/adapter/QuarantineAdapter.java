package dev.aot.markkevindalbios.quarantinemonitoring.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dev.aot.markkevindalbios.quarantinemonitoring.ProfileActivity;
import dev.aot.markkevindalbios.quarantinemonitoring.R;
import dev.aot.markkevindalbios.quarantinemonitoring.model.Person;

/**
 * Created by mark.kevin.d.albios on 2/18/2018.
 */

public class QuarantineAdapter extends RecyclerView.Adapter<QuarantineAdapter.MyViewHolder> {

    private static final String TAG = QuarantineAdapter.class.getCanonicalName();
    private Context context;
    private ArrayList<Person> list;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    public QuarantineAdapter(Context context, ArrayList<Person> list){
        this.context = context;
        this.list = list;
        Log.i(TAG, "List: "+list.size());
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.row_list1, parent, false );
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Person person = list.get(position);
        holder.nameView.setText(person.getlName() + ", " + person.getfName());
        holder.dateView.setText(""+person.getDateOfBirth());
        Picasso.with(context)
                .load(person.getImageUrl())
                .resize(70,70)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void removeItem(int position){

        Person person = (Person) list.get(position);
        dbRef = database.getReference("Quarantines");
        dbRef.child(person.getId()).removeValue();

        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Person person, int position){
        dbRef = database.getReference("Quarantines");
        Log.i(TAG, "Person: "+person.getlName());

        dbRef.child(person.getId()).setValue(person);

        list.add(position, person);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircularImageView image;
        TextView nameView;
        TextView dateView;
        RelativeLayout background;
        LinearLayout foreground;

        public MyViewHolder(final View view){
            super(view);
            image = (CircularImageView) view.findViewById(R.id.image);
            nameView = (TextView) view.findViewById(R.id.name);
            dateView = (TextView) view.findViewById(R.id.date);
            background = (RelativeLayout) view.findViewById(R.id.view_background);
            foreground = (LinearLayout) view.findViewById(R.id.view_foreground);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Person person = (Person) list.get(pos);
                    Intent profileIntent = new Intent(context, ProfileActivity.class);
                    profileIntent.putExtra("profile", person);
                    context.startActivity(profileIntent);
                }
            });
        }
    }
}
