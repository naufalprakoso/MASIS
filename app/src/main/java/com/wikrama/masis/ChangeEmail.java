package com.wikrama.masis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmail extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button resetBtn;
    private EditText emailEdt;
    private String Email;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        user = FirebaseAuth.getInstance().getCurrentUser();

        resetBtn = (Button) findViewById(R.id.resetBtn);
        mAuth = FirebaseAuth.getInstance();
        emailEdt = (EditText) findViewById(R.id.emailEdt);
        mProgressDialog = new ProgressDialog(this);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = emailEdt.getText().toString().trim();
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.show();
                user.updateEmail(Email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangeEmail.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), UserProfile.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(ChangeEmail.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }
                                mProgressDialog.dismiss();
                            }
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
