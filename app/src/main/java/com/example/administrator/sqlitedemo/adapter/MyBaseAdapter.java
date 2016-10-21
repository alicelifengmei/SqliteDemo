package com.example.administrator.sqlitedemo.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lifengmei on 2016/10/21 10:22.
 */
public abstract class MyBaseAdapter<T> extends android.widget.BaseAdapter{

    private List<T> datalist;

    public void setData(List<T> datalist){
        this.datalist = datalist;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datalist == null ? 0 : datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist == null ? position : datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        return setView(convertView,position,datalist);
    }

    public abstract View setView(View convertView,int position,List<T> datalist) ;
//    {
//        ViewHolder holder;
//        if(convertView == null){
//            holder = new ViewHolder();
//            //设置view
//            convertView.setTag(holder);
//        }else{
//            holder = (ViewHolder) convertView.getTag();
//        }
//        //设置数据
//        return convertView;
//    }

    class ViewHolder{

    }
}
