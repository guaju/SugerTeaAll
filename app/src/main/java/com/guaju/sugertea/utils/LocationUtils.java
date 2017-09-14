package com.guaju.sugertea.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by guaju on 2017/8/14.
 */

public class LocationUtils {
    private static final String TAG = "LocationUtils";

    public static HashMap<String, String> getLL(final Activity act) {
        //获得管理定位的一个Manager
        LocationManager manager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);
        //拿到当前可用的定位方式
        List<String> allProviders = manager.getAllProviders();
        for (String str : allProviders) {
        }
        //拿到最佳的定位方式
        Criteria criteria = new Criteria();
        String bestProvider = manager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        manager.requestLocationUpdates(bestProvider, 0, 5.0f, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Toast.makeText(act, "listener "+latitude+"--"+longitude, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });



        return null;
    }

}
