package com.example.bartertrader;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ProductList extends AppCompatActivity implements ProductAdapter.Holder.PrdInterface {

    RecyclerView rv;
    RecyclerView.LayoutManager mng;
    ProductAdapter adp;
    Query dbRef;
    ArrayList<Product> list = new ArrayList<>();


    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        rv = findViewById(R.id.rv_product_list_prd);
        Intent i =getIntent();
        String category = i.getStringExtra("Category");
        dbRef = FirebaseDatabase.getInstance().getReference("product").orderByChild("Category").equalTo(category);

        mng = new LinearLayoutManager(ProductList.this);
        rv.setLayoutManager(mng);
        dbRef.addListenerForSingleValueEvent(listener);

    }
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot dSS: dataSnapshot.getChildren())
            {
                Product prd = dSS.getValue(Product.class);
                list.add(prd);

            }

            adp = new ProductAdapter(list, ProductList.this);
            rv.setAdapter(adp);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onPrdClick(int position) {

        Intent i = new Intent(ProductList.this, ProductDetail.class);
        i.putExtra("Product", list.get(position));
        startActivity(i);
    }
}
