package com.example.saharsh.bechdo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button Registration;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email=(EditText)findViewById(R.id.email_up);
        password=(EditText)findViewById(R.id.pass_up);
        Registration=(Button)findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();



    }

    public void RegisterEmailPass(View view) {
        final String getEmail=email.getText().toString();
        final String getPass =password.getText().toString();

        if (TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPass)){
            Log.i("Tag","Fill Credential");
            Toast.makeText(RegistrationActivity.this,"Fill Credentials",Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(getEmail, getPass)
                    .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("Tag", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();



                                Toast.makeText(RegistrationActivity.this, "Successfully Registered",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.i("Tag", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });



        }

    }
}
