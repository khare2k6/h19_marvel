package marvel.h19.schema;

/**
 * Story schema received in Json response object for marvel character
 */
public class Story {

    private String id;
    private String title;
    private String description;
    private String resourceURI;
    private String type;
    private String modified;
    private Thumbnail thumbnail;
    private Collection comics;
    private Collection series;
    private Collection events;
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

    public String getType() {
        return type;
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

    public Collection getSeries() {
        return series;
    }

    public Collection getEvents() {
        return events;
    }

    public Character getCharacters() {
        return characters;
    }
}
