package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;

import com.foodies.customer.Constants.GpsUtils;
import com.foodies.customer.GoogleMapWork.MapsActivity;
import com.foodies.customer.R;

import static android.content.Context.LOCATION_SERVICE;

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


        textSeleccionarTxt1 = view.findViewById(R.id.selccionarMadondeVamos);

        textSeleccionarTxt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MapsActivity.class);
                startActivityForResult(i, PERMISSION_DATA_ACCESS_CODE);
            }
        });

        btnPaquetes = view.findViewById(R.id.btnPaquetes);

        btnPaquetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCart = new CartFragment();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedorFragment,fragmentCart).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        return view;
    }





}