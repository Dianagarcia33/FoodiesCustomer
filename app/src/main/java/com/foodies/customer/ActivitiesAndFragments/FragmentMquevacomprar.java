package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodies.customer.Adapters.CartFragExpandable;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.AddressListModel;
import com.foodies.customer.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMquevacomprar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMquevacomprar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout buttonQueVacomprar;

    SharedPreferences sPref;
    boolean getLoINSession,PICK_UP;

    Fragment fragmentInicioPrincipal,fragmentAdondeloLlevamos,fragmentQvacomprar,fragmentCart;
    FragmentTransaction fragmentTransaction;
    RelativeLayout addresDiv;

    private String udid,user_id,grandTotal,res_id;

    Context context;

    TextView decline_tv,accept_tv,tax_tv,credit_card_number_tv,delivery_address_tv,delivery_datetime_tv,rider_tip_price_tv,total_delivery_fee_tv,
            promo_tv,total_promo_tv,total_sum_tv,rider_tip,discount_tv,rest_name_tv,free_delivery_tv;

    CartFragExpandable cartFragExpandable;

    private String street,apartment,city,state,address_id;

    public FragmentMquevacomprar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMquevaacomprar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMquevacomprar newInstance(String param1, String param2) {
        FragmentMquevacomprar fragment = new FragmentMquevacomprar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mquevaacomprar, container, false);

        buttonQueVacomprar = view.findViewById(R.id.btnQueVacomprar);
        addresDiv = view.findViewById(R.id.cart_address_divP);
        delivery_address_tv = view.findViewById(R.id.delivery_address_tvP);



        buttonQueVacomprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCart = new CartFragmentProductos();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedorFragment,fragmentCart).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        addresDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
                udid = sPref.getString(PreferenceClass.UDID,"");//"9051e610ebcc1639";
                getLoINSession = sPref.getBoolean(PreferenceClass.IS_LOGIN,false);
                user_id = sPref.getString(PreferenceClass.pre_user_id,"");

                if(!getLoINSession){
                    Fragment UserFragment = new UserAccountFragment();
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.contenedorFragment,UserFragment).commit();
                    fragmentTransaction.addToBackStack(null);
/*
                    TabLayout.Tab tab =PagerMainActivity.tabLayout.getTabAt(3);
                    tab.select();*/

                }
                else {
                    Fragment restaurantMenuItemsFragment = new AddressListFragment(new Fragment_Callback() {
                        @Override
                        public void Responce(Bundle bundle) {
                            if(bundle!=null){
                                AddressListModel addressListModel=(AddressListModel)bundle.getSerializable("data");

                                street =addressListModel.getStreet();
                                city =addressListModel.getCity();
                                state =addressListModel.getState();
                                apartment =addressListModel.getApartment();
                                address_id=addressListModel.getAddress_id();

                                Toast.makeText(getContext(),street + " " + city + " " + state,Toast.LENGTH_LONG).show();

                                delivery_address_tv.setText(street + " " + city + " " + state);

                                grandTotal = "0";
                                res_id = "0";

                            }
                        }
                    });

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle bundle=new Bundle();
                    bundle.putString("grand_total",grandTotal);
                    bundle.putString("rest_id",res_id);
                    restaurantMenuItemsFragment.setArguments(bundle);
                    transaction.addToBackStack(null);
                    transaction.add(R.id.cart_main_containerP, restaurantMenuItemsFragment, "parent").commit();

                }
            }
        });

        return view;
    }
}