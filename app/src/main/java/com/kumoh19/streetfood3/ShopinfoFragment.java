package com.kumoh19.streetfood3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopinfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopinfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Fragment fg;

    String POIName;
    String shopName;
    String shopTime;
    String shopPrice;
    String shopCatagory;
    String shopAddress;
    String shopInfo;

    TextView tshopName;
    TextView tshopTime;
    TextView tshopPrice;
    TextView tshopCatagory;
    TextView tshopAddress;
    TextView tshopInfo;

    private DatabaseReference mDatabase;

    public ShopinfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopinfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopinfoFragment newInstance(String param1, String param2) {
        ShopinfoFragment fragment = new ShopinfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ShopinfoFragment newInstance() {
        return new ShopinfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void setParentFragment(Fragment parent) {
        FragmentTransaction parentFt = getParentFragmentManager().beginTransaction();

        if (!parent.isAdded()) {
            parentFt.replace(R.id.child_fragment1, parent);
            parentFt.addToBackStack(null);
            parentFt.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_shopinfo, container, false);
        // 정보 가져오기
        tshopName = (TextView) root.findViewById(R.id.info_name);
        tshopTime = (TextView) root.findViewById(R.id.info_time);
        tshopPrice = (TextView) root.findViewById(R.id.info_price);
        tshopCatagory = (TextView) root.findViewById(R.id.info_catagory);
        tshopAddress = (TextView) root.findViewById(R.id.info_address);
        tshopInfo = (TextView) root.findViewById(R.id.info_info);

        Bundle bundle = getArguments();
        // Log.i("Add", "번들: " + bundle);
        if(bundle != null) {
            POIName = bundle.getString("param1");
            tshopName.setText(POIName);
        }

        // firebase에서 데이터 가져오기
        mDatabase = FirebaseDatabase.getInstance("https://streetfood-2c61e-default-rtdb.firebaseio.com/").getReference("MY등록");
        readShopinfo();

        return root;
    }

    public void readShopinfo() { // firebase에서 데이터 가져와서 출력
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    for(DataSnapshot data1 : data.getChildren()) {
                        if(data.getValue(Shop.class) != null){
                            Shop post = data1.getValue(Shop.class);
                            shopName = post.getshopName();
                            if(POIName.equals(shopName)) {
                                shopTime = post.getshopTime();
                                shopPrice = post.getshopPrice();
                                shopCatagory = post.getshopCatagory();
                                shopAddress = post.getshopAddress();
                                shopInfo = post.getshopInfo();

                                tshopTime.setText(shopTime);
                                tshopPrice.setText(shopPrice);
                                tshopCatagory.setText(shopCatagory);
                                tshopAddress.setText(shopAddress);
                                tshopInfo.setText(shopInfo);
                            }
                        } else {
                            Toast.makeText(getContext(), "데이터 없음...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                // Get Post object and use the values to update the UI

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}