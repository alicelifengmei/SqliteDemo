package com.example.administrator.sqlitedemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.sqlitedemo.R;
import com.example.administrator.sqlitedemo.dbmanager.DbHelper;
import com.example.administrator.sqlitedemo.model.ContactModel;

/**
 * Created by lifengmei on 2016/10/18 16:50.
 */
public class DeleteAndUpdateContactActivity extends Activity {

    private EditText etName;
    private EditText etPhoneNum;
    private Button btnUpdate;
    private Button btnDelete;
    private ContactModel contactModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_update_contact);
        etName = (EditText) findViewById(R.id.delete_update_contact_et_name);
        etPhoneNum = (EditText) findViewById(R.id.delete_update_contact_et_phonenum);
        btnUpdate = (Button) findViewById(R.id.delete_update_contact_btn_update);
        btnDelete = (Button) findViewById(R.id.delete_update_contact_btn_delete);
        contactModel = (ContactModel) getIntent().getSerializableExtra("contactModel");
        etName.setText(contactModel.getName());
        etPhoneNum.setText(contactModel.getPhoneNum());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(DeleteAndUpdateContactActivity.this);
                String name = etName.getText().toString();
                String phoneNum = etPhoneNum.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phoneNum)) {
                    contactModel.setName(name);
                    contactModel.setPhoneNum(phoneNum);
                    int rowNum = dbHelper.update(contactModel);
                    if(rowNum > -1){
                        setResult(RESULT_OK);
                        finish();
                    }else{
                        Toast.makeText(DeleteAndUpdateContactActivity.this, "联系人修改失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DeleteAndUpdateContactActivity.this, "姓名和电话号码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(DeleteAndUpdateContactActivity.this);
                int id = contactModel.getId();
                int rowNum = dbHelper.deteleById(id);
                if(rowNum>-1){
                    setResult(RESULT_OK);
                    finish();
                }else{
                    Toast.makeText(DeleteAndUpdateContactActivity.this, "联系人删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context,ContactModel contactModel) {

        Intent intent = new Intent(context, DeleteAndUpdateContactActivity.class);
        intent.putExtra("contactModel",contactModel);
        return intent;
    }
}
