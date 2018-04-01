package com.example.ayupriyambodo.bismillahselesai;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity {
    private AutoCompleteTextView inpEmail;
    private EditText inpPassword;
    private FirebaseAuth auth;
    private Button btSignUp;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        inpEmail = (AutoCompleteTextView)findViewById(R.id.email);
        inpPassword = (EditText)findViewById(R.id.password);
        btSignUp = (Button)findViewById(R.id.email_sign_in_button);
        mProgressBar = (ProgressBar)findViewById(R.id.login_progress);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = inpEmail.getText().toString().trim();
                password = inpPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Masukin Emial!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Masukan passwordnya !!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                //membuat user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "User email berhasil dibuat" + task.isSuccessful(),
                                        Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()){
                                    Toast.makeText(SignUpActivity.this, "Auntentikasi gagal" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}

