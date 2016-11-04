package com.wikrama.masis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wikrama.masis.POJO.Mading;

public class AddMading extends AppCompatActivity {

    private EditText judul,desc,image;
    private Button tambahMading;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Mading mading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mading);
        getSupportActionBar().setTitle("Buat Mading");

        databaseReference = firebaseDatabase.getInstance().getReference("Mading");

        judul = (EditText)findViewById(R.id.judul);
        desc = (EditText)findViewById(R.id.desc);
        image = (EditText)findViewById(R.id.gambar);
        tambahMading = (Button)findViewById(R.id.tambah);
        mading = new Mading();

        tambahMading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mading.setJudulMading(judul.getText().toString().trim());
                mading.setDeskripsi(desc.getText().toString().trim());
                mading.setGambarMading("contoh");
                //Masih Statis
                mading.setUser("ichsan");
                mading.setEmail("ichsannuur66@gmail.com");
                mading.setRole("Dosen");
                mading.setUniv("Univ Eresha");
                //
                databaseReference.push().setValue(mading);
                Toast.makeText(AddMading.this, "Mading Ditambah", Toast.LENGTH_SHORT).show();

                judul.setText("");
                desc.setText("");
                image.setText("");
                Intent i = new Intent(AddMading.this,HomeActivity.class);
                startActivity(i);
            }
        });

    }
}
