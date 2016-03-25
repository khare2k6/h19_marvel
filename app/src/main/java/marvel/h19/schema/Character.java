package marvel.h19.schema;

import java.util.ArrayList;
import java.util.List;

/**
 * Character schema received in Json response object for marvel character
 */
public class Character {
    private Integer id;
    private String name;
    private String description;
    private String modified;
    private Thumbnail thumbnail;
    private String resourceURI;
    private Collection comics;
    private Collection series;
    private Collection stories;
    private Collection events;

    public List<Url> urls = new ArrayList<Url>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getModified() {
        return modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public Collection getComics() {
        return comics;
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

    public List<Url> getUrls() {
        return urls;
    }
}
