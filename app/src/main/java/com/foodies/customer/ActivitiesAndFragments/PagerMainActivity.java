package com.foodies.customer.ActivitiesAndFragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.foodies.customer.Utils.RelateToFragment_OnBack.OnBackPressListener;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.google.android.material.tabs.TabLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.foodies.customer.Adapters.AdapterPager;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.R;
import com.foodies.customer.Utils.CustomViewPager;
import com.foodies.customer.Utils.SwipeDirection;


public class PagerMainActivity extends RootFragment {
    public static TabLayout tabLayout;
    public static CustomViewPager viewPager;
    AdapterPager adapter;

    boolean mIsReceiverRegistered = false;
    MyBroadcastReceiver mReceiver = null;

    SharedPreferences sPref;
    int count;

    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_pager, container, false);
        context=getContext();

        sPref = getContext().getSharedPreferences(PreferenceClass.user,Context.MODE_PRIVATE);
        tabLayout = v.findViewById(R.id.tab_layout);

        viewPager = v.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(4);

        if (tabLayout != null) {

            adapter = new AdapterPager(getResources(), getChildFragmentManager());
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setAllowedSwipeDirection(SwipeDirection.none);
            setupTabIcons();
        }

        return v;

    }



    private void setupTabIcons() {
        View view1 = LayoutInflater.from(context).inflate(R.layout.item_menu_tablayout_item, null);
        ImageView imageView1= view1.findViewById(R.id.image);
        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_res_fill));
        TextView textView1=view1.findViewById(R.id.text);
        textView1.setText(R.string.restuarents);
        textView1.setTextColor(getResources().getColor(R.color.colorAccent));
        tabLayout.getTabAt(0).setCustomView(view1);

        View view2 = LayoutInflater.from(context).inflate(R.layout.item_menu_tablayout_item, null);
        ImageView imageView2= view2.findViewById(R.id.image);
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_order_not_fil));
        TextView textView=view2.findViewById(R.id.text);
        textView.setText(R.string.orders);
        textView.setTextColor(getResources().getColor(R.color.dark_gray));
        tabLayout.getTabAt(1).setCustomView(view2);


        View view3 = LayoutInflater.from(context).inflate(R.layout.item_menu_tablayout_item, null);
        ImageView imageView3= view3.findViewById(R.id.image);
        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_cart_not_fil));
        TextView textView3=view3.findViewById(R.id.text);
        textView3.setText(R.string.cart);
        textView3.setTextColor(getResources().getColor(R.color.dark_gray));
        tabLayout.getTabAt(2).setCustomView(view3);


        View view4 = LayoutInflater.from(context).inflate(R.layout.item_menu_tablayout_item, null);
        ImageView imageView4= view4.findViewById(R.id.image);
        imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_acc_not_fil));
        TextView textView4=view4.findViewById(R.id.text);
        textView4.setText(R.string.accounts);
        textView4.setTextColor(getResources().getColor(R.color.dark_gray));
        tabLayout.getTabAt(3).setCustomView(view4);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View v=tab.getCustomView();
                ImageView image=v.findViewById(R.id.image);
                switch (tab.getPosition()){
                    case 0:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_res_fill));
                        break;
                    case 1:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_order_fil));
                        break;
                    case 2:

                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_cart_fil));
                        break;
                    case 3:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_acc_fil));
                        break;
                }
                TextView tv =v.findViewById(R.id.text);
                tv.setTextColor(getResources().getColor(R.color.colorAccent));
                tab.setCustomView(v);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View v=tab.getCustomView();
                ImageView image=v.findViewById(R.id.image);
                switch (tab.getPosition()){
                    case 0:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_res_not_fil));
                        break;
                    case 1:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_order_not_fil));
                        break;
                    case 2:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_cart_not_fil));
                        break;
                    case 3:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_acc_not_fil));
                        break;
                }
                TextView tv =v.findViewById(R.id.text);
                tv.setTextColor(getResources().getColor(R.color.dark_gray));
                tab.setCustomView(v);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });



        int getIfCarExist = sPref.getInt(PreferenceClass.CART_COUNT,0);
        count = sPref.getInt("count",0);

        if(count==0){
            TabLayout.Tab tab = tabLayout.getTabAt(2); // fourth tab
            View tabView = tab.getCustomView();
            TextView badgeText = (TextView) tabView.findViewById(R.id.tab_badge);
            badgeText.setVisibility(View.GONE);
            badgeText.setText(""+count);
        }
        else
        if(getIfCarExist==1){

            TabLayout.Tab tab = tabLayout.getTabAt(2); // fourth tab
            View tabView = tab.getCustomView();
            TextView badgeText = (TextView) tabView.findViewById(R.id.tab_badge);
            badgeText.setVisibility(View.VISIBLE);
            badgeText.setText(""+count);
        }

    }



    void selectPage(int pageIndex) {
        viewPager.setCurrentItem(pageIndex);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsReceiverRegistered) {
            getActivity().unregisterReceiver(mReceiver);
            mReceiver = null;
            mIsReceiverRegistered = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsReceiverRegistered) {
            if (mReceiver == null)
                mReceiver = new MyBroadcastReceiver();
            getActivity().registerReceiver(mReceiver, new IntentFilter("AddToCart"));
            mIsReceiverRegistered = true;
        }


        if (CartFragment.ORDER_PLACED) {
            selectPage(1);
            CartFragment.ORDER_PLACED = false;
        }

    }


    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(viewPager.getCurrentItem());

        if (currentFragment != null) {
              return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }


    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //  updateUI(intent);
            count = sPref.getInt("count",0);
            TabLayout.Tab tab = tabLayout.getTabAt(2); // fourth tab
            View tabView = tab.getCustomView();
            TextView badgeText = (TextView) tabView.findViewById(R.id.tab_badge);
            if(CartFragment.FLAG_CLEAR_ORDER){
                badgeText.setVisibility(View.GONE);
                CartFragment.FLAG_CLEAR_ORDER=false;
            }
            else {
                badgeText.setVisibility(View.VISIBLE);
                badgeText.setText(""+count);
            }

            if(count==0){
                badgeText.setVisibility(View.GONE);
                badgeText.setText(""+count);
            }

        }

    }

}

