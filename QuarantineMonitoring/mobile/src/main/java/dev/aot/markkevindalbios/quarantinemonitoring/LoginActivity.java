package dev.aot.markkevindalbios.quarantinemonitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import dev.aot.markkevindalbios.quarantinemonitoring.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);
    }

    public void login(View view){
        if(validate()){
            Intent i = new Intent(this, MainActivity.class);
            this.finish();
            startActivity(i);
        }
    }

    private boolean validate(){
        boolean result = false;

        String user = username.getText().toString();
        String pass = password.getText().toString();

        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Input Username/Password!",Toast.LENGTH_SHORT).show();
        }else if(!user.equals(Utils.ADMIN_USER) || !pass.equals(Utils.ADMIN_PASS)){
            Toast.makeText(this, "Invalid Username/Password!",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }
}
