package com.example.foodineye_app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodineye_app.R;
import com.example.foodineye_app.data.GetStoreList;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {

    private Context mContext;
    private List<GetStoreList.Stores> storesList;

    private int[] itemOLocation;

    public StoreAdapter(Context context, List<GetStoreList.Stores> storesList) {
        mContext = context;
        this.storesList = storesList;
    }

    @NonNull
    @Override
    public StoreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_store_recyclerview, parent, false);

        if(viewType == 0){
            itemOLocation = new int[2];
            view.getLocationOnScreen(itemOLocation);
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.MyViewHolder holder, int position) {

        //Bind the data for each item in the list
        GetStoreList.Stores store = storesList.get(position);

        //image 불러오기
        String imageUrl = "https://foodineye2.s3.ap-northeast-2.amazonaws.com/" + store.getImg_key();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .circleCrop()
                .into(holder.storeImage);


        holder.storeName.setText(store.getName());

        //Set the background color for each item in the list
        int color = store.isOpen() ? Color.WHITE : Color.LTGRAY;
        holder.itemView.setBackgroundColor(color);
        // Enable or disable click on the item based on store.isOpen()
        if (store.isOpen()) {
            holder.itemView.setClickable(true);
            holder.itemView.setEnabled(true);
        } else {
            holder.itemView.setClickable(false);
            holder.itemView.setEnabled(false);
        }

        //store ID 값 받아오기
        String storeId = store.get_id();
        String menuId = store.getM_id();
        int storeNum = store.getS_num();

        //Click Store Detail, intent에 가게 "_id" 전달, MenuActivity와 연결
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("_id", storeId);
                intent.putExtra("m_id", menuId);
                Log.d("StoreAdapter: " , menuId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (context instanceof Activity) {
                    ((Activity) context).startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    ((Activity) context).finish();

                } else {
                    context.startActivity(intent);
                }
            }
        });

        //-----------------------------------------------------------------------------------------
        // 0번째 아이템인 경우
        if (position == 0 && itemOLocation != null) {
            View itemView = holder.itemView;

            // 아이템 뷰의 위치와 크기를 가져오기 위해 ViewTreeObserver를 사용
            itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    int[] itemLocation = new int[2];
                    itemView.getLocationOnScreen(itemLocation);

                    int topOffset = itemOLocation[1]; // 아이템 뷰의 위쪽 좌표
                    int left = itemLocation[0]; // 아이템 뷰의 왼쪽 좌표
                    int top = itemLocation[1] - topOffset; // 아이템 뷰의 위쪽 좌표 - topOffset
                    int right = left + itemView.getWidth(); // 아이템 뷰의 오른쪽 좌표
                    int bottom = top + itemView.getHeight(); // 아이템 뷰의 아래쪽 좌표

                    // 결과 출력
                    Log.d("location", "Item 0 - Left: " + left);
                    Log.d("location", "Item 0 - Top: " + top);
                    Log.d("location", "Item 0 - Right: " + right);
                    Log.d("location", "Item 0 - Bottom: " + bottom);
                }
            });
        }

        // 1번째 아이템인 경우
        if (position == 1 && itemOLocation != null) {
            View itemView = holder.itemView;

            // 아이템 뷰의 위치와 크기를 가져오기 위해 ViewTreeObserver를 사용
            itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    int[] itemLocation = new int[2];
                    itemView.getLocationOnScreen(itemLocation);

                    int topOffset = itemOLocation[1]; // 아이템 뷰의 위쪽 좌표
                    int left = itemLocation[0]; // 아이템 뷰의 왼쪽 좌표
                    int top = itemLocation[1] - topOffset; // 아이템 뷰의 위쪽 좌표 - topOffset
                    int right = left + itemView.getWidth(); // 아이템 뷰의 오른쪽 좌표
                    int bottom = top + itemView.getHeight(); // 아이템 뷰의 아래쪽 좌표

                    // 결과 출력
                    Log.d("location", "Item 1 - Left: " + left);
                    Log.d("location", "Item 1 - Top: " + top);
                    Log.d("location", "Item 1 - Right: " + right);
                    Log.d("location", "Item 1 - Bottom: " + bottom);
                }
            });
        }
        //-----------------------------------------------------------------------------------------
    }

    @Override
    public int getItemCount() {
        if(storesList==null) return 0;
        return storesList.size();
    }

    //ViewHolder 정의
    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView storeImage;
        TextView storeName;
        Button storeBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            storeImage = (ImageView)itemView.findViewById(R.id.storelist_img);
            storeName = (TextView)itemView.findViewById(R.id.store_name);
            storeBtn = (Button)itemView.findViewById(R.id.sBtn);
        }
    }
}