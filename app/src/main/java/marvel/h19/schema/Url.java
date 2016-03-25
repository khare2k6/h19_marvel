package marvel.h19.schema;

/**
 * Mapping of extra links received in json response, like detail, comics link etc
 */
public class Url {
    private String type;
    private String url;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
