package com.example.ayupriyambodo.bismillahselesai;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText mPassword;
    private AutoCompleteTextView mEmail;
    private FirebaseAuth auth;
    private ProgressBar aProgressBar;
    private Button btnSignin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() !=null){
            startActivity(new Intent(LoginActivity.this, Home.class));
            finish();
        }
        mEmail = (AutoCompleteTextView)findViewById(R.id.email_login);
        mPassword = (EditText)findViewById(R.id.password_login);
        btnSignin = (Button)findViewById(R.id.email_sign_in);
        btnSignup = (Button)findViewById(R.id.signup);
        aProgressBar = (ProgressBar)findViewById(R.id.progress_login);

        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Masukin Email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Masukin Passwor",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                aProgressBar.setVisibility(View.VISIBLE);
                //atentikasi user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                aProgressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()){
                                    if (password.length() < 6){
                                        mPassword.setError(getString(R.string.minimum_password));
                                    }else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Intent intent = new Intent(LoginActivity.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}

