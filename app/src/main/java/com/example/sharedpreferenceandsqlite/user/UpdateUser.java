package com.example.sharedpreferenceandsqlite.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sharedpreferenceandsqlite.R;
import com.example.sharedpreferenceandsqlite.database.DbHandler;

public class UpdateUser extends AppCompatActivity {
    EditText etName, etPassword;
    TextView tvUpdateEmail;
    Button btnUpdateUser;
    String email, name, password;
    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        dbHandler = new DbHandler(this);

        tvUpdateEmail = findViewById(R.id.tvEmailUpdate);
        etName = findViewById(R.id.etNameUpdate);
        etPassword = findViewById(R.id.etPasswordUpdate);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");

        tvUpdateEmail.setText(email);
        etName.setText(name);
        etPassword.setText(password);

        btnUpdateUser.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String password = etPassword.getText().toString();

            boolean data = dbHandler.updateUser(email,name,password);

            Log.d("TAG", "onCreate1: " + name  + password);
            if (data){
                Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}