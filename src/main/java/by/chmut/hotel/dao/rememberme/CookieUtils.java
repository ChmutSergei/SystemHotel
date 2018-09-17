package by.chmut.hotel.dao.rememberme;

import java.util.Base64;

public class CookieUtils {

    private static final String DELIMITER = ":";

    private static final CookieUtils INSTANCE = new CookieUtils();

    public static CookieUtils getInstance() {
        return INSTANCE;
    }


    public String[] decodeCookie(String cookieValue) {

        String cookieAsPlainText = new String(Base64.getDecoder().decode(cookieValue.getBytes()));

        String[] tokens = cookieAsPlainText.split(DELIMITER);

        return tokens;
    }


    public String encodeCookie(String[] cookieTokens) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cookieTokens.length; i++) {
            sb.append(cookieTokens[i]);
            if (i < cookieTokens.length - 1) {
                sb.append(DELIMITER);
            }
        }

        String value = sb.toString();

        String result = new String(Base64.getEncoder().encode(value.getBytes()));

        return result;
    }


}
