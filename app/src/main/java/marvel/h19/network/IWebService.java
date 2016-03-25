package marvel.h19.network;

import marvel.h19.ui.DataAdapter;

/**
 * Created by ankitkha on 17-Mar-16.
 */
public interface IWebService {
    /**
     * Makes a call to marvel api for getting all characters list (Not used as of now)
     * @param callBack {@link marvel.h19.network.HttpRequest.IResponseCallBack}
     */
    public void getAllCharacters(HttpRequest.IResponseCallBack callBack);

    /**
     * Fetches comics collection for a character. (Not used as of now)
     * @param characterId of the intended character
     * @param callBack{@link marvel.h19.network.HttpRequest.IResponseCallBack}
     */
    public void getComicsForCharacter(String characterId,HttpRequest.IResponseCallBack callBack);

    /**
     * Searches character with given name on the server. <li><b>Note:Only fully matching character name will return the positive result</b></li>
     * @param characterName name of character to be searched on the server.
     * @param callBack {@link marvel.h19.network.HttpRequest.IResponseCallBack}
     * @param tag Tag with which these pending calls can be cancelled if required
     */
    public void searchCharacterWithName(String characterName,HttpRequest.IResponseCallBack callBack,String tag);

    /**
     * Searches character with given partial string on the server.
     * @param characterName name of character to be searched on the server.
     * @param callBack {@link marvel.h19.network.HttpRequest.IResponseCallBack}
     * @param tag Tag with which these pending calls can be cancelled if required
     */
    public void searchCharacterWithNameStartingWith(String characterName,HttpRequest.IResponseCallBack callBack,String tag);

    /**
     * To get Comics collection from server for intended character in limited sizes.
     * @param limit Max number of comics expected for this request
     * @param offset
     * @param characterId ID of intended character
     * @param callBack @{@link marvel.h19.network.HttpRequest.IResponseCallBack}
     */
    public void getComicsForCharacterWithOffset(int limit,int offset,String characterId,HttpRequest.IResponseCallBack callBack);

    /**
     * Get Characters from server with filters mentioned in the parameters.
     * @param limit max number of records expected in this request chunk.
     * @param offset regular offset meaning
     * @param callBack {@link marvel.h19.network.HttpRequest.IResponseCallBack}
     */
    public void getCharactersWithOffset(int limit,int offset,HttpRequest.IResponseCallBack callBack);

    /**
     * Generic method for fetching comics,series,events or stories collection from server for intended character
     * @param limit max number of records for this chunk
     * @param offset regular offset meaning
     * @param characterId ID of intended character
     * @param type {@link DataAdapter.AdapterType}
     * @param callBack {@link marvel.h19.network.HttpRequest.IResponseCallBack}
     */
    public void getComponentsWithOffset(int limit, int offset, String characterId, DataAdapter.AdapterType type, HttpRequest.IResponseCallBack callBack);

    /**
     * Cancel all web requests for the given tag
     * @param tag
     */
    public void cancellAllRequestsWithTag(String tag);

}
