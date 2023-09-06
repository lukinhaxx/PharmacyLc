package com.example.pharmacylc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.pharmacylc.R;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root = inflater.inflate(R.layout.fragment_home, container, false);
         ImageSlider imageSlider = root.findViewById(R.id.image_slider);
         List<SlideModel> slideModels = new ArrayList <>();

         sliderModels.add(new SlideModel(R.drawable.banner1, "Discount Oh Shoes Items", ScaleTypes.CENTER_CROP));
         sliderModels.add(new SlideModel(R.drawable.banner2, "Discount Oh Perfume", ScaleTypes.CENTER_CROP));
         sliderModels.add(new SlideModel(R.drawable.banner3, "70% OFF", ScaleTypes.CENTER_CROP));

         imageSlider.setImageList(slideModels);

        return root;
    }
}