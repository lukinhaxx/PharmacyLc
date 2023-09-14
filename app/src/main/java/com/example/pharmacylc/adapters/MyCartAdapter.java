package com.example.pharmacylc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pharmacylc.R;
import com.example.pharmacylc.activities.DetalheActivity;
import com.example.pharmacylc.models.MyCartModel;
import com.example.pharmacylc.models.PopularProductsModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {

    private Context context;
    private List<MyCartModel> list;

    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PopularProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularProductsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(popularProductsModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(popularProductsModelList.get(position).getName());
        holder.price.setText(String.valueOf(popularProductsModelList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetalheActivity.class);
                intent.putExtra("detalhes",popularProductsModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, date, time, totalQuantity, totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price;


        }
    }
}

