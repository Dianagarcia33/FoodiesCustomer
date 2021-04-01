package com.foodies.customer.ActivitiesAndFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foodies.customer.R;
import com.squareup.picasso.Picasso;

public class PagerFragments4 extends Fragment {
    ImageView imageView;
    private Button btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = (ViewGroup)inflater.inflate(R.layout.page_4,container,false);

        imageView = rootView.findViewById(R.id.imgpager1);
        Picasso.with(getContext()).load(R.drawable.sliderjpg4).into(imageView);

        return rootView;
    }
}
