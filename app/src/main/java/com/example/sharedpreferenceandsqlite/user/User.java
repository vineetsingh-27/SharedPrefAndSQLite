package com.example.sharedpreferenceandsqlite.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.sharedpreferenceandsqlite.R;
import com.example.sharedpreferenceandsqlite.database.DbHandler;
import com.example.sharedpreferenceandsqlite.model.UserModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class User extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TextView userName, userEmail;
    TextView tvName, tvEmail, tvPassword;
    Button btnUpdate, btnDelete;
    DbHandler dbHandler;
    String email,name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        /** Since Username and email is inside navigation view so we have to access this way */
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.userName);
        userEmail = headerView.findViewById(R.id.userEmail);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        /* DrawerLayout */
        drawerLayout = findViewById(R.id.main);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         email = getIntent().getStringExtra("email");
//         name = getIntent().getStringExtra("new_name");
//         password = getIntent().getStringExtra("new_password");

        getUserDetails();

        btnUpdate.setOnClickListener(v ->{
            Intent updateIntent = new Intent(this, UpdateUser.class);
            updateIntent.putExtra("email",email);
            updateIntent.putExtra("name",tvName.getText().toString());
            updateIntent.putExtra("password",tvPassword.getText().toString());
            startActivity(updateIntent);
        });
    }

    /** Get User Details */

    public void getUserDetails(){
        dbHandler = new DbHandler(this);
        ArrayList<UserModel> arrayList = dbHandler.getLoggedInUserDetails(email);
        if (!arrayList.isEmpty()){
            userName.setText(arrayList.get(0).getName());
            userEmail.setText(arrayList.get(0).getEmail());

            tvName.setText(arrayList.get(0).getName());
            tvEmail.setText(arrayList.get(0).getEmail());
            tvPassword.setText(arrayList.get(0).getPassword());
        }else{
            Log.d("TAG", "getUserDetails: Error");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}