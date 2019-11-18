package com.example.bartertrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;
import java.net.URLDecoder;

public class Post extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ImageView prdImage;
    EditText prdName, prdDesc;
    String category;
    Spinner spn;
    Button post;
    DatabaseReference dbRef;
    EditText email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        prdImage = findViewById(R.id.iv_post_prdImage);
        prdName = findViewById(R.id.et_post_prdName);
        prdDesc = findViewById(R.id.et_post_prdDesc);
        post = findViewById(R.id.btn_post_post);
        spn = findViewById(R.id.sp_post_catList);

        email = findViewById(R.id.et_reg_em);
        password = findViewById(R.id.et_reg_pw);


        dbRef = FirebaseDatabase.getInstance().getReference("product");
       ;

        prdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = "post";
                Intent i = new Intent(Post.this, Upload.class);
                i.putExtra("class", p);
                startActivity(i);

            }
        });

        Intent i = getIntent();
        final String pkey = i.getStringExtra("PKEY");
        final String url = i.getStringExtra("URL");
        if (url != null) {
            Picasso.get().load(url).fit().into(prdImage);
        }

        spn = findViewById(R.id.sp_post_catList);
        ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(spAdapter);
        spn.setOnItemSelectedListener(this);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("Select Category".equals(category))
                {
                    Toast.makeText(Post.this, "Please Select a Category!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());

                }

                Product product = new Product(prdName.getText().toString(), prdDesc.getText().toString(), category, url);
                dbRef.child(dbRef.push().getKey()).setValue(product);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
