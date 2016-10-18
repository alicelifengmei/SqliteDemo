package com.example.administrator.sqlitedemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.sqlitedemo.R;
import com.example.administrator.sqlitedemo.dbmanager.DbHelper;
import com.example.administrator.sqlitedemo.model.ContactModel;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private Button btnAdd;
    private Button btnQuery;
    private ListView lv;

    private List<ContactModel> contactModels;
    private ContactsAdapter contactsAdapter;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        btnAdd = (Button) findViewById(R.id.main_btn_add);
        btnQuery = (Button) findViewById(R.id.main_btn_query);
        lv = (ListView) findViewById(R.id.main_lv);
        contactsAdapter = new ContactsAdapter();
        lv.setAdapter(contactsAdapter);
    }

    private void initData() {
        dbHelper = new DbHelper(this);
        contactModels = dbHelper.getAllContacts();
        if(contactModels != null){
            contactsAdapter.setData(contactModels);
        }
    }

    private void initListener() {
        btnAdd.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.main_btn_add:
                startActivityForResult(AddContactActivity.newIntent(this),1001);
                break;
            case R.id.main_btn_query:
                LinearLayout dialogView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog_query,null);
                final AlertDialog dialog = new AlertDialog.Builder(this)
                        .setView(dialogView)
                        .create();
                dialog.show();
                final EditText etName = (EditText) dialogView.findViewById(R.id.query_et_name);
                Button btnQuery = (Button) dialogView.findViewById(R.id.query_btn_query);
                final ListView lv = (ListView) dialogView.findViewById(R.id.query_lv);
                final ContactsAdapter resultAdapter = new ContactsAdapter();
                lv.setAdapter(resultAdapter);
                btnQuery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<ContactModel> contactModels = dbHelper.getListByName(etName.getText().toString());
                        if(contactModels != null && contactModels.size()>0){
                            etName.setVisibility(View.GONE);
                            lv.setVisibility(View.VISIBLE);
                            resultAdapter.setData(contactModels);
                        }
                    }
                });
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        startActivityForResult(DeleteAndUpdateContactActivity.newIntent(MainActivity.this, contactModels.get(i)), 1002);
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivityForResult(DeleteAndUpdateContactActivity.newIntent(this,contactModels.get(i)),1002);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 1001:
                case 1002:
                    if(dbHelper == null){
                        dbHelper = new DbHelper(this);
                    }
                    contactModels = dbHelper.getAllContacts();
                    contactsAdapter.setData(contactModels);
                    break;
            }
        }
    }

    private class ContactsAdapter extends BaseAdapter{
        private List<ContactModel> datalist;

        public void setData(List<ContactModel> data){
            this.datalist = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return datalist == null ? 0: datalist.size();
        }

        @Override
        public Object getItem(int i) {
            return datalist == null? i:datalist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_main_contact,null);
                holder.ivAvatar = (ImageView) convertView.findViewById(R.id.item_contact_iv_avatar);
                holder.tvName = (TextView) convertView.findViewById(R.id.item_contact_tv_name);
                holder.tvPhoneNum = (TextView) convertView.findViewById(R.id.item_contact_tv_phonenum);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            ContactModel contactModel = datalist.get(position);
            if(contactModel != null){
                String name = contactModel.getName();
                String phoneNum = contactModel.getPhoneNum();
                if(!TextUtils.isEmpty(name)){
                    holder.tvName.setText(name);
                }
                if(!TextUtils.isEmpty(phoneNum)){
                    holder.tvPhoneNum.setText(phoneNum);
                }
            }
            return convertView;
        }
    }

    private class ViewHolder{

        private ImageView ivAvatar;
        private TextView tvName;
        private TextView tvPhoneNum;
    }
}
