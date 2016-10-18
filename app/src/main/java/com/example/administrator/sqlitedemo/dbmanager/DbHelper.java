package com.example.administrator.sqlitedemo.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.sqlitedemo.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifengmei on 2016/10/17 16:44.
 */
public class DbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "demo_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_CONTRACT = "contract";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_PHONENUM = "phoneNum";
    private static final String CREATE_TABLE = "create table "+TABLE_CONTRACT+" ("+KEY_ID +" integer primary key autoincrement,"+KEY_NAME+" text not null,"+KEY_TYPE+" integer,"+KEY_PHONENUM+" text not null)";

    public DbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, DB_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            if(oldVersion == 1){

            }else if(oldVersion == 2){

            }
        }
    }

    /***
     * 获取联系人列表
     * @return
     */
    public List<ContactModel> getAllContacts(){
        List<ContactModel> contactModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();//TODO 这里拿到的db都是同一个db吗
        if(db != null){
            Cursor cursor = db.query(TABLE_CONTRACT,null,null,null,null,null,null);
            if(cursor != null){
                ContactModel contactModel;
                while(cursor.moveToNext()){
                    int id = cursor.getColumnIndex(KEY_ID);
                    int name = cursor.getColumnIndex(KEY_NAME);
                    int phoneNum = cursor.getColumnIndex(KEY_PHONENUM);
                    int type = cursor.getColumnIndex(KEY_TYPE);
                    contactModel = new ContactModel();
                    contactModel.setId(cursor.getInt(id));
                    contactModel.setName(cursor.getString(name));
                    contactModel.setPhoneNum(cursor.getString(phoneNum));
                    contactModel.setType(cursor.getInt(type));
                    contactModels.add(contactModel);
                }
                cursor.close();
            }
            db.close();//TODO 这里随手关闭数据库会导致线程安全问题吗？---要看每个方法拿到的db是不是同一个db
        }
        return contactModels;
    }

    /***
     * 根据姓名获取单个联系人
     * @param name
     * @return
     */
    public List<ContactModel> getListByName(String name){
        List<ContactModel> contactModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null){
            Cursor cursor = db.query(TABLE_CONTRACT,new String[]{KEY_ID,KEY_NAME,KEY_PHONENUM,KEY_TYPE},KEY_NAME+"=?",new String[]{name},null,null,null);
            if(cursor != null){
                ContactModel contactModel;
                int id = cursor.getColumnIndex(KEY_ID);//最好先将列索引在循环外获得
                int nameI = cursor.getColumnIndex(KEY_NAME);
                int phoneNum = cursor.getColumnIndex(KEY_PHONENUM);
                int type = cursor.getColumnIndex(KEY_TYPE);
                contactModel = new ContactModel();
                while (cursor.moveToNext()){
                    contactModel.setId(cursor.getInt(id));
                    contactModel.setName(cursor.getString(nameI));
                    contactModel.setPhoneNum(cursor.getString(phoneNum));
                    contactModel.setType(cursor.getInt(type));
                    contactModels.add(contactModel);
                }
                cursor.close();
            }
            db.close();
        }
        return contactModels;
    }
    /***
     * 根据姓名获取单个联系人
     * @param name
     * @return
     */
    public ContactModel getByName(String name){
        ContactModel contactModel = new ContactModel();
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null){
            Cursor cursor = db.query(TABLE_CONTRACT,new String[]{KEY_ID,KEY_NAME,KEY_PHONENUM,KEY_TYPE},KEY_NAME+"=?",new String[]{name},null,null,null);
            if(cursor != null){
                int id = cursor.getColumnIndex(KEY_ID);//最好先将列索引在循环外获得
                int nameI = cursor.getColumnIndex(KEY_NAME);
                int phoneNum = cursor.getColumnIndex(KEY_PHONENUM);
                int type = cursor.getColumnIndex(KEY_TYPE);
                while (cursor.moveToNext()){
                    contactModel.setId(cursor.getInt(id));
                    contactModel.setName(cursor.getString(nameI));
                    contactModel.setPhoneNum(cursor.getString(phoneNum));
                    contactModel.setType(cursor.getInt(type));
                }
                cursor.close();
            }
            db.close();
        }
        return contactModel;
    }

    /**
     * 新增一个联系人
     * @param contract
     * @return
     */
    public long insert(ContactModel contract){//单个字段传递和 整体一个model传递的优缺点
        long rowId = -1;
        if(contract == null){
            return rowId;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();//contentvalue字段最好不多于8，否则扩充
            values.put(KEY_NAME,contract.getName());
            values.put(KEY_PHONENUM,contract.getPhoneNum());
            values.put(KEY_TYPE,contract.getType());
            rowId = db.insert(TABLE_CONTRACT,null,values);
            db.close();
        }
        return rowId;
    }

    /**
     * 更新联系人修改信息
     * @param contract
     * @return
     */
    public int update(ContactModel contract){
        int rowNum = -1;
        if(contract == null){
            return rowNum;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(KEY_NAME,contract.getName());
            values.put(KEY_PHONENUM,contract.getPhoneNum());
            values.put(KEY_TYPE,contract.getType());
            rowNum = db.update(TABLE_CONTRACT,values,KEY_ID + "=?",new String[]{contract.getId()+""});
            db.close();
        }
        return rowNum;
    }

    /***
     * 根据id删除联系人
     * @param id
     * @return
     */
    public int deteleById(int id){
        int rowNum = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        if(db != null){
            rowNum = db.delete(TABLE_CONTRACT,KEY_ID + "=?",new String[]{id+""});
            db.close();
        }
        return rowNum;
    }
}
