package marvel.h19.schema;

/**
 * Thumbnail for image of comic,series etc received in json respnse
 */
public class Thumbnail {
    private String path;
    private String extension;

    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }
}
