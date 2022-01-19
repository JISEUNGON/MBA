package com.mba.busapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// 버스 경유표 Adapter
public class BussList extends BaseAdapter {

    ArrayList<BussPass> buspass = new ArrayList<>();
    @Override
    public int getCount() {
        return buspass.size();
    }

    @Override
    public Object getItem(int position) {
        return buspass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Context c = viewGroup.getContext();
        if(view == null){
            LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.bus_pass_layout,viewGroup,false);
        }
        TextView tv = view.findViewById(R.id.tv);
        ImageView iv = view.findViewById(R.id.imageView);

        tv.setTextColor(Color.BLACK);


        BussPass b =buspass.get(i);

        tv.setText(b.getName());
        iv.setImageDrawable(b.getD());

        /*
        // 클릭 비활성화
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        */

        return view;
    }

    public void addBussPass(Drawable d, String name){
        BussPass b = new BussPass();

        b.setD(d);
        b.setName(name);

        buspass.add(b);
    }

    public void removeBussPass(int i){
        buspass.remove(i);
    }

    public void removeAllBus(){

        // 모두삭제
        buspass.clear();
        buspass = new ArrayList<>();
        notifyDataSetChanged();


    }

    // 자세히 보기 클릭했는지 확인
    public boolean checkList(int i){

        if(buspass.get(i).getName() == "자세히 보기"){
            return true;
        }
        return false;
    }
}
