package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.GpsUtils;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.GoogleMapWork.MapsActivity;
import com.foodies.customer.Models.CalculationModel;
import com.foodies.customer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.LOCATION_SERVICE;
import static com.foodies.customer.ActivitiesAndFragments.CartFragment.UPDATE_NODE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPaquetes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPaquetes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Fragment fragmentInicioPrincipal,fragmentAdondeloLlevamos,fragmentQvacomprar,fragmentCart;
    FragmentTransaction fragmentTransaction;
    RadioButton btnpq1,btnpq2,btnpq3;
    private  String userId,key_,name_,desc,price_,symbol,res_id,res_name,res_tax,res_fee;

    private static  String key;
    private String udid,user_id;
    SharedPreferences sPref;
    FirebaseDatabase firebaseDatabase;
    EditText referencias,insAdicionales;
    public static boolean FLAG_CART_ADD;


    DatabaseReference mDatabase;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout btnPaquetes;
    private EditText textSeleccionarTxt1;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSION_DATA_ACCESS_CODE = 2;
    public FragmentPaquetes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPaquetes.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPaquetes newInstance(String param1, String param2) {
        FragmentPaquetes fragment = new FragmentPaquetes();
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
        View view = inflater.inflate(R.layout.fragment_paquetes, container, false);

        btnpq1 = view.findViewById(R.id.btnpq1);
        btnpq2 = view.findViewById(R.id.btnpq2);
        btnpq3 = view.findViewById(R.id.btnpq3);

        sPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        udid = sPref.getString(PreferenceClass.UDID,"");//"9051e610ebcc1639";
        user_id = sPref.getString(PreferenceClass.pre_user_id,"");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference().child(AllConstants.PACKAGES).child(udid);

        referencias = view.findViewById(R.id.inputReferencias);
        insAdicionales = view.findViewById(R.id.inputInsAdicionales);

        btnPaquetes = view.findViewById(R.id.btnPaquetes);

        btnPaquetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int validacionPq = 0;

                if (btnpq1.isChecked()){
                     validacionPq = 1;
                }else if (btnpq2.isChecked()){
                    validacionPq = 2;
                }else if (btnpq3.isChecked()){
                    validacionPq = 3;
                }

                mDatabase = firebaseDatabase.getReference().child(AllConstants.PACKAGES).child(udid);
                mDatabase.keepSynced(true);
                FLAG_CART_ADD = false;

                Query query = mDatabase;
                int finalValidacionPq = validacionPq;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            if(UPDATE_NODE){

                               mDatabase.child(key_).setValue(new CalculationModel(userId, "1",
                                                    referencias.getText().toString(), "0", "0", "1", "0",
                                                    "0", null, "1", "1", "Paqueteria", "$", "desc", "0", "0"));


                        //        mDatabase.child(key_).setValue(new CalculationModelProducto(user_id, insAdicionales.getText().toString(),referencias.getText().toString(),"$","0","1","0",finalValidacionPq));
                            }
                            else {
                                userId = mDatabase.push().getKey();
//                                mDatabase.child(userId).setValue(new CalculationModelProducto(userId, insAdicionales.getText().toString(),referencias.getText().toString(),"$","0","1","0",finalValidacionPq));

                                mDatabase.child(userId).setValue(new CalculationModel(userId, "1",
                                        referencias.getText().toString(), "0", "0", "1", "0",
                                        "0", null, "1", "1", "Paqueteria", "$", "desc", "0", "0"));

                            }
                        }
                        else {

                            if(UPDATE_NODE){
                       //         mDatabase.child(key_).setValue(new CalculationModelProducto(user_id, insAdicionales.getText().toString(),referencias.getText().toString(),"$","0","1","0",finalValidacionPq));
                                mDatabase.child(key_).setValue(new CalculationModel(user_id, "1",
                                        referencias.getText().toString(), "0", "0", "1", "0",
                                        "0", null, "1", "1", "Paqueteria", "$", "desc", "0", "0"));



                            }
                            else {
                                userId = mDatabase.push().getKey();
                                //mDatabase.child(userId).setValue(new CalculationModelProducto(userId, insAdicionales.getText().toString(),referencias.getText().toString(),"$","0","1","0",finalValidacionPq));

                                mDatabase.child(userId).setValue(new CalculationModel(userId, "1",
                                        referencias.getText().toString(), "0", "0", "1", "0",
                                        "0", null, "1", "1", "Paqueteria", "$", "desc", "0", "0"));




                            }
                        }


                        fragmentCart = new CartFragmentPaquetes() ;
                        fragmentTransaction = getFragmentManager().beginTransaction();
                        Bundle bundle=new Bundle();
                        bundle.putString("validacionP", String.valueOf(finalValidacionPq));
                        bundle.putString("referencias",referencias.getText().toString());
                        bundle.putString("insAdicionales",insAdicionales.getText().toString());

                        fragmentTransaction.add(R.id.contenedorFragment,fragmentCart).commit();
                        fragmentTransaction.addToBackStack(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

        return view;
    }





}