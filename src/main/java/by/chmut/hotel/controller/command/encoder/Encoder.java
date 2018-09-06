package by.chmut.hotel.controller.command.encoder;


import sun.misc.BASE64Encoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {

    public static String encode(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digest = md.digest(password.getBytes());

            BASE64Encoder encoder = new BASE64Encoder();

            return encoder.encode(digest);

        } catch (NoSuchAlgorithmException e) {

            return null;
        }
    }

}
