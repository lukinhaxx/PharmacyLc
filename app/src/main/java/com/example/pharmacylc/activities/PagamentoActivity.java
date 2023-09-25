package com.example.pharmacylc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pharmacylc.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class PagamentoActivity extends AppCompatActivity {
    double amount = 0.0;
    Toolbar toolbar;
    TextView subTotal, discount, shipping, total;
    Button paymentBtn;

    String clientId = "AeHeCb6-iAd5tJDCJ5Vq3JVTDtyaTuqddlxV7fqzJCR5HKdVi_duTheKpAX3b74WhqUIiO9gW7lQrX3M";
    int PAYPAL_REQUEST_CODE = 123;

    public static PayPalConfiguration configuration;
    public static final String EXTRA_PAYMENT = "extra_payment";
    public static final String EXTRA_RESULT_CONFIRMATION = "com.paypal.android.sdk.paymentConfirmation";
    public static final int RESULT_EXTRAS_INVALID = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount", 0.0);

        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);
        paymentBtn = findViewById(R.id.pay_btn);

        subTotal.setText("R$" + amount);



        configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpayment();
            }
        });

    }

    private void getpayment() {



        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "BRL", "Code with Arvind", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(intent);

        // Inicie a atividade de pagamento
        Intent paymentIntent = new Intent(this, PaymentActivity.class);
        paymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(paymentIntent, PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (paymentConfirmation != null) {
                try {
                    String paymentDetails = paymentConfirmation.toJSONObject().toString();
                    JSONObject object = new JSONObject(paymentDetails);
                    // Faça algo com os detalhes do pagamento JSON, se necessário
                } catch (JSONException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Pagamento cancelado pelo usuário", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Detalhes de pagamento inválidos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}