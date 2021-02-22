package com.foodies.customer.ActivitiesAndFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodies.customer.Adapters.AdapterRecyclerProductos;
import com.foodies.customer.Adapters.CartFragExpandable;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.CalculationModel;
import com.foodies.customer.Models.CartFragChildModel;
import com.foodies.customer.Models.CartFragParentModel;
import com.foodies.customer.Models.RestaurantChildModel;
import com.foodies.customer.Models.RestaurantsModel;
import com.foodies.customer.R;
import com.foodies.customer.Utils.CustomExpandableListView;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.foodies.customer.ActivitiesAndFragments.CartFragment.UPDATE_NODE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAdondeloLlevamos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAdondeloLlevamos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout buttonSiguiente;
    Fragment fragmentInicioPrincipal,fragmentAdondeloLlevamos,fragmentQvacomprar;
    FragmentTransaction fragmentTransaction;
    private String udid,user_id;
    SharedPreferences sPref;
    private RecyclerView recyclerView;
    boolean getLoINSession,PICK_UP;
    ArrayList<CartFragChildModel> listChildData;
    private  String userId,key_,name_,desc,price_,symbol,res_id,res_name,res_tax,res_fee;

    private static  String key;
    private EditText producto,referenciasProducto,descripcion;

    ArrayList<CartFragParentModel> listDataHeader;
    DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    public static boolean FLAG_CART_ADD;

    RecyclerView selected_item_list;

    AdapterRecyclerProductos cartFragExpandable;
    private ArrayList<ArrayList<CartFragChildModel>> ListChild;
    Collection<Object> values;
    Map<String, Object> td;

    private LinearLayout btnNproducto,btnEditarProducto;

    String grandTotal_ = "0";

    public FragmentAdondeloLlevamos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmenAdondeloLlevamos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAdondeloLlevamos newInstance(String param1, String param2) {
        FragmentAdondeloLlevamos fragment = new FragmentAdondeloLlevamos();
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
        View view =  inflater.inflate(R.layout.fragment_fragmen_adondelo_llevamos, container, false);

        sPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        udid = sPref.getString(PreferenceClass.UDID,"");//"9051e610ebcc1639";
        getLoINSession = sPref.getBoolean(PreferenceClass.IS_LOGIN,false);
        user_id = sPref.getString(PreferenceClass.pre_user_id,"");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference().child(AllConstants.PACKAGES).child(udid);

        btnNproducto = view.findViewById(R.id.btnNproducto);

        btnEditarProducto = view.findViewById(R.id.btnEditarProducto);

        producto = view.findViewById(R.id.editTextNproducto);
        descripcion = view.findViewById(R.id.editTextDproducto);


        selected_item_list = view.findViewById(R.id.selected_item_list);
        mDatabase.keepSynced(true);

        loadProductos();

        buttonSiguiente = view.findViewById(R.id.btnAdondeLollevamos);

        btnNproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = firebaseDatabase.getReference().child(AllConstants.PACKAGES).child(udid);
                mDatabase.keepSynced(true);
                FLAG_CART_ADD = false;

                Query query = mDatabase;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            if(UPDATE_NODE){
                                mDatabase.child(key_).setValue(new CalculationModelProducto(user_id,producto.getText().toString(),descripcion.getText().toString()));
                            }
                            else {
                                userId = mDatabase.push().getKey();
                                mDatabase.child(userId).setValue(new CalculationModelProducto(userId,producto.getText().toString(),descripcion.getText().toString()));
                            }
                        }
                        else {

                            if(UPDATE_NODE){
                                mDatabase.child(key_).setValue(new CalculationModelProducto(userId,producto.getText().toString(),descripcion.getText().toString()));
                            }
                            else {
                                userId = mDatabase.push().getKey();
                                mDatabase.child(userId).setValue(new CalculationModelProducto(userId,producto.getText().toString(),descripcion.getText().toString()));
                            }
                        }
                        loadProductos();
                        producto.setText("");
                        descripcion.setText("");
                        Toast.makeText(getContext(),"Producto Registrado",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

        buttonSiguiente.setEnabled(false);

        buttonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentQvacomprar = new FragmentMquevacomprar();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedorFragment,fragmentQvacomprar).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        btnEditarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = firebaseDatabase.getReference().child(AllConstants.PACKAGES).child(udid);
                mDatabase.keepSynced(true);
                FLAG_CART_ADD = false;

                Query query = mDatabase;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            if(UPDATE_NODE){
                                mDatabase.child(key).setValue(new CalculationModelProducto(key,producto.getText().toString(),descripcion.getText().toString()));
                            }
                            else {
                                userId = mDatabase.push().getKey();
                                mDatabase.child(userId).setValue(new CalculationModelProducto(key,producto.getText().toString(),descripcion.getText().toString()));
                            }
                        }
                        else {

                            if(UPDATE_NODE){
                                mDatabase.child(key).setValue(new CalculationModelProducto(key,producto.getText().toString(),descripcion.getText().toString()));
                            }
                            else {
                                userId = mDatabase.push().getKey();
                                mDatabase.child(userId).setValue(new CalculationModelProducto(key,producto.getText().toString(),descripcion.getText().toString()));
                            }
                        }
                        loadProductos();
                        producto.setText("");
                        descripcion.setText("");
                        Toast.makeText(getContext(),"Listo",Toast.LENGTH_SHORT).show();
                        btnEditarProducto.setVisibility(View.INVISIBLE);
                        btnNproducto.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        return view;

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
        first_tv.setText(R.string.edit);
        first_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFB));
        second_tv.setText(R.string.delete);
        second_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        third_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFB));

        currentOrderDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               editNode();

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


    public void deleteSelectedNode(final String key){

        final DatabaseReference deleteNode = mDatabase.child(key);

        Log.d(AllConstants.tag, key.toString());

        deleteNode.removeValue();

        loadProductos();

    }

    public void loadProductos(){
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
                            grandTotal_= "0";
                            listDataHeader = new ArrayList<>();
                            for (int a = 0; a < jsonArray.length(); a++) {

                                JSONObject allJsonObject = jsonArray.getJSONObject(a);

                                Log.d(AllConstants.tag, allJsonObject.getString("mName"));


                                CartFragParentModel cartFragParentModel = new CartFragParentModel();

                                cartFragParentModel.setItem_key(allJsonObject.optString("key"));
                                cartFragParentModel.setItem_name(allJsonObject.optString("mName"));
                                cartFragParentModel.setItem_description(allJsonObject.optString("description"));


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

                                buttonSiguiente.setEnabled(true);

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

    public void editNode(){

        final DatabaseReference deleteNode = mDatabase.child(key);

        deleteNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("key").getValue(String.class);

                if(name.equalsIgnoreCase(key)){

                    producto.setText(dataSnapshot.child("mName").getValue(String.class));
                    descripcion.setText(dataSnapshot.child("description").getValue(String.class));

                    btnNproducto.setVisibility(View.INVISIBLE);
                    btnEditarProducto.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}