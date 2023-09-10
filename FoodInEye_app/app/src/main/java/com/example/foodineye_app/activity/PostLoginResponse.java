package com.example.foodineye_app.activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostLoginResponse {

    @SerializedName("u_id")
    @Expose
    public String user_id; //회원 고유의 ID

    @SerializedName("token_type")
    @Expose
    public String token_type; //bearer

    @SerializedName("A_Token")
    @Expose
    public String A_Token;

    @SerializedName("R_Token")
    @Expose
    public String R_Token;

    @SerializedName("camera")
    @Expose
    public int eye_permssion; //null 0, true 1, false 2

    public String getUser_id() {
        return user_id;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getA_Token() {
        return A_Token;
    }

    public String getR_Token() {
        return R_Token;
    }

    public String isEye_permssion() {
        if(eye_permssion == 1){
            return "true";
        } else if (eye_permssion == 2) {
            return "false";
        }else if (eye_permssion == 0){
            return "null";
        }else{
            return "null";
        }
    }

    @Override
    public String toString() {
        return "PostLoginResponse{" +
                "user_id='" + user_id + '\'' +
                ", token_type='" + token_type + '\'' +
                ", A_Token='" + A_Token + '\'' +
                ", R_Token='" + R_Token + '\'' +
                ", eye_permssion=" + eye_permssion +
                '}';
    }
}
