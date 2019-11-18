package com.example.bartertrader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    private TextView welcome;
    private Button profile;
    private ImageButton gadgets, clothes, bicycles, tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcome = findViewById(R.id.tv_dash_welcome);
        profile = findViewById(R.id.btn_dash_profile);
        gadgets = findViewById(R.id.ibtn_dash_gadgets);
        clothes = findViewById(R.id.ibtn_dash_clothes);
        bicycles = findViewById(R.id.ibtn_dash_bicycles);
        tools = findViewById(R.id.ibtn_dash_tools);




    }
}
