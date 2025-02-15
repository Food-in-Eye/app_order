package com.example.foodineye_app.activity;

import java.io.Serializable;

public class Cart implements Serializable {

    String s_id;
    String s_name;
    String m_id;
    String f_id;
    String m_name;
    int m_price;
    String m_imageKey;
    int m_count;

    public int s_num;
    int f_num;

    public Cart(String s_id, String s_name, String m_id, String f_id, String m_name, int m_price, String m_imageKey, int s_num, int f_num) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.m_id = m_id;
        this.f_id = f_id;
        this.m_name = m_name;
        this.m_price = m_price;
        this.m_imageKey = m_imageKey;
        m_count = 1;
        this.s_num = s_num;
        this.f_num = f_num;
    }

    public String toString(){return "s_id: "+ s_id+ " m_id: "+m_id+" f_id: "+f_id +"m_count"+m_count;}

    public String getS_id() {  return s_id;  }

    public void setS_id(String s_id) {  this.s_id = s_id;  }

    public String getS_name() {     return s_name;  }

    public void setS_name(String s_name) {    this.s_name = s_name;  }

    public String getM_id() {  return m_id;  }

    public void setM_id(String m_id) { this.m_id = m_id; }

    public String getF_id() {   return f_id;  }

    public void setF_id(String f_id) {  this.f_id = f_id;  }

    public String getM_name() {   return m_name;  }

    public void setM_name(String m_name) {   this.m_name = m_name;  }

    public int getM_price() {    return m_price;  }

    public void setM_price(int m_price) {   this.m_price = m_price;  }

    public String getM_imageKey() {  return m_imageKey;  }

    public void setM_imageKey(String m_imageKey) {  this.m_imageKey = m_imageKey; }

    public int getM_count() {    return m_count;  }

    public void setM_count(int m_count) {    this.m_count = m_count;  }

    public void increase_count() { this.m_count += 1; }

    public void decrease_count() { this.m_count -= 1; }

    public int getM_TotalPrice(){
        return m_price*m_count;
    }

    public int getS_num() {
        return s_num;
    }

    public int getF_num() {
        return f_num;
    }
}
