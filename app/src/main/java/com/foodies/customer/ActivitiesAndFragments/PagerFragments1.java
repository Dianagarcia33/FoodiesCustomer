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

public class PagerFragments1 extends Fragment {
    ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = (ViewGroup)inflater.inflate(R.layout.page_1,container,false);

        imageView = rootView.findViewById(R.id.imgpager1);
        Picasso.with(getContext()).load(R.drawable.sliderjpg1).into(imageView);

        return rootView;
    }
}
