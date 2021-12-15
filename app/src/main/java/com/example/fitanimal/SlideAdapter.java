package com.example.fitanimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public int[] list_images ={
        R.drawable.obraz2, R.drawable.obraz1
    };

    public SlideAdapter (Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
       View view = inflater.inflate(R.layout.slide, container, false);
       LinearLayout layoutslide = view.findViewById(R.id.slideLinearlayout);
       ImageView imgslide = (ImageView) view.findViewById(R.id.slideimg);
       imgslide.setImageResource(list_images[position]);
       container.addView(view);
       return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((LinearLayout) object);
    }

}
