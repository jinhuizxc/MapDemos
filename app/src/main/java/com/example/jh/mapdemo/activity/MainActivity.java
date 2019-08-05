package com.example.jh.mapdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.example.jh.mapdemo.R;
import com.example.jh.mapdemo.utils.MapUtils;


/**
 * 仿微信发送位置demo——项目中用到！
 *
 * 包名: com.example.jh.mapdemo
 * MyApp.jks
 * SHA1:  77:6D:B9:A7:BE:66:7B:4B:F4:7C:00:F5:77:00:14:70:1F:68:6F:92
 *
 * E:\Java\jdk1.8.0_201\bin>keytool -v -list -keystore E:\workspace_android\Map\MapDemos\app\MyApp.jks
 *
 * 包名: com.test.app1
 * E:\Java\jdk1.8.0_201\bin>keytool -v -list -keystore E:\workspace_android\Map\MapDemos\app\app1.jks
 * SHA1: AF:1D:6F:E0:4E:20:C6:88:A8:7E:8F:2B:32:4D:51:3E:FE:5E:B0:2D
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button send;
    private TextView tv_city;
    private TextView tv_lon;
    private TextView tv_lat;
    private TextView tv_location;
    private TextView tv_poi;

    private double longitude;
    private double latitude;
    private String cityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = (Button) findViewById(R.id.btn_send);

        tv_city = (TextView) findViewById(R.id.city);
        tv_lon = (TextView) findViewById(R.id.lon);
        tv_lat = (TextView) findViewById(R.id.lat);
        tv_location = (TextView) findViewById(R.id.location);
        tv_poi = (TextView) findViewById(R.id.poi);

        send.setOnClickListener(this);

        // 请求定位权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }

        //获取定位信息
        MapUtils mapUtils = new MapUtils();
        mapUtils.getLonLat(this, new MyLonLatListener());
    }

    class MyLonLatListener implements MapUtils.LonLatListener {

        @Override
        public void getLonLat(AMapLocation amapLocation) {

            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    latitude = amapLocation.getLatitude();//获取纬度
                    longitude = amapLocation.getLongitude();//获取经度
                    cityCode = amapLocation.getCityCode();
                    tv_lat.setText("当前纬度：" + latitude);
                    tv_lon.setText("当前经度：" + longitude);
                    tv_location.setText("当前位置：" + amapLocation.getAddress());
                    tv_city.setText("当前城市：" + amapLocation.getProvince() + "-" + amapLocation.getCity() + "-" + amapLocation.getDistrict() + "-" + amapLocation.getStreet() + "-" + amapLocation.getStreetNum());

                    tv_poi.setText("当前位置：" + amapLocation.getAoiName());
                    amapLocation.getAccuracy();//获取精度信息
                } else {
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());

                    Toast.makeText(MainActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
//        if (v == send) {
//            Intent intent = new Intent();
//            intent.putExtra("longitude", longitude);
//            intent.putExtra("latitude", latitude);
//            intent.putExtra("cityCode", cityCode);
//            intent.setClass(this, ShareLocationActivity.class);
//            startActivity(intent);
//        }
        if (v == send) {
            Intent intent = new Intent();
            intent.putExtra("longitude", longitude);
            intent.putExtra("latitude", latitude);
            intent.putExtra("cityCode", cityCode);
            intent.setClass(this, TestLocationActivity.class);
            startActivity(intent);
        }
    }
}
