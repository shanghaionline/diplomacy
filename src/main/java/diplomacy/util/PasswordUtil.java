package diplomacy.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordUtil {
    public static String sha1(String data) {
        String ret = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            digest.update(data.getBytes("UTF-8"));
            ret = hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            ret = "NoSuchAlgorithmException SHA1";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ret = "UnsupportedEncodingException UTF-8";
        }
        return ret;
    }

    public static String hex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte i : data) {
            buf.append(String.format("%02x", i));
        }
        return buf.toString();
    }

    public static String randomString(int length, String table) {
        StringBuilder buf = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i += 1) {
            int m = r.nextInt(table.length());
            buf.append(table.charAt(m));
        }
        return buf.toString();
    }

    public static String randomString(int length) {
        return randomString(length,
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }

    public static String password(String raw_password, String salt) {
        String password = sha1(raw_password + salt);
        return String.format("SHA1$%s$%s", salt, password);
    }

    public static String password() {
        return password(randomString(30));
    }

    public static String password(String raw_password) {
        return password(raw_password, randomString(8));
    }

    public static boolean check(String passwd, String raw_passwd) {
        String[] raw = passwd.split("\\$");
        boolean ret = false;
        if (raw.length == 3 && raw[0].equals("SHA1")) {
            ret = passwd.equals(password(raw_passwd, raw[1]));
        }
        return ret;
    }

    public static String invateHash(Long userId) {
        String key = String.format("%dINVITEKEY", userId);
        return sha1(key).substring(0, 7);
    }
}
