package marvel.h19.schema;


import java.util.ArrayList;
import java.util.List;
/**
 * Comic schema received in Json response object for marvel character
 */
public class Comic {
    private String id;
    private String digitalId;
    private String title;
    private String issueNumber;
    private String variantDescription;
    private String description;
    private String modified;
    private String isbn;
    private String upc;
    private String diamondCode;
    private String ean;
    private String issn;
    private String format;
    private String pageCount;
    private Thumbnail thumbnail;
    private String resourceURI;
    private List<Url> urls = new ArrayList<Url>();
    private Collection series;
    private Collection stories;
    private Collection events;
    public String getId() {
        return id;
    }
    //getters
    public String getDigitalId() {
        return digitalId;
    }

    public String getTitle() {
        return title;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public String getVariantDescription() {
        return variantDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getModified() {
        return modified;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getUpc() {
        return upc;
    }

    public String getDiamondCode() {
        return diamondCode;
    }

    public String getEan() {
        return ean;
    }

    public String getIssn() {
        return issn;
    }

    public String getFormat() {
        return format;
    }

    public String getPageCount() {
        return pageCount;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public Collection getSeries() {
        return series;
    }

    public Collection getStories() {
        return stories;
    }

    public Collection getEvents() {
        return events;
    }

}
