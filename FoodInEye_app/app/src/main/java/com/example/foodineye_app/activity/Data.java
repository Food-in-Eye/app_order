package com.example.foodineye_app.activity;

import android.app.Application;
import android.util.Log;

import com.example.foodineye_app.data.GetMenu;
import com.example.foodineye_app.data.MetaInfoData;
import com.example.foodineye_app.data.Order;
import com.example.foodineye_app.gaze.PostGaze;
import com.example.foodineye_app.gaze.RouletteData;

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

    //roulette을 위한 list
    private int[][] gazeCountList = {
            {0, 0, 0, 0, 0, 0, 0},       // 하울
            {0, 0, 0, 0, 0, 0, 0, 0},    // 파스타
            {0, 0, 0, 0, 0, 0},          // 비빔밥
            {0, 0, 0, 0, 0, 0},          // 해장국
            {0, 0, 0, 0}                 // 니나노 덮밥
    };

    private  RouletteData[] top5List = new RouletteData[5];
    private int totalCount;
    private List<GetMenu> infoList; //전체

    private List<MetaInfoData> metaInfoDataList = new ArrayList<>(); //s_num과 f_num의 전체 배열
    private List<GetMenu> allMenuList; //모든 가게 s_num과 food 정보들의 배열
    private int sNum;

    //-------------------------------------------------------------------------------------

    public List<GetMenu> getAllMenuList() {
        return allMenuList;
    }

    public void setAllMenuList(List<GetMenu> allMenuList) {
        this.allMenuList = allMenuList;
    }

    public void addAllMenuList(GetMenu getMenu){
        if (allMenuList == null) {
            allMenuList = new ArrayList<>();
        }
        allMenuList.add(getMenu);
        Log.d("MyApp", "Data_allMenuList: "+allMenuList.toString());
    }

    //s_num과 f_num을 알 때, f_name 반환
    public Cart findMenu(int sNum, int fNum){
        for (GetMenu getMenu : allMenuList){
            if(getMenu.s_num == sNum){

                if(getMenu.findMenu(getMenu.s_id, getMenu.s_name, sNum, getMenu._id, fNum) != null){
                    return getMenu.findMenu(getMenu.s_id, getMenu.s_name, sNum, getMenu._id, fNum);
                }

            }
        }
        return null;
    }

    public int[][] getGazeCountList() {
        return gazeCountList;
    }

    public void setGazeCountList(int[][] gazeCountList) {
        this.gazeCountList = gazeCountList;
    }

    public RouletteData[] getTop5List() {
        return top5List;
    }

    public void setTop5List(RouletteData[] top5List) {
        this.top5List = top5List;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<MetaInfoData> getMetaInfoDataList() {
        return metaInfoDataList;
    }

    public void setMetaInfoDataList(List<MetaInfoData> metaInfoDataList) {
        this.metaInfoDataList = metaInfoDataList;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    //-------------------------------------------------------------------------------------
    public CalibrationViewer getViewCalibration() {
        return viewCalibration;
    }

    public void setViewCalibration(CalibrationViewer viewCalibration) {
        this.viewCalibration = viewCalibration;
    }

    //orderList
    public List<Order> getOrderList(){ return orderList; }
    public void setOrderList(){

    }

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

    public String getHistory_id() {
        return history_id;
    }

    public void setHistory_id(String history_id) {
        this.history_id = history_id;
    }

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

    //orderList에 있는 모든 status == 2 인지 확인
    public boolean checkStatus(){

        boolean allStatusTwo = true;
        for(Order o : orderList){
            if (o.getStatus() != 2) {
                allStatusTwo = false;
                break;
            }
        }

        if (allStatusTwo) {
            return true;
        }return false;
    }

    //orderList 돌면서 order_id에 해당하는 status update하기
    public void updateStatus(String o_id, int status){

        for(Order o : orderList){
            if(o.getOrderId().equals(o_id)){
                o.setStatus(status);
                break;
            }
        }

    }

    //주문 가능한 상태인지 - history id가 있는지 확인
    public String isOrder(){
        if (history_id != null){
            // 현재 진행중인 주문이 있음
            return history_id;
        }
        // 진행중인 주문이 없을 경우 빈 문자열 반환
        return "";
    }

//    public void addMetaInfoData(MetaInfoData metaInfoData){
//        metaInfoDataList.add(metaInfoData);
//    }

    public void addMetaInfoData(MetaInfoData metaInfoData) {
        if (metaInfoDataList == null) {
            metaInfoDataList = new ArrayList<>();
        }
        metaInfoDataList.add(metaInfoData);
        Log.d("MyApp", "Data_addMetaInfoData: "+metaInfoDataList.toString());
    }

    //sNum을 알 때 index의 위치를 알 때 f_num 값 return 하기
    public int getFNumBySNum(int sNum, int index) {
        for (MetaInfoData metaInfoData : metaInfoDataList) {
            if (metaInfoData.getsNum() == sNum) {
                int[] fNumList = metaInfoData.getfNumList();
                if (index >= 0 && index < fNumList.length) {
                    return fNumList[index];
                }
            }
        }
        return -1; // 해당하는 sNum이나 index를 찾을 수 없을 때 예외 처리
    }

    //s_num과 f_num으로 장바구니에 있는 음식 찾기
//    public boolean findFood(int s_num, int f_num){
//
//        for(Cart cart : cartList){
//            if((cart.s_num == s_num) && (cart.f_num == f_num)) {
//                return true;
//            }
//        }
//        return false;
//    }
}