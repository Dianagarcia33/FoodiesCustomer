package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.foodies.customer.Adapters.CartFragExpandable;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.CartFragChildModel;
import com.foodies.customer.Models.CartFragParentModel;
import com.foodies.customer.R;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentInicioMenuPrincipal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInicioMenuPrincipal extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Fragment fragmentInicioPrincipal,fragmentAdondeloLlevamos,fragmentQvacomprar,fragmentPaquetes,fragCarPaquetes;
    FragmentTransaction fragmentTransaction;
    LinearLayout btnAdondeloLlevamos;
    LinearLayout btnQueComprar;
    LinearLayout btnMenu;
    DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    SharedPreferences sPref;
    boolean getLoINSession,PICK_UP;

    Map<String, Object> td;
    RelativeLayout transparent_layer,progressDialog;
    String getCurrentLocationAddress,udid,user_id;



    SharedPreferences sharedPreferences;
    double latitude, longitude;
    private Button welcome_show_restaurants_btn;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSION_DATA_ACCESS_CODE = 2;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;


    public FragmentInicioMenuPrincipal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInicioMenuPrincipal.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInicioMenuPrincipal newInstance(String param1, String param2) {
        FragmentInicioMenuPrincipal fragment = new FragmentInicioMenuPrincipal();
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
        View view = inflater.inflate(R.layout.fragment_inicio_menu_principal, container, false);

        btnAdondeloLlevamos = view.findViewById(R.id.btnMenu1);

        btnQueComprar = view.findViewById(R.id.btnMenu2);

        btnMenu = view.findViewById(R.id.btnMenu3);

        sPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        udid = sPref.getString(PreferenceClass.UDID,"");//"9051e610ebcc1639";
        getLoINSession = sPref.getBoolean(PreferenceClass.IS_LOGIN,false);
        user_id = sPref.getString(PreferenceClass.pre_user_id,"");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference().child(AllConstants.PACKAGES).child(udid);

        btnAdondeloLlevamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragmentAdondeloLlevamos = new FragmentAdondeloLlevamos();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.contenedorFragment,fragmentAdondeloLlevamos).commit();
                fragmentTransaction.addToBackStack(null);


            }
        });


        btnQueComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.keepSynced(true);

                DatabaseReference query = mDatabase;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {

                            Log.d(AllConstants.tag, dataSnapshot.toString());

                            td = (HashMap<String, Object>) dataSnapshot.getValue();
                            Log.d("td", td.toString());

                            if (td != null) {
                                fragCarPaquetes = new CartFragmentPaquetes();
                                fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.contenedorFragment,fragCarPaquetes).commit();
                                fragmentTransaction.addToBackStack(null);
                            } else {
                                fragmentPaquetes = new FragmentPaquetes();
                                fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.contenedorFragment,fragmentPaquetes).commit();
                                fragmentTransaction.addToBackStack(null);
                            }
                        }
                        else {
                            fragmentPaquetes = new FragmentPaquetes();
                            fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorFragment,fragmentPaquetes).commit();
                            fragmentTransaction.addToBackStack(null);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        fragmentPaquetes = new FragmentPaquetes();
                        fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contenedorFragment,fragmentPaquetes).commit();
                        fragmentTransaction.addToBackStack(null);
                    }
                });


            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);

            }
        });


        return view;
    }
}