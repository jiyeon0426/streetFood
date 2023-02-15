package com.kumoh19.streetfood3;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kumoh19.streetfood3.databinding.FragmentHomeBinding;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;

public class HomeFragment extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener{

    private static final String LOG_TAG = "HomeFragment";


    private GpsTracker gpsTracker;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private DatabaseReference mDatabase;

    String shopName;
    double shopLat;
    double shopLng;

    MapView mapView;

    Fragment fg;

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.child_fragment1, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        Log.i("디테일로그", "onMapViewSingleTapped");
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //Toast.makeText(getContext(), "Clicked " + mapPOIItem.getItemName() + " Callout Balloon", Toast.LENGTH_SHORT).show();

        fg = ShopinfoFragment.newInstance(mapPOIItem.getItemName(), null);
        setChildFragment(fg);
        Log.i("HomeFragment", "가게이름: " + mapPOIItem.getItemName());
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, v));
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    // CalloutBalloonAdapter 인터페이스 구현
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        //private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            //mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }


        @Override
        public View getCalloutBalloon(MapPOIItem mapPOIItem) {
            return null;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }





    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        //MapView mapView = new MapView(getActivity());
        mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) root.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
//        if (!checkLocationServicesStatus()) {
//            showDialogForLocationServiceSetting();
//        }else {
//            checkRunTimePermission();
//        }

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

//        //현재위치 마커 찍기
//        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(latitude, longitude);
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("현재위치");
//        marker.setTag(0);
//        marker.setMapPoint(MARKER_POINT);
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//        mapView.addPOIItem(marker);

        // 데이터 가져와서 마커 찍기
        mDatabase = FirebaseDatabase.getInstance("https://streetfood-2c61e-default-rtdb.firebaseio.com/").getReference("MY등록");
        readShop();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    public void readShop() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    for(DataSnapshot data1 : data.getChildren()) {
                        if(data.getValue(Shop.class) != null){
                            Shop post = data1.getValue(Shop.class);
                            shopName = post.getshopName();
                            shopLat = post.getShopLatitude();
                            shopLng = post.getShopLongitude();
                            Log.i("HomeFragment", "shop" + shopName);
                            Log.i("HomeFragment", "shop" + shopLat);
                            Log.i("HomeFragment", "shop" + shopLng);
                            Log.i("HomeFragment", "아이디" + post);

                            // 마커찍기
                            MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(shopLat, shopLng);
                            MapPOIItem marker = new MapPOIItem();
                            marker.setItemName(shopName);
                            marker.setTag(0);
                            marker.setMapPoint(MARKER_POINT);
                            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 기본으로 제공하는 BluePin 마커 모양.
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                            mapView.addPOIItem(marker);
                            //mapView.selectPOIItem(marker,true);

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