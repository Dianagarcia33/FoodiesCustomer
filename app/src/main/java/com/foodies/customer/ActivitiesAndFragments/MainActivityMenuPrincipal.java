package com.foodies.customer.ActivitiesAndFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodies.customer.Adapters.AdapterPager;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.R;
import com.foodies.customer.Utils.CustomViewPager;
import com.foodies.customer.Utils.SwipeDirection;
import com.google.android.material.tabs.TabLayout;

public class MainActivityMenuPrincipal extends AppCompatActivity {

    Fragment fragmentInicioPrincipal,fragmentAdondeloLlevamos,fragmentQvacomprar,userAccountFragment,dealDetailFragment;
    FragmentTransaction fragmentTransaction;
    Context context;
    AdapterPager adapter;
    public static TabLayout tabLayout;
    public static CustomViewPager viewPager;
    boolean mIsReceiverRegistered = false;

    SharedPreferences sPref;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_principal);
        context=getApplicationContext();
        fragmentInicioPrincipal = new FragmentInicioMenuPrincipal();
        fragmentAdondeloLlevamos = new FragmentAdondeloLlevamos();
        fragmentQvacomprar = new FragmentMquevacomprar();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment,fragmentInicioPrincipal).commit();
        sPref = getApplicationContext().getSharedPreferences(PreferenceClass.user,Context.MODE_PRIVATE);
        tabLayout = findViewById(R.id.tab_layout);

        viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);

        if (tabLayout != null) {

            adapter = new AdapterPager(getResources(), getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setAllowedSwipeDirection(SwipeDirection.none);
            setupTabIcons();
        }



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




        View view4 = LayoutInflater.from(context).inflate(R.layout.item_menu_tablayout_item, null);
        ImageView imageView4= view4.findViewById(R.id.image);
        imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_acc_not_fil));
        TextView textView4=view4.findViewById(R.id.text);
        textView4.setText(R.string.accounts);
        textView4.setTextColor(getResources().getColor(R.color.dark_gray));
        tabLayout.getTabAt(2).setCustomView(view4);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View v=tab.getCustomView();
                ImageView image=v.findViewById(R.id.image);
                switch (tab.getPosition()){
                    case 0:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_order_fil));
                        fragmentInicioPrincipal = new FragmentInicioMenuPrincipal();
                        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment,fragmentInicioPrincipal).commit();
                        break;

                    case 1:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_order_fil));
                        userAccountFragment = new UserAccountFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment,userAccountFragment).commit();
                        break;
                    case 2:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_acc_not_fil));
                        userAccountFragment = new UserAccountFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment,userAccountFragment).commit();
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
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_res_fill));
                        break;
                    case 1:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_order_not_fil));
                        break;
                    case 2:
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


    }

    void selectPage(int pageIndex) {
        viewPager.setCurrentItem(pageIndex);

    }
}