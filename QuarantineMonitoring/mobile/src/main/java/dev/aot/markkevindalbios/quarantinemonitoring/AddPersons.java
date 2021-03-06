package dev.aot.markkevindalbios.quarantinemonitoring;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import dev.aot.markkevindalbios.quarantinemonitoring.model.Person;

public class AddPersons extends AppCompatActivity {

    private static final String TAG = AddPersons.class.getName();

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private StorageReference storageReference;

    private static final int CAMERA_INTENT = 0;

    //Views
    private CircularImageView image;
    private EditText fNameView;
    private EditText mNameView;
    private EditText lNameView;
    static private EditText dateOfBirth;
    private EditText address;
    private EditText contactNo;
    private ToggleButton genderView;

    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        database = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    private void initViews() {
        image = (CircularImageView) findViewById(R.id.image);
        fNameView = (EditText) findViewById(R.id.fName);
        lNameView = (EditText) findViewById(R.id.lName);
        mNameView = (EditText) findViewById(R.id.mName);
        genderView = (ToggleButton) findViewById(R.id.gender);
        dateOfBirth = (EditText) findViewById(R.id.bDate);
        address = (EditText) findViewById(R.id.address);
        contactNo = (EditText) findViewById(R.id.contactno);
    }

    public void showCalendar(View view){
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "DatePicker");
    }

    public void cancel(View view){
        this.finish();
    }

    public boolean validate(){
        boolean result = true;
        final String fName = fNameView.getText().toString();
        final String mName = mNameView.getText().toString();
        final String lName = lNameView.getText().toString();
        final String bDay = dateOfBirth.getText().toString();
        final String loc = address.getText().toString();
        final String contact = contactNo.getText().toString();

        if(TextUtils.isEmpty(fName) || TextUtils.isEmpty(mName) || TextUtils.isEmpty(lName) || TextUtils.isEmpty(bDay)
                || TextUtils.isEmpty(loc) || TextUtils.isEmpty(contact)){
            result = false;
        }
        return result;
    }


    public void addPerson(View view) {

        final String fName = fNameView.getText().toString();
        final String mName = mNameView.getText().toString();
        final String lName = lNameView.getText().toString();
        final String gender = genderView.getText().toString();
        final String bday = dateOfBirth.getText().toString();
        final String addr = address.getText().toString();
        final String contact = contactNo.getText().toString();

        if(validate()){
            if (imageBitmap != null) {
                final ProgressDialog progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Adding Person");
                progressDialog.show();

                Uri uri = getImageUri(this, imageBitmap);
                StorageReference filepath = storageReference.child("QuarantineImage").child(lName + ", " + fName);
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downUri = taskSnapshot.getDownloadUrl();
                        String imageUrl = downUri.toString();

                        dbRef = database.getReference("Quarantines").push();
                        String id = dbRef.getKey();

                        Person person = new Person(id, fName, mName, lName, bday, gender, imageUrl, addr, contact);
                        dbRef.setValue(person).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();

                                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                                clearInputs();
                                AddPersons.this.finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "Add Image!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Input All Fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputs(){
        image.setBackground(getDrawable(R.mipmap.ic_launcher_round));
        image.setImageBitmap(null);
        fNameView.setText("");
        lNameView.setText("");
        mNameView.setText("");
        dateOfBirth.setText("");
        contactNo.setText("");
        address.setText("");
    }

    private void launchCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(captureIntent, CAMERA_INTENT);
        }
    }

    public void addImage(View view) {
        launchCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_INTENT && resultCode == RESULT_OK) {
            if (data != null) {

                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                image.setBackground(null);
                image.setImageBitmap(imageBitmap);
            }
        }
    }

    private Uri getImageUri(Context con, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(con.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_INTENT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                }
                break;
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            AddPersons.dateOfBirth.setText(""+(month + 1) + "/" + day + "/" +year);
        }
    }

}
