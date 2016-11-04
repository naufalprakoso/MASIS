package com.wikrama.masis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailEdt, passwordEdt;
    private String Email, Password;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private int passwordLenght;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerBtn = (Button) findViewById(R.id.registerBtn);
        emailEdt = (EditText) findViewById(R.id.emailEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);

        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.registerBtn:
                Email = emailEdt.getText().toString().trim();
                Password = passwordEdt.getText().toString().trim();
                passwordLenght = passwordEdt.getText().length();
                if (Email.equals("") || Password.equals("")){
                    Toast.makeText(RegisterActivity.this, "Can't Empty", Toast.LENGTH_SHORT).show();
                }else if(!Email.contains("@")){
                    emailEdt.setError("Your email incorrect");
                }else if(passwordLenght < 6){
                    passwordEdt.setError("Must more 6 char");
                }else{
                    mAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Gagal daftar",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Berhasil mendaftar", Toast.LENGTH_SHORT).show();
                                        i = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                }
                break;
            default:
                break;
        }
    }
}