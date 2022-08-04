package app.credentials;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hasher {
    public static String hash(String original){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    original.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return "failed";
    }


    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
