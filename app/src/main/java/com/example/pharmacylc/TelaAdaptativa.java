package com.example.pharmacylc;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class TelaAdaptativa extends PagerAdapter {

    Context context;

    LayoutInflater layoutInflater;
    public TelaAdaptativa(Context context) {
        this.context = context;
    }

    int imagesArray [] ={
      R.drawable.onboardscreen1,
      R.drawable.onboardscreen2,
      R.drawable.onboardscreen3
    };

    int headingArray [] ={
      R.string.first_slide,
      R.string.second_slide,
      R.string.third_slide
    };

    int descriptionArray [] ={
            R.string.description,
            R.string.description,
            R.string.description
    };


    @Override
    public int getCount() {
        return headingArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object ;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_deslizante,container,false);

        return super.instantiateItem(container, position);
    }
}

