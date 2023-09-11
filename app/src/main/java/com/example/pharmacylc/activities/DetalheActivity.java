package com.example.pharmacylc.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pharmacylc.R;
import com.example.pharmacylc.models.NewProductsModel;
import com.example.pharmacylc.models.PopularProductsModel;
import com.example.pharmacylc.models.ShowAllModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetalheActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price;
    Button addtoCart, buyNow;
    ImageView addItems,removeItems;

    NewProductsModel newProductsModel = null;
    PopularProductsModel popularProductsModel = null;

    ShowAllModel showAllModel = null;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detalhes");

        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if(obj instanceof PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        }else if(obj instanceof ShowAllModel){
           showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detalhe_img);
        name = findViewById(R.id.detalhe_nome);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detalhe_desc);
        price = findViewById(R.id.detalhe_price);

        addtoCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);

        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);


        if(newProductsModel != null){

            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            name.setText(newProductsModel.getName());


        }
        if(popularProductsModel != null){

            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            name.setText(popularProductsModel.getName());
        }

        if(showAllModel != null){

            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());
        }
    }
}