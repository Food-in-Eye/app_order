package com.example.foodineye_app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.foodineye_app.ApiClient;
import com.example.foodineye_app.ApiInterface;
import com.example.foodineye_app.R;
import com.example.foodineye_app.data.PutEyePermission;
import com.example.foodineye_app.gaze.PermissionRequester;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity {

    CheckBox agreeCK, disagreeCK;
    Boolean eyePermission;
    Button nextBtn;

    SharedPreferences sharedPreferences;
    String u_id;

    BottomNavigationView bottomNavigationView;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        sharedPreferences = getSharedPreferences("test_token1", MODE_PRIVATE);
        u_id = sharedPreferences.getString("u_id", null);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        clickNavigation();

        agreeCK = (CheckBox) findViewById(R.id.camera_agree);
        disagreeCK = (CheckBox) findViewById(R.id.camera_disagree);

        agreeCK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //동의했을 때
                if (agreeCK.isChecked()) {
                    disagreeCK.setChecked(false);
                    checkCameraPermission();
                }
            }
        });

        disagreeCK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //동의하지 않았을 때
                if (disagreeCK.isChecked()) {
                    agreeCK.setChecked(false);
                    eyePermission = false;
                }
            }
        });

        nextBtn = (Button) findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                putCheck();

                if(eyePermission){

                    //동의
                    startCalibrationActivity();

                }else{

                    //비동의
                    startStorelistActivity();

                }
            }
        });

    }

    //checkbox 결과 PUT 보내기
    public void putCheck(){

        PutEyePermission putEyePermission = new PutEyePermission(u_id, eyePermission);

        // 초기 페이지 로딩
        ApiClient apiClient = new ApiClient(getApplicationContext());
        apiClient.initializeHttpClient();

        ApiInterface apiInterface = apiClient.getClient().create(ApiInterface.class);

        Call<Void> call = apiInterface.putEyePermission(u_id, putEyePermission);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.d("CameraActivity", "PUT: 200 OK");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("CameraActivity", "onFailure: " + t.toString());
            }
        });


    }

    //권한 체크하고 카메라 권한 요청
    public void isPermission(){

        // 권한을 먼저 확인하고, 권한이 없는 경우에만 권한 요청을 수행합니다.
        if (PermissionRequester.hasPermissions(this, Manifest.permission.CAMERA)) {

            // 권한이 이미 동의되어 있을 때의 처리
            Intent loginIntent = new Intent(getApplicationContext(), CalibrationActivity.class);
            startActivity(loginIntent);

        } else {

            // 권한이 없는 경우 권한 요청
            PermissionRequester.request(this);
            setContentView(R.layout.activity_camera);
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

            //권한 요청했는데 허용하지 않음을 체크하면 checkbox -> disagree
        }
    }

    private void checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "카메라 권한에 동의했습니다.", Toast.LENGTH_SHORT).show();
            // 권한이 이미 허용된 경우
//            startCalibrationActivity();

        } else {
            // 권한이 허용되지 않은 경우
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // 사용자가 이전에 권한 요청을 거절한 경우
                Toast.makeText(this, "시선추적을 하기 위해서는 카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }

            // 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MODE_PRIVATE);
        }

        eyePermission = true;
    }

    public void startCalibrationActivity(){
        Intent intent = new Intent(this, CalibrationActivity.class);
        startActivity(intent);
        finish();
    }

    public void startStorelistActivity(){
        Intent intent = new Intent(this, StorelistActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // 권한이 허용된 경우
//                startCalibrationActivity();
//            } else {
//                // 권한이 거부된 경우
//                Toast.makeText(this, "카메라 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
//                // 권한이 거부되면 다시 권한 요청
//                checkCameraPermission();
//            }
//        }
//    }

    public void clickNavigation(){

        // Bottom Navigation Bar에서 항목을 선택할 때의 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_order:
                        // order 탭을 선택한 경우
                        // 홈 화면으로 이동하는 코드를 여기에 추가

                        return true;
                    case R.id.bottom_home:
                        // home 탭을 선택한 경우
                        // 프로필 화면으로 이동하는 코드를 여기에 추가

                        return true;
                    case R.id.bottom_mypage:
                        // mypage 탭을 선택한 경우
                        // 프로필 화면으로 이동하는 코드를 여기에 추가

                        return true;
                }
                return false;
            }
        });
    }
}