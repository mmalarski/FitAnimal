package com.example.fitanimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    boolean left;

    public int[] list_images ={
            R.drawable.left, R.drawable.right
    };

    public int[]  buttons={
           R.id.wardrobe, R.id.food
    };

    public SlideAdapter (Context context) {
        this.context = context;
        //left = true;
    }

    @Override
    public int getCount() {
        return list_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (ConstraintLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide, container, false);
        ImageView imgslide = view.findViewById(R.id.slideimg);
        imgslide.setImageResource(list_images[position]);
        Button resetButton=(Button)view.findViewById(buttons[position]);
        resetButton.setVisibility(View.VISIBLE);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

}
