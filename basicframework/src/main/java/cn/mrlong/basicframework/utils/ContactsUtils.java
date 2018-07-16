package cn.mrlong.basicframework.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by BINGO on 2017/09/28.
 * 操作联系人
 */
public class ContactsUtils {
    public static boolean AddContacts(Context context, String name, String phone, String email) {
        boolean isOk = false;
        try {
            //插入raw_contacts表，并获取_id属性
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = context.getContentResolver();
            ContentValues values = new ContentValues();
            long contact_id = ContentUris.parseId(resolver.insert(uri, values));
            //插入data表
            uri = Uri.parse("content://com.android.contacts/data");
            //add Name
            values.put("raw_contact_id", contact_id);
            values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/name");
            values.put("data1", name);
            resolver.insert(uri, values);
            values.clear();
            //add Phone
            values.put("raw_contact_id", contact_id);
            values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
            values.put("data1", phone);
            resolver.insert(uri, values);
            values.clear();
            //add email
            values.put("raw_contact_id", contact_id);
            values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/email_v2");
            values.put("data1", email);
            resolver.insert(uri, values);
            isOk = true;
        }catch (Exception e){
            isOk = false;
            e.printStackTrace();
        }finally {
            return isOk;
        }
    }

    //读取通讯录的全部的联系人
    //需要先在raw_contact表中遍历id，并根据id到data表中获取数据
    public static void ReadAll(Context context) {
        Uri uri = Uri.parse("content://com.android.contacts/contacts");    //访问raw_contacts表
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, null, null, null);    //获得_id属性
        while (cursor.moveToNext()) {
            StringBuilder buf = new StringBuilder();
            int id = cursor.getInt(0);//获得id并且在data中寻找数据
            buf.append("id=" + id);
            uri = Uri.parse("content://com.android.contacts/contacts/" + id + "/data");    //如果要获得data表中某个id对应的数据，则URI为content://com.android.contacts/contacts/#/data
            Cursor cursor2 = resolver.query(uri, new String[]{ContactsContract.Data.DATA1, ContactsContract.Data.MIMETYPE}, null, null, null);    //data1存储各个记录的总数据，mimetype存放记录的类型，如电话、email等
            while (cursor2.moveToNext()) {
                String data = cursor2.getString(cursor2.getColumnIndex("data1"));
                if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/name")) {        //如果是名字
                    buf.append(",name=" + data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")) {    //如果是电话
                    buf.append(",phone=" + data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")) {    //如果是email
                    buf.append(",email=" + data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")) {    //如果是地址
                    buf.append(",address=" + data);
                } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")) {    //如果是组织
                    buf.append(",organization=" + data);
                }
            }
            String str = buf.toString();
            Log.i("Contacts", str);
        }
    }

    public static void delete(Context context, String name) {
        try {
            //根据姓名求id
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, "display_name=?", new String[]{name}, null);
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0);
                //根据id删除data中的相应数据
                resolver.delete(uri, "display_name=?", new String[]{name});
                uri = Uri.parse("content://com.android.contacts/data");
                resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPeople(Context context, String mNumber) {
        String name = "";
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                                /*ContactsContract.CommonDataKinds.Phone.NUMBER*/};
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = '" + mNumber + "'",
                null,
                null);
        if (cursor == null) {
            return "";
        }
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            name = cursor.getString(nameFieldColumnIndex);
            break;
        }
        if (cursor != null) {
            cursor.close();
        }
        return name;
    }

    public void update(Context context)throws Exception {
        int id = 1;
        String phone = "999999";
        Uri uri = Uri.parse("content://com.android.contacts/data");//对data表的所有数据操作
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("data1", phone);
        resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/phone_v2",id+""});
    }
}