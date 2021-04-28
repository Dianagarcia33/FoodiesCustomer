package com.foodies.customer.ActivitiesAndFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodies.customer.Adapters.AdapterPager;
import com.foodies.customer.Adapters.CartFragExpandable;
import com.foodies.customer.Adapters.CartFragExpandablePack;
import com.foodies.customer.Adapters.DealsAdapter;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.AddressListModel;
import com.foodies.customer.Models.CartFragChildModel;
import com.foodies.customer.Models.CartFragParentModel;
import com.foodies.customer.Models.DealsModel;
import com.foodies.customer.Models.RestaurantChildModel;
import com.foodies.customer.Models.RestaurantsModel;
import com.foodies.customer.R;
import com.foodies.customer.Utils.CustomExpandableListView;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragmentPaquetes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragmentPaquetes extends Fragment implements View.OnClickListener{

    RelativeLayout accept_div,decline_div,cart_payment_method_div,cart_address_div,cart_address_divC,cart_datetime_div,tip_div,promo_code_div,cart_check_out_div;
    TextView decline_tv,accept_tv,tax_tv,credit_card_number_tv,delivery_address_tv,delivery_address_tvC,delivery_datetime_tv,rider_tip_price_tv,total_delivery_fee_tv,
            promo_tv,total_promo_tv,total_sum_tv,rider_tip,discount_tv,rest_name_tv,free_delivery_tv;
    CustomExpandableListView selected_item_list;
    SharedPreferences sPref;
    DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    private String udid,tax_dues,instructions,riderTip,tax_preference,fee_prefernce,total_sum,res_id,rest_name,user_id,mQuantity,
            coupan_code_,lat_origen,long_origen,lat_fin,long_fin;
    String grandTotal_ = "0";

    private String payment_id,card_number;
    private String street,apartment,city,state,address_id;

    CartFragExpandablePack cartFragExpandable;
    ArrayList<CartFragParentModel> listDataHeader;
    ArrayList<CartFragChildModel> listChildData;
    private ArrayList<ArrayList<CartFragChildModel>> ListChild;
    TextView sub_total_price_tv,total_tex_tv;
    String grandTotal,symbol;
    CamomileSpinner cartProgress;
    Button clear_btn;
    RelativeLayout no_cart_div;
    Collection<Object> values;
    Map<String, Object> td;
    HashMap<String,Object> values_final;
    ArrayList<HashMap<String,Object>> extraItemArray;
    private boolean FLAG_COUPON;
    boolean getLoINSession,PICK_UP;
    Double previousRiderTip = 0.0;

    ArrayList<DealsModel> delsArrayList;
    private boolean isViewShown = false;
    LinearLayout mainCartDiv;
    JSONArray jsonArrayMenuExtraItem;
    FrameLayout cart_main_container;
    public static boolean ORDER_PLACED,UPDATE_NODE;
    Location location = new Location("localizacion 1");
    LocationManager locationManager;

    Location location2 = new Location("localizacion 2");
    Boolean valLocation1 = false;
    Boolean valLocation2 = false;
    SharedPreferences dealsSharedPreferences;
    RelativeLayout transparent_layer,progressDialog;

    public static boolean FLAG_CLEAR_ORDER;
    String minimumOrderPrice;
    private static  String key;
    View view;
    Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragmentPaquetes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragmentPaquetes.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragmentPaquetes newInstance(String param1, String param2) {
        CartFragmentPaquetes fragment = new CartFragmentPaquetes();
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
        View view = inflater.inflate(R.layout.fragment_cart_paquetes, container, false);
        dealsSharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        context=getContext();

        if (!isViewShown) {
            initUI(view);
        }
        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getView() != null) {
            isViewShown = true;
            cart_main_container.invalidate();
            initUI(getView());
        } else {
            isViewShown = false;
        }

    }

    public void initUI(View view){
        sPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        udid = sPref.getString(PreferenceClass.UDID,"");//"9051e610ebcc1639";
        getLoINSession = sPref.getBoolean(PreferenceClass.IS_LOGIN,false);
        user_id = sPref.getString(PreferenceClass.pre_user_id,"");



        free_delivery_tv = view.findViewById(R.id.free_delivery_tv);
        progressDialog = view.findViewById(R.id.progressDialog);
        transparent_layer = view.findViewById(R.id.transparent_layer);

        delivery_address_tv = view.findViewById(R.id.delivery_address_tv);
        delivery_address_tvC = view.findViewById(R.id.delivery_address_tvC);
        cartProgress = view.findViewById(R.id.cartProgress);
        cartProgress.start();
        cart_main_container = view.findViewById(R.id.cart_main_container);
        cart_main_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


        cart_main_container.invalidate();
        credit_card_number_tv = view.findViewById(R.id.credit_card_number_tv);


        extraItemArray = new ArrayList<>();



        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference().child(AllConstants.PACKAGES).child(udid);
        riderTip = "0";

        no_cart_div = view.findViewById(R.id.no_cart_div);
        mainCartDiv = view.findViewById(R.id.mainCartDiv);
        promo_tv = view.findViewById(R.id.promo_tv);
        total_promo_tv = view.findViewById(R.id.total_promo_tv);


        cart_check_out_div = view.findViewById(R.id.cart_check_out_div);

        clear_btn = view.findViewById(R.id.clear_btn);
        rest_name_tv = view.findViewById(R.id.rest_name_tv);

        delivery_datetime_tv=view.findViewById(R.id.delivery_datetime_tv);

        discount_tv = view.findViewById(R.id.discount_tv);
        promo_code_div = view.findViewById(R.id.promo_code_div);
        rider_tip = view.findViewById(R.id.rider_tip);
        total_sum_tv = view.findViewById(R.id.total_sum_tv);

        total_delivery_fee_tv= view.findViewById(R.id.total_delivery_fee_tv);
        rider_tip_price_tv = view.findViewById(R.id.rider_tip_price_tv);

        tip_div = view.findViewById(R.id.tip_div);
        total_tex_tv = view.findViewById(R.id.total_tex_tv);
        tax_tv = view.findViewById(R.id.tax_tv);


        sub_total_price_tv = view.findViewById(R.id.sub_total_price_tv);
        decline_div = view.findViewById(R.id.decline_div);
        accept_div = view.findViewById(R.id.accept_div);
        decline_tv = view.findViewById(R.id.decline_tv);
        accept_tv = view.findViewById(R.id.accept_tv);
        selected_item_list = view.findViewById(R.id.selected_item_list);




        cart_payment_method_div = view.findViewById(R.id.cart_payment_method_div);
        cart_address_div = view.findViewById(R.id.cart_address_div);
        cart_address_divC = view.findViewById(R.id.cart_address_divC);

        cart_datetime_div=view.findViewById(R.id.cart_datetime_div);


        cart_check_out_div.setOnClickListener(this);
        clear_btn.setOnClickListener(this);
        tip_div.setOnClickListener(this);
        promo_code_div.setOnClickListener(this);
        cart_address_div.setOnClickListener(this);
        cart_address_divC.setOnClickListener(this);
        cart_datetime_div.setOnClickListener(this);
        cart_payment_method_div.setOnClickListener(this);





        selected_item_list.setExpanded(true);
        selected_item_list.setGroupIndicator(null);



        decline_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decline_div.setBackground(ContextCompat.getDrawable(context,R.drawable.round_shape_btn_login));
                accept_div.setBackground(ContextCompat.getDrawable(context,R.drawable.round_shape_btn_grey));
                decline_tv.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));
                accept_tv.setTextColor(ContextCompat.getColor(context,R.color.or_color_name));
                rider_tip_price_tv.setText(symbol+"0");
                total_delivery_fee_tv.setText(symbol+"0");
                rider_tip.setText(symbol+"0");
                delivery_address_tv.setText(getResources().getString(R.string.pick_up));
                cart_datetime_div.setVisibility(View.GONE);
                PICK_UP = true;

                Calculate_Price();

                cart_address_div.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });

                tip_div.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });

            }
        });

        accept_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decline_div.setBackground(ContextCompat.getDrawable(context,R.drawable.round_shape_btn_grey));
                accept_div.setBackground(ContextCompat.getDrawable(context,R.drawable.round_shape_btn_login));
                decline_tv.setTextColor(ContextCompat.getColor(context,R.color.or_color_name));
                accept_tv.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));
                rider_tip_price_tv.setText(symbol+riderTip);
                total_delivery_fee_tv.setText(symbol+fee_prefernce);
                rider_tip.setText(symbol+riderTip);
                if(street==null&&apartment==null&&city==null&&state==null){
                    delivery_address_tv.setText(getResources().getString(R.string.select_delivery_address));
                }
                else {
                    delivery_address_tv.setText(street + " " + apartment + " " + city + " " + state);
                }
                PICK_UP = false;

                previousRiderTip = 0.0;

                cart_datetime_div.setVisibility(View.VISIBLE);

                Calculate_Price();

                cart_address_div.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });

                tip_div.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tip_div:
                addRiderTip();
                break;


            case R.id.promo_code_div:
                varifyCoupan();
                break;


            case R.id.cart_address_div:
                if(!getLoINSession){

                    Fragment userFragment = new UserAccountFragment() ;
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    fragmentTransaction.add(R.id.contenedorFragment,userFragment).commit();
                    fragmentTransaction.addToBackStack(null);

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
//                                credit_card_number_tv.setTextColor(ContextCompat.getColor(context,R.color.black));

                                delivery_address_tv.setText(street + " " + city + " " + state);
//                                delivery_address_tv.setTextColor(ContextCompat.getColor(context,R.color.black));

                                fee_prefernce=addressListModel.getDelivery_fee();
                                total_delivery_fee_tv.setText(symbol+fee_prefernce);


                                location.setLatitude(addressListModel.getLatCity());  //latitud
                                location.setLongitude(addressListModel.getLongCity()); //longitud

                                valLocation1 = true;


                                calculateTax(city);
                            }
                        }
                    });

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle bundle=new Bundle();
                    bundle.putString("grand_total",grandTotal);
                    bundle.putString("rest_id",res_id);
                    restaurantMenuItemsFragment.setArguments(bundle);
                    transaction.addToBackStack(null);
                    transaction.add(R.id.cart_main_container, restaurantMenuItemsFragment, "parent").commit();

                    /*
                    *
                    *   FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                        Bundle bundle=new Bundle();
                        bundle.putString("validacionP", String.valueOf(finalValidacionPq));
                        bundle.putString("referencias",referencias.getText().toString());
                        bundle.putString("insAdicionales",insAdicionales.getText().toString());
                        transaction.addToBackStack(null);
                        transaction.add(R.id.contenedorFragment, fragmentCart, "parent").commit();
                    * */

                }
                break;

            case R.id.cart_address_divC:
                if(!getLoINSession){

                    Fragment userFragment = new UserAccountFragment() ;
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    fragmentTransaction.add(R.id.contenedorFragment,userFragment).commit();
                    fragmentTransaction.addToBackStack(null);

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

//                                credit_card_number_tv.setTextColor(ContextCompat.getColor(context,R.color.black));

                                delivery_address_tvC.setText(street + " " + city + " " + state);
//                                delivery_address_tv.setTextColor(ContextCompat.getColor(context,R.color.black));

                                fee_prefernce=addressListModel.getDelivery_fee();
                                total_delivery_fee_tv.setText(symbol+fee_prefernce);


                                location2.setLatitude(addressListModel.getLatCity());  //latitud
                                location2.setLongitude(addressListModel.getLongCity()); //longitud

                                //   Calculate_Price();
                                valLocation2 = true;

                                calculateTax(city);
                            }
                        }
                    });

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle bundle=new Bundle();
                    bundle.putString("grand_total",grandTotal);
                    bundle.putString("rest_id",res_id);
                    restaurantMenuItemsFragment.setArguments(bundle);
                    transaction.addToBackStack(null);
                    transaction.add(R.id.cart_main_container, restaurantMenuItemsFragment, "parent").commit();

                    /*
                    *
                    *   FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                        Bundle bundle=new Bundle();
                        bundle.putString("validacionP", String.valueOf(finalValidacionPq));
                        bundle.putString("referencias",referencias.getText().toString());
                        bundle.putString("insAdicionales",insAdicionales.getText().toString());
                        transaction.addToBackStack(null);
                        transaction.add(R.id.contenedorFragment, fragmentCart, "parent").commit();
                    * */

                }
                break;

            case R.id.cart_datetime_div:
                Select_DateTime_F select_dateTime_f = new Select_DateTime_F(new Fragment_Callback() {
                    @Override
                    public void Responce(Bundle bundle) {
                        if(bundle!=null){
                            delivery_datetime_tv.setText(bundle.getString("datetime"));
                        }
                    }
                });
                FragmentTransaction dtransaction = getChildFragmentManager().beginTransaction();
                dtransaction.addToBackStack(null);
                dtransaction.add(R.id.contenedorFragment, select_dateTime_f, "parent").commit();
                break;

            case R.id.cart_payment_method_div:
                if(!getLoINSession){

                    Fragment userFragment = new UserAccountFragment() ;
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    fragmentTransaction.add(R.id.contenedorFragment,userFragment).commit();
                    fragmentTransaction.addToBackStack(null);


                }
                else {
                    Fragment restaurantMenuItemsFragment = new AddPaymentFragment(new Fragment_Callback() {
                        @Override
                        public void Responce(Bundle bundle) {
                            if(bundle!=null){

                                payment_id=bundle.getString("card_id");
                                card_number=bundle.getString("card_number");
                                credit_card_number_tv.setText(card_number);
                                credit_card_number_tv.setTextColor(ContextCompat.getColor(context,R.color.black));
                            }
                        }
                    });
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.add(R.id.contenedorFragment, restaurantMenuItemsFragment, "parent").commit();
                }
                break;


            case R.id.cart_check_out_div:
                if(!getLoINSession){


                    Fragment userFragment = new UserAccountFragment() ;
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    fragmentTransaction.add(R.id.contenedorFragment,userFragment).commit();
                    fragmentTransaction.addToBackStack(null);


                }
                else {
                        placeOrder();
                }
                break;


            case R.id.clear_btn:
                showDialogCartDelete();
                break;


        }
    }

    public void calculateTax(String cityParam){

        if (valLocation1){
            if (valLocation2) {
                if (cityParam != null) {
                    float distance = location.distanceTo(location2);
                    Log.d("distancia", String.valueOf(distance));


                    prueba(distance,cityParam);
                }
            }
        }



    }

    public void showDialogCartDelete(){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
        } else {
            builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
        }
        builder.setTitle(R.string.delete_cart)
                .setMessage(R.string.are_you_sure_to_delete_cart)
                .setPositiveButton(R.string.discard, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        mDatabase.setValue(null);
                        no_cart_div.setVisibility(View.VISIBLE);
                        mainCartDiv.setVisibility(View.GONE);
                        SharedPreferences.Editor editor = sPref.edit();
                        editor.putInt(PreferenceClass.CART_COUNT,0);
                        editor.putInt("count",0)
                                .commit();
                        Intent intent = new Intent();
                        intent.setAction("AddToCart");
                        getContext().sendBroadcast(intent);

                        AdapterPager a = (AdapterPager) PagerMainActivity.viewPager.getAdapter();
                        a.destroyItem(PagerMainActivity.viewPager, 2,a.instantiateItem(PagerMainActivity.viewPager, 2));
                        CartFragment f = (CartFragment) a.instantiateItem(PagerMainActivity.viewPager, 2);


                        FLAG_CLEAR_ORDER = true;


                        dialog.dismiss();

                    }


                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }


