package com.kumoh19.streetfood3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyregisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyregisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference mDatabase;

    String userId;
    Fragment fg;

    RecyclerView recyclerView;
    Adapter adapter;

    public MyregisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyregisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyregisterFragment newInstance(String param1, String param2) {
        MyregisterFragment fragment = new MyregisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static MyregisterFragment newInstance() {
        return new MyregisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_myregister, container, false);

        // 아이디 가져오기
        Bundle bundle = getArguments();
        if(bundle != null) {
            userId = bundle.getString("param1");
            Log.i("MyregisterFragment", "사용자아이디 " + userId);
        }

        // 리사이클러뷰
        recyclerView = (RecyclerView) view.findViewById(R.id.recyceler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        mDatabase = FirebaseDatabase.getInstance("https://streetfood-2c61e-default-rtdb.firebaseio.com/").getReference("MY등록");
        readShopMyregister(userId);



        //recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    String shopName;
    String shopAddress;
    String shopCategory;
    
    public void readShopMyregister(String userId) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter = new Adapter();
                //recyclerView.setAdapter(adapter);
                ArrayList<ShopItem> mShopItems = new ArrayList<>();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.i("MyregisterFragment", "스냅샷" + data);
                    String id = data.getKey();
                    if (id.equals(userId)) {
                        for(DataSnapshot data1 : data.getChildren()) {
                            if(data.getValue(Shop.class) != null) {
                                Shop post = data1.getValue(Shop.class);
                                shopName = post.getshopName();
                                shopAddress = post.getshopAddress();
                                shopCategory = post.getshopCatagory();
                                Log.i("MyregisterFragment", "나" + shopName);
                                mShopItems.add(new ShopItem(shopName, shopAddress, shopCategory));
                            }
                        }
                    } else {
                            //Toast.makeText(getContext(), "데이터 없음...", Toast.LENGTH_SHORT).show();
                        }
                }
                recyclerView.setAdapter(adapter);
                adapter.setShopList(mShopItems);
                adapter.setOnItemClickListener(
                        new Adapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int pos) {
                                String name = mShopItems.get(pos).getShopName();
                                fg = ShopinfoFragment.newInstance(name, null);
                                setChildFragment(fg);
                            }

                        }
                );

            }
                // Get Post object and use the values to update the UI

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.child_fragment3, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}

