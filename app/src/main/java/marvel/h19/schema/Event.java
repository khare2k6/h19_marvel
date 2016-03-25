package marvel.h19.schema;

import java.util.ArrayList;
import java.util.List;

/**
 * Event schema received in Json response object for marvel character
 */
public class Event {
    private String id;
    private String title;
    private String description;
    private String resourceURI;
    private List<Url> urls = new ArrayList<Url>();
    private String modified;
    private String start;
    private String end;
    private Thumbnail thumbnail;
    private Collection comics;
    private Collection stories;
    private Collection series;
    private Character characters;

    //getters
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

    public String getModified() {
        return modified;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
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

    public Collection getSeries() {
        return series;
    }

    public Character getCharacters() {
        return characters;
    }
}
