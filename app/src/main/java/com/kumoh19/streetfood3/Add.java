package com.kumoh19.streetfood3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add extends Fragment {

    static final int REQ_ADD_CONTACT = 1;
    String address;
    double lat;
    double lng;
    Fragment fg;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private double mParam3;
    private double mParam4;

    private DatabaseReference mDatabase;

    public Add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add.
     */
    // TODO: Rename and change types and number of parameters
    public static Add newInstance(String param1, String param2) {
        Add fragment = new Add();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Add newInstance(String param1, double param3, double param4) {
        Add fragment = new Add();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putDouble(ARG_PARAM3, param3);
        args.putDouble(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    public static Add newInstance() {
        return new Add();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getDouble(ARG_PARAM1);
            mParam4 = getArguments().getDouble(ARG_PARAM2);
        }
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.child_fragment, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        // 카테고리 스피너
        Spinner spn = (Spinner) root.findViewById(R.id.add_catagory);
        ArrayAdapter<CharSequence> spn_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.catagory_array,
                android.R.layout.simple_spinner_item);

        spn_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn.setAdapter(spn_adapter);
        spn.setSelection(0);



        // 지도모양 버튼
        ImageButton address_map = (ImageButton) root.findViewById(R.id.address_map);
        address_map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                fg = AddressFragment.newInstance();
                setChildFragment(fg);
            }
        });

        //가게등록 버튼
        Button register = (Button) root.findViewById(R.id.add_shop);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //firebase 정의
                mDatabase = FirebaseDatabase.getInstance("https://streetfood-2c61e-default-rtdb.firebaseio.com/").getReference("MY등록");
                readShop();

                EditText name = root.findViewById(R.id.add_name);
                EditText time = root.findViewById(R.id.add_time);
                EditText price = root.findViewById(R.id.add_price);
                //EditText catagory = root.findViewById(R.id.add_catagory);

                EditText info = root.findViewById(R.id.add_info);
                EditText address = root.findViewById(R.id.add_address);

                String strName = name.getText().toString();
                String strTime = time.getText().toString();
                String strPrice = price.getText().toString();
                String strCatagory = spn.getSelectedItem().toString();
                String strInfo = info.getText().toString();
                String strAddress = address.getText().toString();
                double douLat = lat;
                double douLng = lng;

                Log.i("Add", "클릭위도: " + lat);
                Log.i("Add", "클릭경도: " + lng);

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("name", strName);
                result.put("time", strTime);
                result.put("price", strPrice);
                result.put("catagory", strCatagory);
                result.put("info", strInfo);
                result.put("address", strAddress);
                result.put("위도", douLat);
                result.put("경도", douLng);


                writeNewShop(strName, strTime, strPrice, strCatagory, strInfo, strAddress, douLat, douLng);
            }
        });
        // 주소 가져오기
        TextView address_edit = (TextView) root.findViewById(R.id.add_address);

        Bundle bundle = getArguments();
        // Log.i("Add", "번들: " + bundle);
        if(bundle != null) {
            address = bundle.getString("param1");
            //Log.i("Add", "주소: " + address);
            address_edit.setText(address);
            lat = bundle.getDouble("param3");
            lng = bundle.getDouble("param4");

            Log.i("Add", "위도: " + lat);
            Log.i("Add", "경도: " + lng);
        }

        // 위도 경도 가져오기


        // 위치 가져오기
//        TextView address_edit = (TextView) root.findViewById(R.id.add_address);
//        Bundle bundle = getArguments();
//        onCreate(bundle);
//        str = mParam1;
//        Log.i("Add", "ADR : " + mParam1);
//        address_edit.setText("위치");
        // Inflate the layout for this fragment
        return root;
    }



    private void writeNewShop(String shopName, String shopTime, String shopPrice, String shopCatagory, String shopInfo, String shopAddress, double shopLatitude, double shopLongitude) {
        Shop shop = new Shop(shopName, shopTime, shopPrice, shopCatagory, shopInfo, shopAddress, shopLatitude, shopLongitude);

        mDatabase.child(((HomeActivity)getActivity()).strId).child(shopName).setValue(shop)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(getContext(), "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(getContext(), "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void readShop() {
        mDatabase.child(((HomeActivity)getActivity()).strId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(Shop.class) != null){
                    Shop post = dataSnapshot.getValue(Shop.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    //Toast.makeText(getContext(), "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}