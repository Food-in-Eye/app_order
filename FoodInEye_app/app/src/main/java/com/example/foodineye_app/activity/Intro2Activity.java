package com.example.foodineye_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.foodineye_app.R;

public class Intro2Activity extends AppCompatActivity {

    Toolbar toolbar;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);

        //toolbar
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.intro2_toolbar);
        setToolBar(toolbar);

        //nettBtn
        nextBtn = (Button) findViewById(R.id.intro2_next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent introIntent = new Intent(getApplicationContext(), Intro3Activity.class);
                startActivity(introIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    //toolbar
    private void setToolBar(androidx.appcompat.widget.Toolbar toolbar){

        // 툴바를 액션바로 설정
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(""); // 툴바의 타이틀을 직접 설정
        ImageView backBtn = (ImageView) findViewById(R.id.intro2_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뒤로 가기 버튼 동작을 처리
                onBackPressed();
            }
        });

        ImageView homeBtn = (ImageView) findViewById(R.id.intro2_home);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-> home
                Intent loginIntent = new Intent(getApplicationContext(), Intro3Activity.class);
                startActivity(loginIntent);
                finish();
            }
        });

    }
}