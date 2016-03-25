package marvel.h19.schema;

import java.util.ArrayList;
import java.util.List;

/**
 * In Json response a collection of comics,events,stories or series for a component is represented
 * as this this class.
 */
public class Collection {
    // number of comics,events,stories etc available
    private Integer available;
    //unique uri pointing to this collection
    private String collectionURI;
    //additional item like uri to each comic,events etc
    private List<AdditionalItem> items = new ArrayList<AdditionalItem>();
    private Integer returned;

    //getters
    public Integer getAvailable() {
        return available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public List<AdditionalItem> getItems() {
        return items;
    }

    public Integer getReturned() {
        return returned;
    }


}
