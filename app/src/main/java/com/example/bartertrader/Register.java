package com.example.bartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Register extends AppCompatActivity {

    private EditText email, firstName, surname, phone, password;
    private Button register;
    private ImageView avatar;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.et_reg_em);
        firstName = findViewById(R.id.et_reg_first);
        surname = findViewById(R.id.et_reg_sur);
        phone = findViewById(R.id.et_reg_ph);
        password = findViewById(R.id.et_reg_pw);
        register = findViewById(R.id.btn_reg_reg);
        avatar = findViewById(R.id.iv_reg_avatar);

        dbref = FirebaseDatabase.getInstance().getReference("user");

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Register.this, Upload.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        final String pkey = i.getStringExtra("PKEY");
        final String url = i.getStringExtra("URL");
        if (url != null) {
            Picasso.get().load(url).fit().into(avatar);
        }

        register.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {



                                User p = new User(email.getText().toString(), firstName.getText().toString(), surname.getText().toString(),
                        phone.getText().toString(), password.getText().toString(), url);
                dbref.child(dbref.push().getKey()).setValue(p);

            }



        });


    }
}
