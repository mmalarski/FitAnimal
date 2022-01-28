package com.example.fitanimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    boolean left;

    public int[] list_images ={
            R.drawable.room_l, R.drawable.room_r
    };

    public int[]  buttons={
           R.id.wardrobe, R.id.food, R.id.bed, R.id.toy, R.id.animal
    };

    public boolean[]  flags={
            true, false
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
        boolean isBowlEmpty = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE).getBoolean("isBowlEmpty", true);
        View view = inflater.inflate(R.layout.slide, container, false);
        ImageView imgslide = view.findViewById(R.id.slideimg);
        ImageButton bowl = view.findViewById(R.id.food);
        if (isBowlEmpty) {
            bowl.setImageResource(R.drawable.bowl_empty);
        } else {
            bowl.setImageResource(R.drawable.bowl);
        }
        imgslide.setImageResource(list_images[position]);

        Button resetButton=(Button)view.findViewById(buttons[0]);
        ImageButton resetImageButton=(ImageButton)view.findViewById(buttons[1]);
        ImageButton resetImageButton2=(ImageButton)view.findViewById(buttons[2]);
        ImageButton resetImageButton3=(ImageButton)view.findViewById(buttons[3]);
        ImageButton resetImageButton4=(ImageButton)view.findViewById(buttons[4]);

        if(position == 0) {
            resetButton.setVisibility(View.VISIBLE);
            resetImageButton.setVisibility(View.INVISIBLE);
            resetImageButton2.setVisibility(View.INVISIBLE);
            resetImageButton4.setVisibility(View.INVISIBLE);
            resetImageButton3.setVisibility(View.INVISIBLE);
        } else {
            resetButton.setVisibility(View.INVISIBLE);
            resetImageButton.setVisibility(View.VISIBLE);
            resetImageButton2.setVisibility(View.VISIBLE);
            resetImageButton4.setVisibility(View.VISIBLE);
            resetImageButton3.setVisibility(View.VISIBLE);

        }

        if(!flags[position])
        {
           //TextView tv=(TextView) view.findViewById(R.id.tv_stepsTaken);
            //tv.setVisibility(View.VISIBLE);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

}
