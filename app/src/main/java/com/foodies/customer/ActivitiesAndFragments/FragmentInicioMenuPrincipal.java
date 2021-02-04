package com.foodies.customer.ActivitiesAndFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.R;

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

    Fragment fragmentInicioPrincipal,fragmentAdondeloLlevamos,fragmentQvacomprar,fragmentPaquetes;
    FragmentTransaction fragmentTransaction;
    Button btnAdondeloLlevamos;
    Button btnQueComprar;
    Button btnMenu;

    String getCurrentLocationAddress;



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

        btnAdondeloLlevamos = view.findViewById(R.id.btnAdondeLollevamos);

        btnQueComprar = view.findViewById(R.id.btnQueVacomprar);

        btnMenu = view.findViewById(R.id.btnMenu);

        btnAdondeloLlevamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentAdondeloLlevamos = new FragmentAdondeloLlevamos();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedorFragment,fragmentAdondeloLlevamos).commit();
                fragmentTransaction.addToBackStack(null);

            }
        });


        btnQueComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentPaquetes = new FragmentPaquetes();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedorFragment,fragmentPaquetes).commit();
                fragmentTransaction.addToBackStack(null);
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