public void prueba(Float distanceR,String cityS){

        //mQuantity

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
    String currentDateandTime = sdf.format(new Date());
    delsArrayList = new ArrayList<>();
    String lat = dealsSharedPreferences.getString(PreferenceClass.LATITUDE,"");
    String long_ = dealsSharedPreferences.getString(PreferenceClass.LONGITUDE,"");


    JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lat", lat);
            jsonObject.put("long", long_);
            jsonObject.put("current_time",currentDateandTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.Call_Api(context, Config.showCountries, jsonObject, new Callback() {
        @Override
        public void Responce(String resp) {

            try {
                JSONObject jsonResponse = new JSONObject(resp);

                int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                if(code_id == 200) {

                    JSONObject json = new JSONObject(jsonResponse.toString());
                    JSONArray jsonarray = json.getJSONArray("cities");
                    String taxPackage = null;

                    for (int i = 0; i < jsonarray.length(); i++) {


                        JSONObject json1 = jsonarray.getJSONObject(i);
                        JSONObject jsonObjTax = jsonarray.getJSONObject(i).optJSONObject("Tax");

                        Log.d("cityS", cityS.toString());
                        Log.d("cityjson", jsonObjTax.getString("city").toString());


                        if(cityS == jsonObjTax.getString("city").toString()) {
                            if (jsonObjTax != null) {

                                if (mQuantity.matches("1")) {
                                    taxPackage = jsonObjTax.optString("tax_pack_s");
                                } else if (mQuantity.matches("2")) {
                                    taxPackage = jsonObjTax.optString("tax_pack_m");
                                } else if (mQuantity.matches("3")) {
                                    taxPackage = jsonObjTax.optString("tax_pack_b");
                                }

                               // int dato = Integer.parseInt(String.valueOf(distanceR))*Integer.parseInt(taxPackage);

                                Log.d("taxDato", String.valueOf(distanceR));
                                Log.d("taxPackage", String.valueOf(taxPackage));



                                 DecimalFormat formater = new DecimalFormat("0.00");

                                 String vdistacia = formater.format(distanceR);

                                String distacia = String.format("%.2f", Integer.parseInt(vdistacia) / 10000000);

                                double subTotal = Double.parseDouble(distacia) * Double.parseDouble(taxPackage);

                               sub_total_price_tv.setText(String.valueOf(subTotal));


                            }
                        }

                    }



                }

            } catch (JSONException e) {
                e.printStackTrace();

            }

            transparent_layer.setVisibility(View.GONE);
            progressDialog.setVisibility(View.GONE);
            TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);


        }
    });
}

    public void getCartData(){
        mDatabase.keepSynced(true);
        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);
        listDataHeader = new ArrayList<>();
        ListChild = new ArrayList<>();
        DatabaseReference query = mDatabase;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    Log.d(AllConstants.tag, dataSnapshot.toString());

                    td = (HashMap<String, Object>) dataSnapshot.getValue();
                    if (td != null) {

                        values = td.values();
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(values);
                            grandTotal_ = "0";
                            for (int a = 0; a < jsonArray.length(); a++) {

                                JSONObject allJsonObject = jsonArray.getJSONObject(a);

                                CartFragParentModel cartFragParentModel = new CartFragParentModel();

                                cartFragParentModel.setItem_name(allJsonObject.optString("mName"));
                                cartFragParentModel.setItem_price(allJsonObject.optString("mPrice"));
                                mQuantity = allJsonObject.optString("mQuantity");
                                cartFragParentModel.setItem_quantity(allJsonObject.optString("mQuantity"));
                                cartFragParentModel.setItem_symbol(allJsonObject.optString("mCurrency"));
                                cartFragParentModel.setItem_key(allJsonObject.optString("key"));
                                cartFragParentModel.setItem_description(allJsonObject.optString("mDesc"));

                                String total = allJsonObject.optString("grandTotal");
                                minimumOrderPrice = allJsonObject.optString("minimumOrderPrice");
                                symbol = allJsonObject.optString("mCurrency");

                                res_id = allJsonObject.optString("restID");
                                rest_name = allJsonObject.optString("rest_name");
                                rest_name_tv.setText(rest_name);


                                if (total.isEmpty() || total.equalsIgnoreCase("null")) {

                                    total = "0";
                                }

                                getDescText(minimumOrderPrice, total);

                                grandTotal = "" + (Double.parseDouble(total) + Double.parseDouble(grandTotal_));

                                grandTotal_ = grandTotal;

                                tax_preference = allJsonObject.optString("mTax");
                                instructions = allJsonObject.optString("instruction");


                                listDataHeader.add(cartFragParentModel);
                                listChildData = new ArrayList<>();

                                if (!allJsonObject.has("extraItem")) {
                                    TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout, true);
                                    ListChild.add(listChildData);
                                } else {
                                    JSONArray extraItemArray = allJsonObject.getJSONArray("extraItem");

                                    for (int b = 0; b < extraItemArray.length(); b++) {

                                        JSONObject jsonObject = extraItemArray.getJSONObject(b);

                                        CartFragChildModel cartFragChildModel = new CartFragChildModel();

                                        cartFragChildModel.setQuantity(allJsonObject.optString("mQuantity"));
                                        cartFragChildModel.setSymbol(allJsonObject.optString("mCurrency"));

                                        listChildData.add(cartFragChildModel);
                                    }
                                    ListChild.add(listChildData);

                                }
                            }
                            if (listDataHeader != null && listDataHeader.size() > 0) {

                                (getView().findViewById(R.id.no_cart_div)).setVisibility(View.GONE);
                                (getView().findViewById(R.id.mainCartDiv)).setVisibility(View.VISIBLE);

                                sub_total_price_tv.setText(symbol + grandTotal);

                                if (!tax_preference.isEmpty()) {
                                    tax_tv.setText("(" + tax_preference + "%)");
                                } else {
                                    tax_preference = String.valueOf(0);
                                    tax_tv.setText("(0%)");
                                }


                                if (fee_prefernce != null) {
                                    if (fee_prefernce.isEmpty()) {
                                        fee_prefernce = String.valueOf(0);
                                    }
                                }
                                if (fee_prefernce == null) {
                                    fee_prefernce = "0";
                                }


                                if (grandTotal.isEmpty()) {
                                    grandTotal = "0.0";
                                }

                                if (delivery_address_tv.getText().toString().equalsIgnoreCase("Select Delivery Address")) {
                                    fee_prefernce = "" + 0.0;
                                }
                                total_delivery_fee_tv.setText(symbol + fee_prefernce);


                                rider_tip_price_tv.setText(symbol + "0.0");

                                total_promo_tv.setText(symbol + "0.0");
                                total_sum_tv.setText(total_sum);

                                Calculate_Price();

                                cartFragExpandable = new CartFragExpandablePack(getContext(), listDataHeader, ListChild);
                                selected_item_list.setAdapter(cartFragExpandable);

                                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout, true);
                                int itemCount = cartFragExpandable.getGroupCount();


                                for (int i = 0; i < cartFragExpandable.getGroupCount(); i++)
                                    try {

                                        selected_item_list.expandGroup(i);
                                    } catch (IndexOutOfBoundsException e) {
                                        e.getCause();
                                    }

                                selected_item_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                    @Override
                                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                                        CartFragParentModel item = (CartFragParentModel) listDataHeader.get(groupPosition);
                                        key  = item.getItem_key();
                                        customDialogbox();
                                        return true;
                                    }
                                });

                            } else {
                                (getView().findViewById(R.id.no_cart_div)).setVisibility(View.VISIBLE);
                                (getView().findViewById(R.id.mainCartDiv)).setVisibility(View.GONE);
                            }

                            transparent_layer.setVisibility(View.GONE);
                            progressDialog.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            No_data_found();
                        }
                    } else {
                        No_data_found();
                    }
                }
                else {
                    No_data_found();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                No_data_found();
            }
        });

    }


    public void No_data_found(){


        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
        transparent_layer.setVisibility(View.GONE);
        progressDialog.setVisibility(View.GONE);
        (getView().findViewById(R.id.no_cart_div)).setVisibility(View.VISIBLE);
        (getView().findViewById(R.id.mainCartDiv)).setVisibility(View.GONE);

    }

    private void getDescText(String minimumOrderPrice, String grandTotal){

        try {
            Double var3 = Double.parseDouble(minimumOrderPrice) - Double.parseDouble(grandTotal);

            if (var3 >= Double.parseDouble(minimumOrderPrice)) {

                free_delivery_tv.setText(R.string.you_have_reached_your_free_delivery_order);



            } else {
                if (String.valueOf(var3).contains("-")) {
                    free_delivery_tv.setText(R.string.you_have_reached_your_free_delivery_order);

                } else {
                    free_delivery_tv.setText(getString(R.string.you_have_to_need_more) + symbol + var3 + getString(R.string.for_free_delivery_order));
                }
            }
        }catch (Exception e){

        }

    }


    public void addRiderTip(){

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_cart);

        final EditText ed_text = dialog.findViewById(R.id.ed_text);
        ed_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        TextView title = dialog.findViewById(R.id.title);
        title.setText(getContext().getResources().getString(R.string.add_rider_tip));
        ed_text.setHint(R.string.enter_tip_here);

        Button cancelDiv = (Button) dialog.findViewById(R.id.cancel_btn);
        Button done_btn =  (Button) dialog.findViewById(R.id.done_btn);

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(ed_text.getText().toString())){

                    riderTip = ed_text.getText().toString();
                    PICK_UP = false;
                    rider_tip_price_tv.setText(symbol+riderTip);
                    rider_tip.setText(symbol+riderTip);

                    Calculate_Price();

                    dialog.dismiss();
                }else {
                    Toast.makeText(getContext(), R.string.please_enter_the_ammount, Toast.LENGTH_SHORT).show();
                }
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

    public void varifyCoupan(){

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_cart);


        final EditText ed_text = dialog.findViewById(R.id.ed_text);

        Button cancelDiv = (Button) dialog.findViewById(R.id.cancel_btn);
        Button done_btn = (Button) dialog.findViewById(R.id.done_btn);

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(ed_text.getText().toString())) {

                    coupan_code_ = ed_text.getText().toString();
                    getCoupanRequest(coupan_code_);

                    TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout, false);
                    transparent_layer.setVisibility(View.VISIBLE);
                    progressDialog.setVisibility(View.VISIBLE);
                    dialog.dismiss();

                }else {
                    Toast.makeText(getContext(), R.string.please_enter_the_promocode, Toast.LENGTH_SHORT).show();
                }
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

    String discount="0";
    Double discount_amount=0.0;
    String coupon_id="0";
    public void getCoupanRequest(String coupan_code){


        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("coupon_code",coupan_code);
            jsonObject.put("restaurant_id",res_id);
            jsonObject.put("user_id",user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        ApiRequest.Call_Api(context, Config.VERIFY_COUPAN, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject1 = new JSONObject(resp);

                    int code = Integer.parseInt(jsonObject1.optString("code"));
                    if(FLAG_COUPON){
                        Toast.makeText(getContext(), R.string.coupon_already_been_added,Toast.LENGTH_SHORT).show();
                        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);

                    }
                    else {
                        if (code == 200) {
                            FLAG_COUPON = true;
                            JSONArray jsonArray = jsonObject1.getJSONArray("msg");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                JSONObject jsonObject3 = jsonObject2.getJSONObject("RestaurantCoupon");
                                discount = jsonObject3.optString("discount");
                                coupon_id=jsonObject3.optString("id");
                                promo_tv.setText("("+discount+"%)");

                                Calculate_Price();
                            }

                        } else {
                            Toast.makeText(getContext(), resp.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



    }



    public void Calculate_Price(){

        total_sum=grandTotal_;


        if(!PICK_UP){
            total_sum = ""+(Double.parseDouble(total_sum) + Double.parseDouble(fee_prefernce));

            total_sum = ""+(Double.parseDouble(total_sum) + Double.parseDouble(riderTip));

        }


        float tax= (float) (Double.parseDouble(grandTotal) * Double.parseDouble(tax_preference) / 100);

        tax_dues = get_roundoff_double(""+tax);
        total_sum = ""+(Double.valueOf(Double.parseDouble(total_sum) + Double.parseDouble(tax_dues)));
        total_tex_tv.setText(symbol + tax_dues);



        float dis= (float) ((Double.parseDouble(discount)*Double.parseDouble(grandTotal_))/100);
        discount_amount = Double.valueOf(get_roundoff_double(""+dis));

        discount_tv.setText(symbol+" "+discount_amount+" ("+discount+"%)");
        total_promo_tv.setText(symbol+" "+discount_amount);

        total_sum = ""+(Double.parseDouble(total_sum)-discount_amount);


        total_sum= get_roundoff_double(total_sum);
        total_sum_tv.setText(symbol+get_roundoff_double(total_sum));


    }


    public String get_roundoff_double(String value){

        if(!value.contains(".")){
            return value;
        }
        else
        {
            int position = value.indexOf(".");
            return value.substring(0,position+2);
        }
    }


    public void placeOrder(){

        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        JSONArray menu_item=null;
        JSONArray valueArray = new JSONArray(values);
        for (int i=0;i<valueArray.length();i++){

            JSONObject jsonObject1 = null;
            try {
                jsonObject1 = valueArray.getJSONObject(i);
                values_final= new HashMap<>();

                if(jsonObject1.optString("extraItem")!=null&& !jsonObject1.optString("extraItem").isEmpty()) {
                    jsonArrayMenuExtraItem = new JSONArray(jsonObject1.optString("extraItem"));
                    values_final.put("menu_extra_item",jsonArrayMenuExtraItem);
                    String size = String.valueOf(jsonArrayMenuExtraItem.length());
                }
                else {
                    values_final.put("menu_extra_item",new JSONArray("["+"]"));
                }


                values_final.put("menu_item_price", jsonObject1.optString("mPrice"));
                values_final.put("menu_item_quantity", jsonObject1.optString("mQuantity"));
                values_final.put("menu_item_name", jsonObject1.optString("mName"));


                extraItemArray.add(values_final);

            } catch (JSONException e) {
                e.printStackTrace();
                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

            }

        }

        menu_item =new JSONArray(extraItemArray);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id",user_id);
            jsonObject.put("price",total_sum);
            jsonObject.put("sub_total",69);
            jsonObject.put("tax",tax_dues);
            jsonObject.put("quantity",mQuantity);
            if(delivery_address_tv.getText().toString().equalsIgnoreCase(getResources().getString(R.string.pick_up)))
            {
                jsonObject.put("address_id", "");
            }else {
                jsonObject.put("address_id", address_id);
            }
            jsonObject.put("restaurant_id",1);
            jsonObject.put("instructions",instructions);

            jsonObject.put("coupon_id",coupon_id);
            jsonObject.put("discount",discount_amount);

            jsonObject.put("order_time",formattedDate);
            jsonObject.put("delivery_fee",fee_prefernce);
            jsonObject.put("version",SplashScreen.VERSION_CODE);

            if(delivery_address_tv.getText().toString().equalsIgnoreCase(getResources().getString(R.string.pick_up))) {
                jsonObject.put("delivery","0");
            }
            else {
                jsonObject.put("delivery","1");
            }


            if(delivery_address_tv.getText().toString().equalsIgnoreCase(getResources().getString(R.string.pick_up)))
            {
                jsonObject.put("delivery_date_time","");
            }
            else if(delivery_datetime_tv.getText().toString().equalsIgnoreCase(getResources().getString(R.string.select_delivery_time))){
                jsonObject.put("delivery_date_time","");
            }
            else {
                jsonObject.put("delivery_date_time",delivery_datetime_tv.getText());
            }


            if(rider_tip.getText().toString().equalsIgnoreCase(getResources().getString(R.string.add_rider_tip))){
                jsonObject.put("rider_tip","0");
            }
            else {
                String riderTip_ = riderTip;
                jsonObject.put("rider_tip",riderTip_ );
            }

            jsonObject.put("device","android");

            jsonObject.put("cod","1");
            jsonObject.put("payment_id","0");



            jsonObject.put("menu_item",menu_item);


            List<String> language_names_for_api=  Arrays.asList(getResources().getStringArray(R.array.language_names_for_api));
            List <String> language_code=  Arrays.asList(getResources().getStringArray(R.array.language_code));

            String language= Locale.getDefault().getLanguage();
            if(sPref.getString(PreferenceClass.selected_language,null)!=null)
                language = sPref.getString(PreferenceClass.selected_language, language_code.get(0));

            if(language_code.contains(language)){
                jsonObject.put("lang",language_names_for_api.get(language_code.indexOf(language)));
            }else {
                jsonObject.put("lang","english");
            }



        } catch (JSONException e) {
            e.printStackTrace();

        }

        Log.d(AllConstants.tag,jsonObject.toString());

      /*  Gson userGson=new GsonBuilder().create();
        String jsonstring=userGson.toJson(jsonObject.toString());
        */

        Log.d(AllConstants.tag,jsonObject.toString());


        ApiRequest.Call_Api(context, Config.addOrderSession, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {
                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

                try {
                    JSONObject  responce = new JSONObject(resp);
                    int code_id  = Integer.parseInt(responce.optString("code"));
                    if(code_id == 200) {
                        JSONObject msg=responce.optJSONObject("msg");
                        if(msg!=null){
                            JSONObject OrderSession=msg.optJSONObject("OrderSession");
                            String id=OrderSession.optString("id");
                            Open_Checkout_Fragment(id);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Log.d(AllConstants.tag,Config.payment_link);


    }


    public void Open_Checkout_Fragment(String id){
        Checkout_F checkout_f = new Checkout_F(new Fragment_Callback() {
            @Override
            public void Responce(Bundle bundle) {

                mDatabase.setValue(null);
                Intent intent = new Intent();
                intent.setAction("AddToCart");
                getContext().sendBroadcast(intent);

                SharedPreferences.Editor editor = sPref.edit();
                editor.putInt(PreferenceClass.CART_COUNT,0);
                editor.putInt("count",0)
                        .commit();
                ORDER_PLACED = true;

                FLAG_CLEAR_ORDER = true;
                OrderDetailFragment.CALLBACK_ORDERFRAG = true;

                getCartData();

                startActivity(new Intent(getContext(),MainActivity.class));
                getActivity().finish();

            }
        });
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("url",Config.payment_link+id);
        checkout_f.setArguments(bundle);

        transaction.addToBackStack(null);
        transaction.add(R.id.contenedorFragment, checkout_f).commit();
    }

    @SuppressLint("SetTextI18n")
    public void customDialogbox(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialoge_box);


        RelativeLayout cancelDiv = (RelativeLayout) dialog.findViewById(R.id.forth);
        RelativeLayout currentOrderDiv = (RelativeLayout) dialog.findViewById(R.id.second);
        RelativeLayout pastOrderDiv = (RelativeLayout) dialog.findViewById(R.id.third);
        TextView second_tv = (TextView)dialog.findViewById(R.id.second_tv);
        TextView third_tv = (TextView)dialog.findViewById(R.id.third_tv);
        second_tv.setText(R.string.delete);
        second_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        third_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFB));

        currentOrderDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UPDATE_NODE = true;

                dialog.dismiss();

            }
        });

        pastOrderDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteSelectedNode(key);
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

    @Override
    public void onResume() {
        super.onResume();
        if(AddressListFragment.CART_NOT_LOAD ){
            AddressListFragment.CART_NOT_LOAD = false;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getCartData();
            }
        },1000);


    }

    public void deleteSelectedNode(final String key){

        final DatabaseReference deleteNode = mDatabase.child(key);

        deleteNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("key").getValue(String.class);


                if(name.equalsIgnoreCase(key)){
                    deleteNode.setValue(null);
                    getCartData();

                    int getCartCount = sPref.getInt("count",0);

                    SharedPreferences.Editor editor = sPref.edit();
                    editor.putInt("count",getCartCount-1).commit();
                    getActivity().sendBroadcast(new Intent("AddToCart"));

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void editNode(){

        final DatabaseReference deleteNode = mDatabase.child(key);

        deleteNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("key").getValue(String.class);

                if(name.equalsIgnoreCase(key)){



                    Intent intent = new Intent(getContext(),AddToCartActivity.class);


                    RestaurantsModel rest_model=new RestaurantsModel();
                    RestaurantChildModel rest_child_model=new RestaurantChildModel();

                    rest_model.restaurant_id=dataSnapshot.child("restID").getValue(String.class);
                    rest_model.restaurant_name=dataSnapshot.child("rest_name").getValue(String.class);
                    rest_model.restaurant_tax=dataSnapshot.child("mTax").getValue(String.class);
                    rest_model.min_order_price=dataSnapshot.child("minimumOrderPrice").getValue(String.class);


                    rest_child_model.restaurant_menu_item_id=dataSnapshot.child("mID").getValue(String.class);
                    rest_child_model.child_title=dataSnapshot.child("mName").getValue(String.class);
                    rest_child_model.child_sub_title=dataSnapshot.child("mDesc").getValue(String.class);
                    rest_child_model.price=dataSnapshot.child("mPrice").getValue(String.class);
                    rest_child_model.currency_symbol=dataSnapshot.child("mCurrency").getValue(String.class);


                    intent.putExtra("rest_model",rest_model);
                    intent.putExtra("rest_child_model",rest_child_model);
                    intent.putExtra("key",key);


                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}