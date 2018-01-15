package com.ehighsun.contactasnycread;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on 2017-08-09.
 */

public class ContactsGetUtil {
    public static List<Contacts> getPhoneNum(Context context){
        List<Contacts> phones = new ArrayList<>();
        List<Contacts> list1 = new ArrayList<>();
        List<Contacts> list2 = new ArrayList<>();
        String sortKey="sort_key";
        if(Build.VERSION.SDK_INT>19){
            sortKey="phonebook_label";
        }
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"display_name",sortKey,ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, "sort_key");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Contacts contact = new Contacts();
                String phoneName = cursor.getString(0);
                String key = cursor.getString(1);
                String trim = key.trim();
                String phoneNum = cursor.getString(2);
                if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(phoneName)) continue;
                if (!MatchUtils.isEmePhoneNum(phoneNum)) continue;
                if (!MatchUtils.checkStringEncode(phoneName)) continue;
                if(TextUtils.isEmpty(trim) || !isCract(trim.substring(0,1))){
                    contact.setSortKey("#");
                    contact.setPhontName(phoneName);
                    contact.setPhoneNum(phoneNum);
                    list2.add(contact);
                }else {
                    contact.setSortKey(trim.substring(0,1).toUpperCase(Locale.CHINA));
                    contact.setPhontName(phoneName);
                    contact.setPhoneNum(phoneNum);
                    list1.add(contact);
                }
            }
            phones.addAll(list1);
            phones.addAll(list2);
            cursor.close();
        }
        return phones;
    }

    private static boolean isCract(String s){
        String reg = "[a-zA-Z]";
        boolean isCract = s.matches(reg);
        return isCract;
    }




}
