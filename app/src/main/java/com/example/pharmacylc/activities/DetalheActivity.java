package com.example.pharmacylc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pharmacylc.R;
import com.example.pharmacylc.models.NewProductsModel;
import com.example.pharmacylc.models.PopularProductsModel;
import com.example.pharmacylc.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetalheActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button addtoCart, buyNow;
    ImageView addItems,removeItems;

    int totalQuantiy = 1;
    int totalPrice = 0;

    NewProductsModel newProductsModel = null;
    PopularProductsModel popularProductsModel = null;

    ShowAllModel showAllModel = null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detalhes");

        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if(obj instanceof PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        }else if(obj instanceof ShowAllModel){
           showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detalhe_img);
        quantity = findViewById(R.id.quantity);

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

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantiy < 10){
                    totalQuantiy++;
                    quantity.setText(String.valueOf(totalQuantiy));
                }

            }
        });
        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantiy > 1){
                    totalQuantiy--;
                    quantity.setText(String.valueOf(totalQuantiy));
                }
            }
        });


        addtoCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addtoCart();


            }

            private void addtoCart() {

                String saveCurrentTime, saveCurrentDate;
                Calendar calForDate = Calendar.getInstance();

                @SuppressLint({"NewApi", "LocalSuppress"}) SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, YYYY");
                saveCurrentDate = currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productName", name.getText().toString());
                cartMap.put("productPrice", price.getText().toString());
                cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("currentDate", saveCurrentDate);

                firestore.collection("addToCart").document(auth.getCurrentUser().getUid())
                        .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(DetalheActivity.this, "Adicionado no carrinho", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
            }
        });
    }
}