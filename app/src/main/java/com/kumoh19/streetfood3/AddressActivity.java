package com.kumoh19.streetfood3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kumoh19.streetfood3.databinding.FragmentHomeBinding;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressActivity extends AppCompatActivity implements MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private GpsTracker gpsTracker;
    private HomeActivity HomeActivity;
    private FragmentHomeBinding binding;
    private String address1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        gpsTracker = new GpsTracker(this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        // 줌 레벨 변경
        mapView.setZoomLevel(2, true);

        //마커 찍기
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);

        //현재위치 주소
        MapReverseGeoCoder mapGeoCoder = new MapReverseGeoCoder( "312c2631f940e1e1b41dad098ccae072", MARKER_POINT, this, this );
        mapGeoCoder.startFindingAddress();

        //위치 등록 버튼
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

            }
        });
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress( MapReverseGeoCoder arg)
    {
        Toast.makeText(this, "주소를 찾는데 실패했습니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReverseGeoCoderFoundAddress( MapReverseGeoCoder arg0, String str )
    {
        Button register_btn = (Button) findViewById(R.id.register);
        TextView address_name = (TextView) findViewById(R.id.address_name);
        address_name.setText(str);
    }

}