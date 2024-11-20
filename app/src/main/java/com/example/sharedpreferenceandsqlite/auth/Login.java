package com.example.sharedpreferenceandsqlite.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharedpreferenceandsqlite.R;
import com.example.sharedpreferenceandsqlite.admin.Admin;
import com.example.sharedpreferenceandsqlite.database.DbHandler;
import com.example.sharedpreferenceandsqlite.user.User;

public class Login extends Fragment {
    EditText etEmail, etPassword;
    RadioButton rbAdmin, rbUser;
    Button btnLogin;
    TextView txtRegister;
    DbHandler dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        rbAdmin = view.findViewById(R.id.rbAdmin);
        rbUser = view.findViewById(R.id.rbUser);
        btnLogin = view.findViewById(R.id.btnLogin);
        txtRegister = view.findViewById(R.id.txtRegister);

        /** DbHandler */
        dbHandler = new DbHandler(getContext());

        /** SharedPreferences */
        SharedPreferences sp = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        /** Login Button */
        btnLogin.setOnClickListener(v ->{
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            /** Admin Login */
            if (rbAdmin.isChecked()){
                if (email.equals("admin") && password.equals("admin")){
                    etEmail.setText("");
                    etPassword.setText("");
                    Intent loginIntent = new Intent(getActivity(), Admin.class);
                    loginIntent.putExtra("email",email);
                    startActivity(loginIntent);
                }else{
                    etEmail.setText("");
                    etPassword.setText("");
                    Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
                /** User Login */
            }else if (rbUser.isChecked()){
                boolean isValid = dbHandler.validateUser(email,password);
                if (isValid){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.apply();

                    Intent loginIntent = new Intent(getActivity(), User.class);
                    loginIntent.putExtra("email",email);
                    startActivity(loginIntent);
                }
            }else{
                Toast.makeText(getContext(), "Select any mode", Toast.LENGTH_SHORT).show();
            }
        });

        /** Register Button */
        txtRegister.setOnClickListener(v->{
            Fragment registerFragment = new Register();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, registerFragment)
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }
}