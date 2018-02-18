package dev.aot.markkevindalbios.quarantinemonitoring;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import dev.aot.markkevindalbios.quarantinemonitoring.model.Person;

public class ProfileActivity extends AppCompatActivity {

    private CircularImageView imageView;
    private TextView name;
    private Person person;
    private TextView contactno;
    private TextView bDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        person = (Person)getIntent().getExtras().getSerializable("profile");
        init();
    }

    private void init(){
        imageView = (CircularImageView)findViewById(R.id.profile);
        name = (TextView)findViewById(R.id.name);
        contactno = (TextView)findViewById(R.id.contactno);
        bDate = (TextView)findViewById(R.id.bDate);

        name.setText(person.getfName() + " " + person.getlName());
        Picasso.with(this)
                .load(person.getImageUrl())
                .resize(70,70)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
        contactno.setText(person.getContactNo());
        bDate.setText(person.getDateOfBirth());
    }
}
