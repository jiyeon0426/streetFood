package com.kumoh19.streetfood3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategorymapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategorymapFragment extends Fragment implements MapView.POIItemEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GpsTracker gpsTracker;
    private DatabaseReference mDatabase;
    MapView mapView;
    Fragment fg;
    String catagory;

    public CategorymapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategorymapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategorymapFragment newInstance(String param1, String param2) {
        CategorymapFragment fragment = new CategorymapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CategorymapFragment newInstance() {
        return new CategorymapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.child_fragment2, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_catagorymap, container, false);

        //MapView mapView = new MapView(getActivity());
        mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) root.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        if (((HomeActivity)getActivity()).checkLocationServicesStatus()) {
            ((HomeActivity)getActivity()).checkRunTimePermission();
        } else {
            ((HomeActivity)getActivity()).showDialogForLocationServiceSetting();
        }

        gpsTracker = new GpsTracker(getActivity());

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        // 줌 레벨 변경
        mapView.setZoomLevel(2, true);

        mapView.setPOIItemEventListener(this);
        //mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        //현재위치 마커 찍기
//        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(latitude, longitude);
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("현재위치");
//        marker.setTag(0);
//        marker.setMapPoint(MARKER_POINT);
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//        mapView.addPOIItem(marker);

        // 카테고리 가져오기
        Bundle bundle = getArguments();
        if(bundle != null) {
            catagory = bundle.getString("param1");
            //Log.i("CategorymapFragment", "카테고리 " + catagory);
        }

        mDatabase = FirebaseDatabase.getInstance("https://streetfood-2c61e-default-rtdb.firebaseio.com/").getReference("MY등록");
        readShopCatagory(catagory);


        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        fg = ShopinfoFragment.newInstance(mapPOIItem.getItemName(), null);
        setChildFragment(fg);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    String shopName;
    String shopCatagory;
    double shopLat;
    double shopLng;

    public void readShopCatagory(String catagory) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    for(DataSnapshot data1 : data.getChildren()) {
                        if(data.getValue(Shop.class) != null) {
                            Shop post = data1.getValue(Shop.class);
                            shopCatagory = post.getshopCatagory();
                            if (shopCatagory.equals(catagory)) {
                                shopName = post.getshopName();
                                shopLat = post.getShopLatitude();
                                shopLng = post.getShopLongitude();
                                Log.i("CategorymapFragment", "ㅋㅋ" + shopName);

                            // 마커찍기
                            MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(shopLat, shopLng);
                            MapPOIItem marker = new MapPOIItem();
                            marker.setItemName(shopName);
                            marker.setTag(0);
                            marker.setMapPoint(MARKER_POINT);
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 기본으로 제공하는 BluePin 마커 모양.
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                            mapView.addPOIItem(marker);

                            } else {
                                //Toast.makeText(getContext(), "데이터 없음...", Toast.LENGTH_SHORT).show();
                            }
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