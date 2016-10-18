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
public class AddContactActivity extends Activity {

    private EditText etName;
    private EditText etPhoneNum;
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        etName = (EditText) findViewById(R.id.add_contact_et_name);
        etPhoneNum = (EditText) findViewById(R.id.add_contact_et_phonenum);
        btnAdd = (Button) findViewById(R.id.add_contact_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(AddContactActivity.this);
                String name = etName.getText().toString();
                String phoneNum = etPhoneNum.getText().toString();
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phoneNum)){
                    ContactModel contactModel = new ContactModel();
                    contactModel.setName(name);
                    contactModel.setPhoneNum(phoneNum);
                    dbHelper.insert(contactModel);
                    setResult(RESULT_OK);
                    finish();
                }else{
                    Toast.makeText(AddContactActivity.this,"姓名和电话号码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context){

        Intent intent = new Intent(context,AddContactActivity.class);
        return intent;
    }
}
