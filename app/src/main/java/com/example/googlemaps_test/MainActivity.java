package com.example.googlemaps_test;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {//OnMapReadyCallback implements 으로 인해 자동 호출
        mapView = googleMap;
        mapView.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        addMarker1(37.302716,127.920501);
        addMarker1(37.302728,127.920019);
    }

    public void addMarker1(double x, double y){ //현재 지도에서 하나의 마커를 추가할떄
        LatLng wonju = new LatLng(x, y);

        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(wonju)
                .title("원하는 위치(위도, 경도)에 마커를 표시했습니다.")
                .snippet("여기는 흥업면인거같네요!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .alpha(0.5f);

        mapView.addMarker(makerOptions);
        mapView.setOnInfoWindowClickListener(infoWindowClickListener);
        mapView.setOnMarkerClickListener(markerClickListener);

        mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(wonju, 16));

        mapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "눌렀습니다!!", Toast.LENGTH_LONG);
                return false;
            }
        });
    }

    public void manyMarker(double x, double y,int size) { //한번에 여러개 마커를 띄울때

        for (int idx = 0; idx < size; idx++) {

            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions
                    .position(new LatLng(x + idx, y))
                    .title("마커" + idx);

            mapView.addMarker(makerOptions);
        }

        mapView.setOnInfoWindowClickListener(infoWindowClickListener);

        mapView.setOnMarkerClickListener(markerClickListener);

        mapView.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.52487, 126.92723)));
    }


    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
            Toast.makeText(MainActivity.this, "정보창 클릭 Marker ID : "+markerId, Toast.LENGTH_SHORT).show();
        }
    };

    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerId = marker.getId();
            LatLng location = marker.getPosition();
            Toast.makeText(MainActivity.this, "마커 클릭 Marker ID : "+markerId+"("+location.latitude+" "+location.longitude+")", Toast.LENGTH_SHORT).show();
            return false;
        }
    };
}