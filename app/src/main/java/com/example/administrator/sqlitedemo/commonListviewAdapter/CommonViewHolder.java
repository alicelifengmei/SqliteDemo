package com.example.administrator.sqlitedemo.commonListviewAdapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通用的ViewHolder
 * Created by lifengmei on 2016/10/21 14:24.
 */
public class CommonViewHolder {
    private SparseArray<View> views;
    private View mConvertView;

    private CommonViewHolder(Context context,ViewGroup parent,int layoutId){
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);

    }

    public static CommonViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId){
        if(convertView==null){
            return new CommonViewHolder(context,parent,layoutId);
        }
        return (CommonViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }

    public View getConvertView(){
        return mConvertView;
    }
}
