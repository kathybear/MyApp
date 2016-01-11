package com.ypf.myapp.utils;

import android.text.TextUtils;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ypf on 2016/1/11.
 */
public class StringUtil {
    //判断手机号码
    public static boolean isMobileNO(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }

        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isEmail(String strEmail) {
        if (strEmail == null) {
            return false;
        }

        String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s) || s.trim().isEmpty() || s.equals("null")
                || s.equals("NULL") || s.equals(JSONObject.NULL);
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0;) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString
     *            16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] toByteArray(String hexString) {
        if (isEmpty(hexString))
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static final String des_password = "796e13bfbf6b8001";

    public static void main(String[] args) throws Exception {

        byte[] b = toByteArray(des_password);
        String s = "I love you.";
        System.out.println(s);
        byte[] encryptedData = encrypt(s.getBytes("UTF-8"), b);
        String tmp = byte2hex(encryptedData);
        System.out.println(tmp);
        byte[] decryptedData = decrypt(toByteArray(tmp), b);
        System.out.println(new String(decryptedData, "UTF-8"));
    }

    public static String byte2hex(byte[] b) {
        String des = "";
        String tmp;
        for (byte aB : b) {
            tmp = (Integer.toHexString(aB & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static byte[] encrypt(byte[] data, byte[] b) throws Exception {
        DESKeySpec dks = new DESKeySpec(b);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        SecureRandom sr = new SecureRandom();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, byte[] b) throws Exception {
        DESKeySpec dks = new DESKeySpec(b);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        SecureRandom sr = new SecureRandom();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        return cipher.doFinal(data);
    }

    static byte[] getKey() throws Exception {
        SecureRandom sr = new SecureRandom();
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        kg.init(sr);
        SecretKey key = kg.generateKey();
        return key.getEncoded();
    }
}
