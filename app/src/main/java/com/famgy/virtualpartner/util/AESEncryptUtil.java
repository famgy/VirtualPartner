package com.famgy.virtualpartner.util;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptUtil {
    /**
     * 对字符串解密函数
     * @param paramLong     Long常量15485863L
     * @param paramString   要解密的密文
     * @param ANDROIDID     adnroid_id的值
     * @return              解密后的明文
     */
    public static String encrypt(Long paramLong,String paramString,String ANDROIDID){
        if(paramLong == null | paramString.equals("") | paramString == null | ANDROIDID == null){
            return "参数错误";
        }
        byte[] bb = operationLong(paramLong,ANDROIDID);

        return Base64Encrypt(bb,paramString);
    }

    /**
     * 生成秘钥函数
     * @param paramLong Long常量15485863L
     * @param androidID adnroid_id的值
     * @return          生成的秘钥的byte数组
     */
    private static byte[] operationLong(Long paramLong , String androidID){
        String str2 = androidID;
        String str1 = str2;
        if (str2 == null) {
            str1 = "";
        }
        byte strByte = (byte)str1.hashCode();
        return getAIDAndLong(strByte, paramLong);
    }

    /**
     * 通过android_id的hashcode的byte值与Long常量15485863L生成秘钥
     * @param paramByte android_id的hashcode的byte值
     * @param paramLong Long常量15485863L
     * @return          生成的秘钥的byte数组
     */
    private static byte[] getAIDAndLong(byte paramByte, long paramLong){
        int k = 0;
        byte[] arrayOfByte = new byte[16];
        arrayOfByte[0] = paramByte;
        arrayOfByte[1] = ((byte)(paramByte - 71));
        arrayOfByte[2] = ((byte)(paramByte - 71 - 71));

        int i = 3;
        while (i < 16)
        {
            arrayOfByte[i] = ((byte)(arrayOfByte[(i - 3)] ^ arrayOfByte[(i - 2)] ^ 0xFFFFFFB9 ^ i));
            i += 1;
        }
        i = -7;
        arrayOfByte = (byte[])arrayOfByte.clone();
        int i2 = arrayOfByte.length;
        long l = paramLong;

        if (paramLong < 2L)
        {

            l = paramLong;
            if (paramLong > -2L) {
                l = -313187L + 13819823L * paramLong;
            }
        }

        int j = 0;
        while (j < i2)
        {
            int m = i2 - 1 & k + 1;
            paramLong = arrayOfByte[m] * l + i;

            int n = (byte)(int)(paramLong >> 32);
            int i1 = (int)(paramLong + n);

            k = i1;
            i = n;

            if (i1 < n)
            {
                k = i1 + 1;
                i = n + 1;
            }
            arrayOfByte[m] = ((byte)(-2 - k));
            j += 1;
            k = m;
        }
        return arrayOfByte;
    }

    /**
     * Base64解码函数，因为在加密时对最后生成的密文进行了Base64编码，所以在解密时先要解码
     * @param paramArrayOfByte  秘钥的byte数组
     * @param paramString       要解密的密文
     * @return                  解密后的明文
     */
    private static String Base64Encrypt(byte[] paramArrayOfByte,String paramString ){

        byte[] paramArrayOfBytes = AESEncrypt(paramArrayOfByte, paramString.getBytes());

        Log.d("jw","paramArrayOfBytes == "+paramArrayOfBytes.toString());

        return new String(Base64.encode(paramArrayOfBytes, 2));
    }

    /**
     * AES解密算法
     * @param paramArrayOfByte1 秘钥
     * @param paramArrayOfByte2 密文
     * @return                  明文
     */
    private static byte[] AESEncrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2){
        SecretKeySpec key = new SecretKeySpec(paramArrayOfByte1, "AES");
        Cipher localCipher = null;
        byte[] bt = null;
        try {
            localCipher = Cipher.getInstance("AES");
            localCipher.init(1, key);
            bt =  localCipher.doFinal(paramArrayOfByte2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }finally {
            return bt;
        }
    }
}
