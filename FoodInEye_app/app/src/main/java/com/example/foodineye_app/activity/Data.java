package com.example.foodineye_app.activity;

import android.app.Application;
import android.util.Log;

import com.example.foodineye_app.data.Order;
import com.example.foodineye_app.gaze.PostGaze;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import visual.camp.sample.view.CalibrationViewer;

public class Data extends Application {

    // layout 전체
    private static JSONArray jsonGazeArray = new JSONArray();
    private static List<PostGaze> GazeList = new ArrayList<>();

    private List<Cart> cartList;
    private String recentS_id, recentM_id;
    private List<Order> orderList;
    private String history_id; // 한번의 주문내역 지칭(웹소켓 통신시)

    //calibration
    private CalibrationViewer viewCalibration;

    //주문 가능한 상태인지 - 현재 모든 주문이 status==2가 아닌 경우 주문 false
    private boolean isOrder;

    //-------------------------------------------------------------------------------------
    public CalibrationViewer getViewCalibration() {
        return viewCalibration;
    }

    public void setViewCalibration(CalibrationViewer viewCalibration) {
        this.viewCalibration = viewCalibration;
    }

    //orderList
    public List<Order> getOrderList(){ return orderList; }

    //carList
    @Override
    public void onCreate(){
        cartList = new ArrayList<>();
        orderList = new ArrayList<>();
        super.onCreate();
    }

    public List<Cart> getCartList() {  return cartList;  }

    //중복확인
    public boolean containsItem(List<Cart> cartList, Cart cart) {
        for (Cart c : cartList) {
            if (c.getF_id().equals(cart.getF_id())) {
                return true;
            }
        }
        return false;
    }
    public void setCartList(Cart cart){
        if(containsItem(cartList, cart)){
            for(Cart c : cartList){
                if (c.getF_id().equals(cart.getF_id())) {
                    c.increase_count();
                }
            }
        }else{
            cartList.add(cart);
        }
    }
    public int getTotalPrice(){
        int total = 0;
        for(Cart cart : cartList){
            total += cart.getM_TotalPrice();
        }
        return total;
    }

    public String getRecentS_id() {   return recentS_id;  }

    public void setRecentS_id(String recentS_id) {   this.recentS_id = recentS_id;   }

    public String getRecentM_id() {   return recentM_id;   }

    public void setRecentM_id(String recentM_id) {    this.recentM_id = recentM_id;  }

    public void setOrderList(List<Order> orderList) {    this.orderList = orderList;   }

    public String getHistory_id() {    return history_id;   }

    public void setHistory_id(String history_id) {    this.history_id = history_id;    }

    public JSONArray getJsonArray() {
        if (jsonGazeArray == null) {
            jsonGazeArray = new JSONArray();
        }
        return jsonGazeArray;
    }

    public static void addJsonObject(JSONObject jsonObject) {
        if (jsonGazeArray == null) {
            jsonGazeArray = new JSONArray();
        }
        jsonGazeArray.put(jsonObject);
        Log.i("json", "jsonArray"+jsonGazeArray.toString());
    }

    public List<PostGaze> getGazeList() {
        return GazeList;
    }

    public static void addGazeList(PostGaze postGaze) {
        GazeList.add(postGaze);
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }

    //초기화
    public void initializeAllVariables() {
        jsonGazeArray = new JSONArray();
        GazeList = new ArrayList<>();
        cartList = new ArrayList<>();
        recentS_id = null;
        recentM_id = null;
        orderList = new ArrayList<>();
        history_id = null;
        viewCalibration = null;
        isOrder = true;
    }

    @Override
    public String toString() {
        return "Data{" +
                "cartList=" + cartList +
                ", recentS_id='" + recentS_id + '\'' +
                ", recentM_id='" + recentM_id + '\'' +
                ", orderList=" + orderList +
                ", history_id='" + history_id + '\'' +
                ", viewCalibration=" + viewCalibration +
                '}';
    }
}