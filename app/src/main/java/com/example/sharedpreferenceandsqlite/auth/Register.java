package com.example.sharedpreferenceandsqlite.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharedpreferenceandsqlite.R;
import com.example.sharedpreferenceandsqlite.database.DbHandler;

public class Register extends Fragment {
    EditText etName, etEmail, etPassword;
    Button registerBtn;
    DbHandler dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        etName = view.findViewById(R.id.etNameRegister);
        etEmail = view.findViewById(R.id.etEmailRegister);
        etPassword = view.findViewById(R.id.etPasswordRegister);
        registerBtn = view.findViewById(R.id.btnRegister);

        /** DbHandler */
        dbHandler = new DbHandler(getContext());

        /** Register Button */
        registerBtn.setOnClickListener(v ->{
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            /** Validation */
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            }else{
                boolean result = dbHandler.addUser(name,email,password);
                Log.d("TAG", "register1: " + name + email + password);

                /** Open Login Fragment */
                Fragment loginFragment = new Login();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, loginFragment)
                        .commit();

                if (result){
                    Toast.makeText(getContext(), "User added successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "User not added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}