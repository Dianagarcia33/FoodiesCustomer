package com.foodies.customer.ActivitiesAndFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foodies.customer.R;
import com.squareup.picasso.Picasso;

public class PagerFragments2 extends Fragment {
ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = (ViewGroup)inflater.inflate(R.layout.page_2,container,false);

        imageView = rootView.findViewById(R.id.imgpager2);
        Picasso.with(getContext()).load(R.drawable.slider2).into(imageView);


        return rootView;
    }
}
