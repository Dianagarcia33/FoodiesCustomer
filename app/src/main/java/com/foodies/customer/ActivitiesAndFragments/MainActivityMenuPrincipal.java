package com.foodies.customer.ActivitiesAndFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.foodies.customer.R;

public class MainActivityMenuPrincipal extends AppCompatActivity {

    Fragment fragmentInicioPrincipal,fragmentAdondeloLlevamos,fragmentQvacomprar;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_principal);

        fragmentInicioPrincipal = new FragmentInicioMenuPrincipal();
        fragmentAdondeloLlevamos = new FragmentAdondeloLlevamos();
        fragmentQvacomprar = new FragmentMquevacomprar();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment,fragmentInicioPrincipal).commit();

    }


}