package com.example.bartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    TextView email, password;
     String dbEmail, dbPassword;
     String uEmail, uPassword;
     Button login;
     Button reg;
     Button fpw;
     Query dbref;

     ArrayList<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_login_em);
        password = findViewById(R.id.et_login_pw);
        login = findViewById(R.id.btn_login_lg);
        reg = findViewById(R.id.btn_login_reg);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                uEmail = email.getText().toString();
                uPassword = password.getText().toString();

                dbref = FirebaseDatabase.getInstance().getReference("user")
                        .orderByChild("email")
                        .equalTo(uEmail);
                dbref.addListenerForSingleValueEvent(listener);
                }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for(DataSnapshot dSS: dataSnapshot.getChildren())
            {
                final String uPKey = dSS.getKey();
                dbref = FirebaseDatabase.getInstance().getReference("user")
                        .orderByChild(uPKey)
                        .equalTo(uPassword);
                User user = dSS.getValue(User.class);
                list.add(user);
            }

            try {
                dbEmail = list.get(0).getEmail();
                dbPassword = list.get(0).getPassword();
                Toast.makeText(Login.this, "Login Successful!!!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Login.this, Dashboard.class));
            } catch (Exception e) {
                Log.e("", "java.lang.IndexOutOfBoundsException: " + e.toString());
                Toast.makeText(Login.this, "User does not exist!!! Please Register to proceed", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
