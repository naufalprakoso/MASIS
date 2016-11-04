package com.wikrama.masis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Naufal Prakoso on 11/5/2016.
 */

public class CustomFirstHomeDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button save;
    public EditText userEdt, jobEdt, univEdt;
    public SharedPreferences pref;
    public SharedPreferences.Editor mEdit;
    public String User, Job, Univ;

    public CustomFirstHomeDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_home_dialog);

        pref = c.getSharedPreferences("MASIS", Context.MODE_PRIVATE);
        mEdit = pref.edit();

        save = (Button) findViewById(R.id.saveBtn);
        userEdt = (EditText) findViewById(R.id.userEdt);
        jobEdt = (EditText) findViewById(R.id.jobEdt);
        univEdt = (EditText) findViewById(R.id.univEdt);

        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                User = userEdt.getText().toString();
                Job = jobEdt.getText().toString();
                Univ = univEdt.getText().toString();
                if (User.trim().equals("") || Job.trim().equals("") || Univ.trim().equals("")){
                    Toast.makeText(c, "Can't empty", Toast.LENGTH_SHORT).show();
                }else{
                    mEdit.putString("userName", User);
                    mEdit.putString("userJob", Job);
                    mEdit.putString("userUniv", Univ);
                    mEdit.commit();
                    Intent i = new Intent(c, HomeActivity.class);
                    c.startActivity(i);
                    c.finish();
                }
                break;
            default:
                break;
        }
        dismiss();
    }
}