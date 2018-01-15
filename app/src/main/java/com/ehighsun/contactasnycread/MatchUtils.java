package com.ehighsun.contactasnycread;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class MatchUtils {

    /**
     * 是否是身份证号码
     *
     * @param num
     * @return
     */
    public static boolean isIdentityNumer(String num) {
        boolean isIdentityNum = false;
        Pattern pattern = Pattern.compile("(^\\d{17}[0-9a-zA-Z]$)|(^\\d{15}$)");
        Matcher matcher = pattern.matcher(num);
        isIdentityNum = matcher.matches();
        return isIdentityNum;
    }

    /**
     * 是否是电话号码
     *
     * @param num
     * @return
     */
    public static boolean isPhoneNumber(String num) {
        boolean isNumber = false;
        Pattern pattern = Pattern.compile("^[1][0-9]{10}$");
        Matcher matcher = pattern.matcher(num);
        isNumber = matcher.matches();
        return isNumber;
    }


    /**
     * 校验登录密码是否为6-20位数字和字母组成
     *
     * @return
     */
    public static boolean checkPsw(String psw) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
        Matcher m = p.matcher(psw);
        boolean b = m.matches();
        return b;
    }


    public static boolean isEmePhoneNum(String s){
        String num = s.replaceAll("\\D", "");
        if(num.indexOf("86")==0 && num.length()==13 && isPhoneNumber(num.substring(2,num.length()))){
            return true;
        }
        return isPhoneNumber(num);
    }

    public static boolean checkIsUnLawful(String[] input){
        if (input != null && input.length> 0){
            for (int i = 0; i < input.length; i++) {
                String length = input[i];
                int codePoint;
                if (!TextUtils.isEmpty(length)){
                    for (int j = 0; j < length.length(); j += Character.charCount(codePoint)) {
                        codePoint = length.codePointAt(j);
                        if ((codePoint >= 0xd800 && codePoint <= 0xdfff) || codePoint > 0x10ffff) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean checkStringEncode (String input){
        int codePoint;
        if(!TextUtils.isEmpty(input)){
            for (int j = 0; j < input.length(); j += Character.charCount(codePoint)) {
                codePoint = input.codePointAt(j);
                if ((codePoint >= 0xd800 && codePoint <= 0xdfff) || codePoint > 0x10ffff) {
                    return false;
                }
            }

        }
        return true;
    }

}
