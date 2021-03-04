package com.foodies.customer.ActivitiesAndFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodies.customer.Adapters.AdapterRecyclerProductos;
import com.foodies.customer.Adapters.CartFragExpandable;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.AddressListModel;
import com.foodies.customer.Models.CartFragChildModel;
import com.foodies.customer.Models.CartFragParentModel;
import com.foodies.customer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.foodies.customer.ActivitiesAndFragments.CartFragment.UPDATE_NODE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragmentProductos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragmentProductos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView decline_tv,accept_tv,tax_tv,credit_card_number_tv,delivery_address_tv,delivery_datetime_tv,rider_tip_price_tv,total_delivery_fee_tv,
            promo_tv,total_promo_tv,total_sum_tv,rider_tip,discount_tv,rest_name_tv,free_delivery_tv;


    AdapterRecyclerProductos cartFragExpandable;

    private String street,apartment,city,state,address_id;

    FragmentTransaction fragmentTransaction;
    RelativeLayout addresDiv;

    private String udid,user_id,grandTotal,res_id_origen,res_id;


    ArrayList<CartFragChildModel> listChildData;

    ArrayList<CartFragParentModel> listDataHeader;
    DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    private static  String key;
    Collection<Object> values;
    Map<String, Object> td;
    int grandTotal_ = 0;
    SharedPreferences sPref;
    boolean getLoINSession,PICK_UP;
    RecyclerView selected_item_list;
    Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragmentProductos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartaFragmentProductos.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragmentProductos newInstance(String param1, String param2) {
        CartFragmentProductos fragment = new CartFragmentProductos();
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
        View view = inflater.inflate(R.layout.fragment_cart_productos, container, false);


        sPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        udid = sPref.getString(PreferenceClass.UDID,"");//"9051e610ebcc1639";
        getLoINSession = sPref.getBoolean(PreferenceClass.IS_LOGIN,false);
        user_id = sPref.getString(PreferenceClass.pre_user_id,"");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference().child(AllConstants.PACKAGES).child(udid);
        selected_item_list = view.findViewById(R.id.selected_item_list_c);
        addresDiv = view.findViewById(R.id.cart_address_divP);
        delivery_address_tv = view.findViewById(R.id.delivery_address_tvP);
        mDatabase.keepSynced(true);
        loadProductosCart();

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

                                Log.d(AllConstants.tag, addressListModel.getAddress_id());

                                delivery_address_tv.setText(street + " " + city + " " + state);

                                grandTotal = "0";
                                res_id_origen = addressListModel.getAddress_id().toString();

                            }
                        }
                    });

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle bundle=new Bundle();
                    bundle.putString("grand_total",grandTotal);
                    bundle.putString("rest_id",res_id_origen);
                    restaurantMenuItemsFragment.setArguments(bundle);
                    transaction.addToBackStack(null);
                    transaction.add(R.id.cart_main_containerP, restaurantMenuItemsFragment, "parent").commit();

                }
            }
        });



        return view;

    }

    public void loadProductosCart(){
        listChildData = new ArrayList<>();


        DatabaseReference query = mDatabase;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    Log.d(AllConstants.tag, dataSnapshot.toString());

                    td = (HashMap<String, Object>) dataSnapshot.getValue();

                    if (td != null) {
                        values = td.values();
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(values);
                            grandTotal_= 0;
                            listDataHeader = new ArrayList<>();
                            for (int a = 0; a < jsonArray.length(); a++) {

                                JSONObject allJsonObject = jsonArray.getJSONObject(a);

                                Log.d(AllConstants.tag, allJsonObject.getString("mName"));


                                CartFragParentModel cartFragParentModel = new CartFragParentModel();

                                cartFragParentModel.setItem_key(allJsonObject.optString("key"));
                                cartFragParentModel.setItem_name(allJsonObject.optString("mName"));
                                cartFragParentModel.setItem_description(allJsonObject.optString("description"));

                                grandTotal_ += Integer.parseInt(allJsonObject.optString("mPriceT"));



                                listDataHeader.add(cartFragParentModel);


                            }

                            if (listDataHeader != null && listDataHeader.size() > 0) {

                                Log.d(AllConstants.tag, listDataHeader.toString());


                                cartFragExpandable = new AdapterRecyclerProductos(listDataHeader, getContext(), new AdapterRecyclerProductos.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(CartFragParentModel item) {
                                        key  = item.getItem_key();
                                        customDialogbox();
                                    }
                                });


                                selected_item_list.setHasFixedSize(true);
                                selected_item_list.setLayoutManager(new LinearLayoutManager(getContext()));
                                selected_item_list.setAdapter(cartFragExpandable);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void customDialogbox(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialoge_box);


        RelativeLayout cancelDiv = (RelativeLayout) dialog.findViewById(R.id.forth);
        RelativeLayout currentOrderDiv = (RelativeLayout) dialog.findViewById(R.id.second);
        RelativeLayout pastOrderDiv = (RelativeLayout) dialog.findViewById(R.id.third);
        TextView first_tv = (TextView)dialog.findViewById(R.id.first_tv);
        TextView second_tv = (TextView)dialog.findViewById(R.id.second_tv);
        TextView third_tv = (TextView)dialog.findViewById(R.id.third_tv);

        first_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFB));
        second_tv.setText(R.string.delete);
        second_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        third_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFB));



        pastOrderDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteSelectedNode(key);
                loadProductosCart();
                dialog.dismiss();

            }
        });


        cancelDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void deleteSelectedNode(final String key){

        final DatabaseReference deleteNode = mDatabase.child(key);
        Log.d(AllConstants.tag, deleteNode.toString());

        Log.d(AllConstants.tag, key.toString());

        deleteNode.removeValue();



    }
}