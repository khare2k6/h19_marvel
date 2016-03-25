package marvel.h19.ui;

import marvel.h19.schema.Character;

/**
 * Maintaining Singleton for Selected character from the character catalog or from search result.
 */
public class SelectedCharacter {
    private Character mCharacter;
    private static SelectedCharacter mInstance;

    private SelectedCharacter() {
    }

    public static SelectedCharacter getInstance() {
        if (mInstance == null) {
            mInstance = new SelectedCharacter();
        }
        return mInstance;
    }

    public Character get() {
        return mCharacter;
    }

    /**
     * Set Character object which has been clicked by user from catalog or search result
     * @param character object of selected character.
     */
    public void setCharacter(Character character) {
        mCharacter = character;
    }
}
