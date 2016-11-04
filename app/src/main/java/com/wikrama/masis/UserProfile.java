package com.wikrama.masis;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfile extends AppCompatActivity {

    private TextView nameTxt, univTxt, jobTxt, emailTxt;
    private FirebaseUser user;
    private SharedPreferences pref;
    private SharedPreferences.Editor mEdit;
    private String Name, Univ, Job, Email;
    private Button editProfile, changeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();

        nameTxt = (TextView) findViewById(R.id.nameTxt);
        univTxt = (TextView) findViewById(R.id.univTxt);
        jobTxt = (TextView) findViewById(R.id.jobTxt);
        emailTxt = (TextView) findViewById(R.id.emailTxt);
        editProfile = (Button) findViewById(R.id.editProfile);

        pref = getSharedPreferences("MASIS", MODE_PRIVATE);
        mEdit = pref.edit();

        Name = pref.getString("userName", null);
        Job = pref.getString("userJob", null);
        Univ = pref.getString("userUniv", null);
        Email = user.getEmail();

        nameTxt.setText(Name + "");
        jobTxt.setText(Job + "");
        univTxt.setText(Univ + "");
        emailTxt.setText(Email + "");

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfil cdd = new EditProfil(UserProfile.this);
                cdd.show();
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