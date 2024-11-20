package com.example.sharedpreferenceandsqlite;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sharedpreferenceandsqlite.auth.Login;
import com.example.sharedpreferenceandsqlite.auth.Register;

public class MainActivity extends AppCompatActivity {
    FrameLayout loginFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginFrame = findViewById(R.id.main);

        /** Fragment Manager to replace main fragment with login fragment and commit */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main, new Login());
        fragmentTransaction.commit();
    }
}