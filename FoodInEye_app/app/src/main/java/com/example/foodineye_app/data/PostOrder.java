package com.example.foodineye_app.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostOrder {

    @SerializedName("u_id")
    @Expose
    String u_id;
    @SerializedName("total_price")
    @Expose
    int total_price;
    @SerializedName("content")
    @Expose
    List<StoreOrder> content;

    public PostOrder(String u_id, int total_price, List<StoreOrder> content) {
        this.u_id = u_id;
        this.total_price = total_price;
        this.content = content;
    }

    public String toString(){return "u_id: "+ u_id+ " total_price: "+total_price+ " content: "+content.toString();}

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public List<StoreOrder> getContent() {
        return content;
    }

    public void setContent(List<StoreOrder> content) {
        this.content = content;
    }

    //StoreOrder class
    public static class StoreOrder {

        @SerializedName("s_id")
        @Expose
        String s_id;
        @SerializedName("s_name")
        @Expose
        String s_name;
        @SerializedName("m_id")
        @Expose
        String m_id;
        @SerializedName("f_list")
        @Expose
        List<FoodCount> f_list;

        public StoreOrder(String s_id, String s_name, String m_id, List<FoodCount> f_list) {
            this.s_id = s_id;
            this.s_name = s_name;
            this.m_id = m_id;
            this.f_list = f_list;
        }

        public String toString(){return "s_id: "+ s_id+ " m_id: "+m_id+ " f_list: "+f_list.toString();}

        public String getS_id() {
            return s_id;
        }

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        public String getM_id() {
            return m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
        }

        public List<FoodCount> getF_list() {
            return f_list;
        }

        public void setF_list(List<FoodCount> f_list) {
            this.f_list = f_list;
        }

        //FoodCount class
        public static class FoodCount{

            @SerializedName("f_id")
            @Expose
            String f_id;
            @SerializedName("f_name")
            @Expose
            String f_name; // =m_name
            @SerializedName("count")
            @Expose
            int count;
            @SerializedName("price")
            @Expose
            int price;

            public FoodCount(String f_id, String f_name, int count, int price) {
                this.f_id = f_id;
                this.f_name = f_name;
                this.count = count;
                this.price = price;
            }

            public String toString(){return "f_id: "+ f_id+ " count: "+count +" price: "+price;}
        }
    }
}
