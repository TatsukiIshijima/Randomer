package com.io.tatsuki.randomer.Utils;

/**
 * パスワード生成のUtil
 */

public class GenerateUtil {

    private static final String NUMBER = "0123456789";                    // 数字
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";     // 大文字
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";     // 小文字
    private static final String SYMBOL = "!#$%&()=-_~^+@*?[]";            // 記号

    /**
     * パスワード生成
     * @param passwordLength    桁数
     * @param numFlag           数字フラグ
     * @param uppFlag           大文字フラグ
     * @param lowFlag           小文字フラグ
     * @param symbolFlag        記号フラグ
     * @return  password        パスワード
     */
    public static String generatePassword(int passwordLength,
                                          boolean numFlag,
                                          boolean uppFlag,
                                          boolean lowFlag,
                                          boolean symbolFlag) {
        StringBuffer stringBuffer = new StringBuffer();
        String password = new String();

        if (numFlag) stringBuffer.append(NUMBER);
        if (uppFlag) stringBuffer.append(UPPER);
        if (lowFlag) stringBuffer.append(LOWER);
        if (symbolFlag) stringBuffer.append(SYMBOL);

        for (int i = 0; i < passwordLength; i++) {
            password += stringBuffer.charAt((int) (Math.random() * stringBuffer.length()));
        }

        return password;
    }
}
