package com.example.desginstofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForGotMyPass extends AppCompatActivity {
    Button buttonPwdRest;
    EditText editTextEmail;
    ProgressBar progressBar;
    FirebaseAuth fireProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_got_my_pass);

        //getSupportActionBar().setTitle("Forgot Password");

        buttonPwdRest = (Button) findViewById(R.id.btn1);
        editTextEmail=(EditText) findViewById(R.id.eTForGot1);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        buttonPwdRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForGotMyPass.this, "plaese enter valid Email!", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Email is required!");
                    editTextEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ForGotMyPass.this, "plaese enter valid Email!", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("valid Email is required!");
                    editTextEmail.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    restPassword(email);
                }
            }
        });
    }

    private void restPassword(String email) {
        fireProfile=FirebaseAuth.getInstance();
        fireProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForGotMyPass.this, "plaese check your inbox for password rest like",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForGotMyPass.this,StudentLogIN.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ForGotMyPass.this, "email is not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}