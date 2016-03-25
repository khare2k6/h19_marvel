package marvel.h19.schema;

import java.util.ArrayList;
import java.util.List;

/**
 * Series schema received in Json response object for marvel character
 */
public class SeriesSingular {
    private String id;
    private String title;
    private String description;
    private String resourceURI;
    private List<Url> urls = new ArrayList<Url>();
    private String startYear;
    private String endYear;
    private String rating;
    private String modified;
    private Thumbnail thumbnail;
    private Collection comics;
    private Collection stories;
    private Collection events;
    private Character characters;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public String getStartYear() {
        return startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public String getRating() {
        return rating;
    }

    public String getModified() {
        return modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Collection getComics() {
        return comics;
    }

    public Collection getStories() {
        return stories;
    }

    public Collection getEvents() {
        return events;
    }

    public Character getCharacters() {
        return characters;
    }
}
