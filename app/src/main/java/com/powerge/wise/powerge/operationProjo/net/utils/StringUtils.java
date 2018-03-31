package com.powerge.wise.powerge.operationProjo.net.utils;

import android.text.TextUtils;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2018/3/30.
 */

public class StringUtils {

    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public StringUtils() {
    }

    public static String[] parseURL(String var0) {
        if(TextUtils.isEmpty(var0)) {
            return null;
        } else {
            if(var0.startsWith("//")) {
                var0 = "http:" + var0;
            }

            int var1 = var0.indexOf("://");
            if(var1 == -1) {
                return null;
            } else {
                String[] var2 = new String[2];
                String var3 = var0.substring(0, var1);
                if(!"http".equalsIgnoreCase(var3) && !"https".equalsIgnoreCase(var3)) {
                    return null;
                } else {
                    var2[0] = var3;
                    int var4 = var0.length();

                    int var5;
                    for(var5 = var1 + 3; var5 < var4; ++var5) {
                        char var6 = var0.charAt(var5);
                        if(var6 == 47 || var6 == 58 || var6 == 63 || var6 == 35) {
                            var2[1] = var0.substring(var1 + 3, var5);
                            return var2;
                        }
                    }

                    if(var5 == var4) {
                        var2[1] = var0.substring(var1 + 3);
                        return var2;
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    public static String concatString(String var0, String var1) {
        return (new StringBuilder(var0.length() + var1.length())).append(var0).append(var1).toString();
    }

    public static String concatString(String var0, String var1, String var2) {
        return (new StringBuilder(var0.length() + var1.length() + var2.length())).append(var0).append(var1).append(var2).toString();
    }

    public static String buildKey(String var0, String var1) {
        return concatString(var0, "://", var1);
    }

    public static String simplifyString(String var0, int var1) {
        return var0.length() <= var1?var0:concatString(var0.substring(0, var1), "......");
    }

    public static String stringNull2Empty(String var0) {
        return var0 == null?"":var0;
    }

    public static String md5ToHex(String var0) {
        if(var0 == null) {
            return null;
        } else {
            try {
                MessageDigest var1 = MessageDigest.getInstance("MD5");
                return bytesToHexString(var1.digest(var0.getBytes("utf-8")));
            } catch (Exception var2) {
                return null;
            }
        }
    }

    private static String bytesToHexString(byte[] var0, char[] var1) {
        int var2 = var0.length;
        char[] var3 = new char[var2 << 1];
        int var4 = 0;

        for(int var5 = 0; var4 < var2; ++var4) {
            var3[var5++] = var1[(240 & var0[var4]) >>> 4];
            var3[var5++] = var1[15 & var0[var4]];
        }

        return new String(var3);
    }

    public static String bytesToHexString(byte[] var0) {
        return var0 == null?"":bytesToHexString(var0, DIGITS_LOWER);
    }

    public static String longToIP(long var0) {
        StringBuilder var2 = new StringBuilder(16);
        long var3 = 1000000000L;

        do {
            var2.append(var0 / var3);
            var2.append('.');
            var0 %= var3;
            var3 /= 1000L;
        } while(var3 > 0L);

        var2.setLength(var2.length() - 1);
        return var2.toString();
    }

    public static boolean isNotBlank(String var0) {
        return !isBlank(var0);
    }

    public static boolean isBlank(String var0) {
        int var1;
        if(var0 != null && (var1 = var0.length()) != 0) {
            for(int var2 = 0; var2 < var1; ++var2) {
                if(!Character.isWhitespace(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
