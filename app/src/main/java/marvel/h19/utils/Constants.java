package marvel.h19.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Various constants used in the project
 */
public class Constants {
    public static final String BASE_URL = "http://gateway.marvel.com";
    public static final String FORWARD_SLASH ="/";
    public static final String VERSION_ONE = "/v1/public";
    public static final String CHARACTERS = "characters";
    public static final String COMICS = "comics";
    public static final String EVENTS = "events";
    public static final String STORIES = "stories";
    public static final String SERIES = "series";
    public static final String API_KEY_MARVEL = "7d2c2442eae79d5468738ccbc32521ed";
    private static final String PRIVATE_KEY_MARVEL = "49def9db720a9b2426809921ed1227a9eacfc116";

    public static final String KEY_API = "apikey";
    public static final String KEY_HASH = "hash";
    public static final String KEY_LIMIT = "limit";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_AMPERCENT = "&";
    public static final String KEY_QUESTION_MARK = "?";
    public static final String KEY_EQUAL = "=";
    public static final String KEY_TIME_STAMP = "ts";
    public static final String KEY_DOT ="." ;
    public static final String KEY_SEARCH_NAME = "name";
    public static final String KEY_SEARCH_NAME_START_WITH = "nameStartsWith";
    public static final String KEY_IMAGE_NOT_AVAILALBLE = "image_not_available";

    public static final String KEY_DETAIL_VIEW_URL = "detail_url";
    public static final int ERROR_INTERNET_NOT_CONNECTED = 1000;
    public static final int LIMIT = 5;
    public static final int INITIAL_OFFSET = 0;
    public static final int DEFAULT_PADDING= 16;
    public static final int DELAY= 500;



    public static final String MARVEL_SERVER = BASE_URL + VERSION_ONE;

    //APIs
    public static final String URL_ALL_CHARACTERS = MARVEL_SERVER + FORWARD_SLASH + CHARACTERS;
    public static final String KEY_DATA = "data";
    public static final String KEY_RESULTS = "results";


    public static  String getCharacterDetail(String characterId) {
        return URL_ALL_CHARACTERS + FORWARD_SLASH + characterId;
    }

    public static  String getComicsForCharacter(String characterId) {
        return URL_ALL_CHARACTERS + FORWARD_SLASH + characterId+ FORWARD_SLASH+ COMICS;
    }

    public static  String getStoriesForCharacter(String characterId) {
        return URL_ALL_CHARACTERS + FORWARD_SLASH + characterId + FORWARD_SLASH+ STORIES;
    }

    public static  String getSeriesForCharacter(String characterId) {
        return URL_ALL_CHARACTERS + FORWARD_SLASH + characterId + FORWARD_SLASH + SERIES;
    }

    public static  String getEventsForCharacter(String characterId) {
        return URL_ALL_CHARACTERS + FORWARD_SLASH + characterId + FORWARD_SLASH + EVENTS;
    }

    public static String getHash(long time) {
        String md5=md5(String.valueOf(time) + PRIVATE_KEY_MARVEL + API_KEY_MARVEL);
        return md5;
    }
    private static String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }
}
