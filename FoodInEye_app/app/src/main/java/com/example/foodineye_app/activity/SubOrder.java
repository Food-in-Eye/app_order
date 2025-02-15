package com.example.foodineye_app.activity;

//하위 리사이클러뷰 가게별 음식들 정의
public class SubOrder {
    private String foodId;
    private String foodName;
    private int foodtotalPrice;
    private int foodCount;

    public SubOrder(String foodId, String foodName, int foodPrice, int foodCount) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodtotalPrice = foodPrice;
        this.foodCount = foodCount;
    }

    public String toString(){return "foodId: "+ foodId+ " foodName: "+foodName+" foodPrice: "+foodtotalPrice +"foodCount"+foodCount;}

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodPrice() {
        return foodtotalPrice*foodCount;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodtotalPrice = foodPrice;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

}
