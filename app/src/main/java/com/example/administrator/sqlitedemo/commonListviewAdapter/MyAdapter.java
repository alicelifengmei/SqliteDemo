package com.example.administrator.sqlitedemo.commonListviewAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by lifengmei on 2016/10/21 14:51.
 */
public class MyAdapter extends BaseAdapter{
    private List<String> datalist;

    public void setData(List<String> datalist){
        this.datalist = datalist;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
