package com.example.foodineye_app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodineye_app.GazeTrackerDataStorage;
import com.example.foodineye_app.R;

import java.util.List;

import visual.camp.sample.view.PointView;

public class ShoppingCartActivity extends AppCompatActivity implements CartAdapter.OnItemClickListener {

    Toolbar toolbar;

    List<Cart> cartList;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    TextView totalPrice;
    TextView toOrder;

    int total;

    //-----------------------------------------------------------------------------------------
    //gazetracker
    GazeTrackerDataStorage gazeTrackerDataStorage;
    private final HandlerThread backgroundThread = new HandlerThread("background");

    Context ctx;
    ConstraintLayout storeLayout;
    PointView viewpoint;

    //-----------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        toolbar = (Toolbar) findViewById(R.id.cart_toolbar);
        setToolBar(toolbar);

        //-------------------------------------------------------------------------------------
        //start-gaze-tracking
        ctx = getApplicationContext();
        storeLayout = findViewById(R.id.cartLayout);
        viewpoint = findViewById(R.id.view_point_cart);

        setGazeTrackerDataStorage();

        //-------------------------------------------------------------------------------------

        cartList = ((Data) getApplication()).getCartList();
        total = ((Data)getApplication()).getTotalPrice();

        recyclerView = findViewById(R.id.recyclerView_cartList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cartAdapter = new CartAdapter(getApplicationContext(), cartList, this);
        recyclerView.setAdapter(cartAdapter);

        //총 가격
        totalPrice = (TextView) findViewById(R.id.cart_totalPrice);
        totalPrice.setText(String.valueOf(total));

        //주문하기
        toOrder = (TextView) findViewById(R.id.orderBtn);
        toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onItemClick(){
        int total = ((Data)getApplication()).getTotalPrice();
        totalPrice.setText(String.valueOf(total));
    }
    @Override
    public void onDeleteClick(int position){
        cartList.remove(position);
        cartAdapter.notifyItemRemoved(position);
    }
    @Override
    public void onToMenuClick(){
        String s_id = ((Data)getApplication()).getRecentS_id();
        String m_id = ((Data)getApplication()).getRecentM_id();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("intent_recentSId", s_id);
        intent.putExtra("intent_cartMId", m_id);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setGazeTrackerDataStorage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopGazeTracker();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gazeTrackerDataStorage.quitBackgroundThread();
        backgroundThread.quitSafely();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    //gazeTracker
    private void setGazeTrackerDataStorage(){
        gazeTrackerDataStorage = new GazeTrackerDataStorage(this);
        gazeTrackerDataStorage.setContext(this);

        if (gazeTrackerDataStorage != null) {
            gazeTrackerDataStorage.setGazeTracker(ctx, storeLayout, viewpoint);
        }
    }

    private void stopGazeTracker() {
        if (gazeTrackerDataStorage != null) {
            gazeTrackerDataStorage.stopGazeTracker("cart", 0, 0);
        }
    }

    //toolbar
    private void setToolBar(androidx.appcompat.widget.Toolbar toolbar){

        // 툴바를 액션바로 설정
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(""); // 툴바의 타이틀을 직접 설정
        ImageView backBtn = (ImageView) findViewById(R.id.cart_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뒤로 가기 버튼 동작을 처리
                onBackPressed();
            }
        });

        ImageView homeBtn = (ImageView) findViewById(R.id.cart_home);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-> home
                Intent loginIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

    }


    //
    // Miscellaneous
    //

    private void show(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}