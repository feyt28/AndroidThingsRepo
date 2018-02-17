package dev.aot.markkevindalbios.quarantinemonitoring.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import dev.aot.markkevindalbios.quarantinemonitoring.ProfileActivity;
import dev.aot.markkevindalbios.quarantinemonitoring.R;
import dev.aot.markkevindalbios.quarantinemonitoring.model.Person;

/**
 * Created by mark.kevin.d.albios on 2/2/2018.
 */

public class QuarantineListAdapter extends BaseAdapter {

    private ArrayList<Person> list;
    private Context context;
    private ViewHolder holder;
    private Person person;

    public QuarantineListAdapter(Context context, ArrayList<Person> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return (Person) list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, final ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.row_list, parent, false );
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        if(getCount() > 0){
            person = (Person) getItem(position);
            holder.nameView.setText(person.getlName() + ", " + person.getfName());
            holder.dateView.setText(""+person.getDateOfBirth());

            Picasso.with(context)
                    .load(person.getImageUrl())
                    .resize(70,70)
                    .centerCrop()
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.image);
        }

        return view;
    }

    private class ViewHolder{
        CircularImageView image;
        TextView nameView;
        TextView dateView;

        public ViewHolder(View view){
            image = (CircularImageView) view.findViewById(R.id.image);
            nameView = (TextView) view.findViewById(R.id.name);
            dateView = (TextView) view.findViewById(R.id.date);
        }
    }
}
