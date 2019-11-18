package com.example.bartertrader;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.logging.Handler;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder>
    {
        ArrayList<Product> list;
        Holder.PrdInterface listener;

        public ProductAdapter(ArrayList<Product> list, Holder.PrdInterface listener2) {
            this.list = list;
            listener = listener2;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
            Holder holder = new Holder(v, listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {

            holder.tv.setText(list.get(position).getPrdName());
            Picasso.get().load(list.get(position).getUrl()).fit().into(holder.iv);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            ImageView iv;
            TextView tv;
            PrdInterface listener;

            public Holder(@NonNull View itemView, PrdInterface listener2) {
                super(itemView);
                iv = itemView.findViewById(R.id.iv_product_prdImg);
                tv = itemView.findViewById(R.id.tv_product_prdName);
                listener = listener2;
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {

                listener.onPrdClick(getAdapterPosition());
            }

            public interface PrdInterface
            {
                public void onPrdClick (int position);
            }
        }

    }