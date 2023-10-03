package com.example.pharmacylc.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacylc.R;
import com.example.pharmacylc.adapters.ShowAllAdapter;
import com.example.pharmacylc.models.ShowAllModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModelList;

    Toolbar toolbar;
    FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        toolbar = findViewById(R.id.show_all_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> finish());

        String type = getIntent().getStringExtra("type");

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModelList);
        recyclerView.setAdapter(showAllAdapter);

        if (type == null || type.isEmpty()) {
            firestore.collection("VerTodos")
                    .orderBy("name")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showAllModelList.clear();
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                            }

                            mergeSort(showAllModelList);

                            showAllAdapter.notifyDataSetChanged();
                        }
                    });
        } else {
            firestore.collection("VerTodos")
                    .whereEqualTo("type", type)
                    .orderBy("name")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showAllModelList.clear();
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                            }


                            mergeSort(showAllModelList);

                            showAllAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }

    private void mergeSort(List<ShowAllModel> list) {
        if (list.size() <= 1) {
            return;
        }

        int middle = list.size() / 2;
        List<ShowAllModel> left = list.subList(0, middle);
        List<ShowAllModel> right = list.subList(middle, list.size());

        mergeSort(left);
        mergeSort(right);

        merge(list, left, right);
    }

    private void merge(List<ShowAllModel> list, List<ShowAllModel> left, List<ShowAllModel> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getName().compareTo(right.get(j).getName()) < 0) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            list.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            list.set(k++, right.get(j++));
        }
    }
}
