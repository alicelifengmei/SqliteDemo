package com.example.administrator.sqlitedemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.sqlitedemo.R;
import com.example.administrator.sqlitedemo.widget.DividerGridItemDecoration;
import com.example.administrator.sqlitedemo.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifengmei on 2016/10/20 16:04.
 */
public class RecycleViewActivity extends Activity{

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        initData();
        RecyclerView rc = (RecyclerView) findViewById(R.id.recycleview_rv);
        //仿listview
//        rc.setLayoutManager(new LinearLayoutManager(this));
//        rc.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        //仿gridview
//        rc.setLayoutManager(new GridLayoutManager(this,4));
//        rc.addItemDecoration(new DividerGridItemDecoration(this));
        //瀑布流竖着---所有的列会均分屏幕宽度
        rc.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        rc.addItemDecoration(new DividerGridItemDecoration(this));
        //横着--奇怪的是 所有的行会均匀分布到整个屏幕高度
//        rc.setLayoutManager(new StaggeredGridLayoutManager(7,StaggeredGridLayoutManager.HORIZONTAL));
//        rc.addItemDecoration(new DividerGridItemDecoration(this));
        rc.setAdapter(new RecycleViewAdapter());
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(RecycleViewActivity.this).inflate(R.layout.item_recycleview,parent,false));
            return holder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv;
            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.item_recycleview_tv_name);
            }
        }
    }
}
