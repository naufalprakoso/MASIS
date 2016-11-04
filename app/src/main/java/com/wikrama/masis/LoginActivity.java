package com.wikrama.masis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private EditText emailEdt, passwordEdt;
    private TextView registerTxt, forgotTxt;
    private String Email, Password;
    private Button loginBtn;
    private FirebaseAuth mAuth;
    private int passwordLenght;
    private static final int RC_SIGN_IN = 9001;
    private SharedPreferences pref;
    private GoogleApiClient mGoogleApiClient;
    private SharedPreferences.Editor mEdit;
    private Intent i;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        pref = getSharedPreferences("MASIS", MODE_PRIVATE);
        mEdit = pref.edit();

        registerTxt = (TextView) findViewById(R.id.registerTxt);
        forgotTxt = (TextView) findViewById(R.id.forgotTxt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        findViewById(R.id.googleBtn).setOnClickListener(this);
        emailEdt = (EditText) findViewById(R.id.emailEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);

        loginBtn.setOnClickListener(this);
        forgotTxt.setOnClickListener(this);
        registerTxt.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.loginBtn:
                Email = emailEdt.getText().toString().trim();
                Password = passwordEdt.getText().toString().trim();
                passwordLenght = passwordEdt.getText().length();
                if (Email.equals("") || Password.equals("")){
                    Toast.makeText(getApplicationContext(), "Can't Empty", Toast.LENGTH_SHORT).show();
                }else if(!Email.contains("@")){
                    emailEdt.setError("Your email incorrect");
                }else if(passwordLenght < 6){
                    passwordEdt.setError("Must more 6 char");
                }else{
                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.show();
                    mAuth.signInWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                    }else{
                                        mEdit.putString("isLogged", "1");
                                        mEdit.commit();
                                        mProgressDialog.dismiss();
                                        i = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                    });
                }
                break;
            case R.id.googleBtn:
                signIn();
                break;
            case R.id.registerTxt:
                i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                break;
            case R.id.forgotTxt:
                break;
            default:
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(this, "Login with google account failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login failed",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            mEdit.putString("isLogged", "1");
                            mEdit.commit();
                            i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}