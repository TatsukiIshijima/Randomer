package com.io.tatsuki.randomer.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.kazakago.cryptore.CipherAlgorithm;
import com.kazakago.cryptore.Cryptore;
import com.kazakago.cryptore.DecryptResult;
import com.kazakago.cryptore.EncryptResult;

/**
 * 暗号化・復号化クラス
 */

public class EncryptUtil {

    private static final String TAG = EncryptUtil.class.getSimpleName();
    private static final String ALIAS_RSA = "CIPHER_RSA";
    private static final String ALIAS_AES = "CIPHER_AES";

    /**
     * 初期化（RSA）
     * @param context
     * @return
     * @throws Exception
     */
    private static Cryptore getCryptoreRSA(@NonNull Context context) throws Exception {
        Cryptore.Builder builder = new Cryptore.Builder(ALIAS_RSA, CipherAlgorithm.RSA);
        builder.setContext(context);                                    //Need Only RSA on below API Lv22.
        //builder.setBlockMode(BlockMode.ECB);                          //If Needed.
        //builder.setEncryptionPadding(EncryptionPadding.RSA_PKCS1);    //If Needed.
        return builder.build();
    }

    /**
     * 暗号化
     * @param context
     * @param plainStr
     * @return String 暗号化した文字列
     */
    public static String encryptRSA(@NonNull Context context, String plainStr) {
        try {
            byte[] plainByte = plainStr.getBytes();
            EncryptResult result = getCryptoreRSA(context).encrypt(plainByte);
            return Base64.encodeToString(result.getBytes(), Base64.DEFAULT);
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * 復号化
     * @param context
     * @param encryptedStr
     * @return String 復号した文字列
     */
    public static String decryptRSA(@NonNull Context context, String encryptedStr) {
        try {
            byte[] encryptedByte = Base64.decode(encryptedStr, Base64.DEFAULT);
            DecryptResult result = getCryptoreRSA(context).decrypt(encryptedByte, null);
            return new String(result.getBytes());
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return "";
    }
}
