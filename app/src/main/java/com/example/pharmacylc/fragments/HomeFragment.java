package com.example.pharmacylc.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacylc.R;
import com.example.pharmacylc.activities.ShowAllActivity;
import com.example.pharmacylc.adapters.CategoryAdapter;
import com.example.pharmacylc.adapters.NewProductsAdapter;
import com.example.pharmacylc.adapters.PopularProductsAdapter;
import com.example.pharmacylc.adapters.ShowAllAdapter;
import com.example.pharmacylc.models.CategoryModel;
import com.example.pharmacylc.models.NewProductsModel;
import com.example.pharmacylc.models.PopularProductsModel;
import com.example.pharmacylc.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView catShowAll, popularShowAll, newProductShowAll;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerview, newProductRecyclerview, popularRecyclerview;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    FirebaseFirestore db;

    EditText search_box;
    private List<ShowAllModel> showAllModelList;
    private RecyclerView recyclerViewSearch;
    private ShowAllAdapter showAllAdapter;
    private BinarySearchTree searchTree;

    class TreeNode {
        String name;
        ShowAllModel data;
        TreeNode left;
        TreeNode right;

        public TreeNode(String name, ShowAllModel data) {
            this.name = name;
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    class BinarySearchTree {
        private TreeNode root;

        public BinarySearchTree() {
            root = null;
        }

        public void insert(String name, ShowAllModel data) {
            root = insertRec(root, name, data);
        }

        private TreeNode insertRec(TreeNode root, String name, ShowAllModel data) {
            if (root == null) {
                root = new TreeNode(name, data);
                return root;
            }

            if (name.compareTo(root.name) < 0) {
                root.left = insertRec(root.left, name, data);
            } else if (name.compareTo(root.name) > 0) {
                root.right = insertRec(root.right, name, data);
            }

            return root;
        }

        public ShowAllModel search(String name) {
            return searchRec(root, name);
        }

        private ShowAllModel searchRec(TreeNode root, String name) {
            if (root == null || root.name.equals(name)) {
                return (root != null) ? root.data : null;
            }

            if (name.compareTo(root.name) < 0) {
                return searchRec(root.left, name);
            }

            return searchRec(root.right, name);
        }
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint({"NotifyDataSetChanged", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog = new ProgressDialog(getActivity());
        catRecyclerview = root.findViewById(R.id.rec_category);
        newProductRecyclerview = root.findViewById(R.id.new_product_rec);
        popularRecyclerview = root.findViewById(R.id.popular_rec);

        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);

        catShowAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });

        newProductShowAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });

        popularShowAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });

        db = FirebaseFirestore.getInstance();


        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);





        progressDialog.setTitle(getString(R.string.bem_vindo_a_minha_farm_cia));
        progressDialog.setMessage(getString(R.string.aguarde_por_favor));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CategoryModel categoryModel = document.toObject(CategoryModel.class);
                            categoryModelList.add(categoryModel);
                            categoryAdapter.notifyDataSetChanged();
                            linearLayout.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();

                        }
                    } else {
                        Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        newProductRecyclerview.setAdapter(newProductsAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                            newProductsModelList.add(newProductsModel);
                            newProductsAdapter.notifyDataSetChanged();

                            Log.d("Firestore", "Nome do produto: " + newProductsModel.getName());
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

        popularRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getActivity(), popularProductsModelList);
        popularRecyclerview.setAdapter(popularProductsAdapter);

        db.collection("Allproducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                            popularProductsModelList.add(popularProductsModel);
                            popularProductsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

        recyclerViewSearch = root.findViewById(R.id.search_rec);
        search_box = root.findViewById(R.id.search_box);
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(getContext(), showAllModelList);

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(showAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);

       search_box.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    showAllModelList.clear();
                    showAllAdapter.notifyDataSetChanged();
                }else{
                    searchProduct(s.toString());

                }
           }
       });

        return root;
    }

    private void searchProduct(String name) {
        if (!name.isEmpty()) {
            db.collection("VerTodos")
                    .whereEqualTo("name", name)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                showAllModelList.clear();
                                showAllAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    });
        } else {
            showAllModelList.clear();
            showAllAdapter.notifyDataSetChanged();
        }

    }
    private void sortShowAllModels(List<ShowAllModel> list) {
        mergeSort(list);
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



