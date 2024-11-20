package com.example.sharedpreferenceandsqlite.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedpreferenceandsqlite.R;
import com.example.sharedpreferenceandsqlite.adapter.UserListAdapter;
import com.example.sharedpreferenceandsqlite.database.DbHandler;
import com.example.sharedpreferenceandsqlite.model.UserModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    TextView userNameAdmin, userEmailAdmin;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    DbHandler dbHandler;
    String email;

    RecyclerView userRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        /** Since Username and email is inside navigation view so we have to access this way */
        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        View headerView = navigationView.getHeaderView(0);
        userNameAdmin = headerView.findViewById(R.id.userName);
        userEmailAdmin = headerView.findViewById(R.id.userEmail);

        drawerLayout = findViewById(R.id.admin_drawer);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);

        userRecyclerView = findViewById(R.id.rv_admin);

        /* DrawerLayout */
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** Get email from intent */
        email = getIntent().getStringExtra("email");

        /** Method to get admin details */
        getAdminDetails();

        /** Database handler */
        dbHandler = new DbHandler(this);

        /** Fetch all user details */
        ArrayList<UserModel> userList = dbHandler.getAllUserDetails();

        /** Set adapter */
        UserListAdapter adapter = new UserListAdapter(userList);
        userRecyclerView.setAdapter(adapter);

    }

    public void getAdminDetails(){
        Log.d("TAG", "getAdminDetails: " + email);
        userNameAdmin.setText("Admin");
        userEmailAdmin.setText(email);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}