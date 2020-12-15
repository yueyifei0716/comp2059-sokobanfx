package sokobanfx.model;

/**
 * GameObject represents the objects in a game.
 * <p>
 * Each object has a symbol which is used to load the game files.
 */
public enum GameObject {
    WALL('W'),
    BOUNDARY('P'),
    FLOOR(' '),
    CRATE('C'),
    TRAP_M('M'),
    TRAP_R('K'),
    PAD('H'),
    GATE('G'),
    DIAMOND('T'),
    KEEPER('S'),
    CRATE_ON_DIAMOND('O'),
    CRATE_ON_PAD('B'),
    PIPE_UP('U'),
    PIPE_DOWN('D'),
    PIPE_LEFT('L'),
    PIPE_RIGHT('R'),
    DEBUG_OBJECT('=');

    public final char symbol;

    GameObject(final char symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the enum char symbol of GameObject.
     * <p>
     * If the char is not the symbol of any enum,
     * it will return WALL as a default object.
     *
     * @param c the char symbol of object
     * @return the {@link GameObject} represented by a char
     */
    public static GameObject fromChar(char c) {
        for (GameObject t : GameObject.values()) {
            if (Character.toUpperCase(c) == t.symbol) {
                return t;
            }
        }
        return WALL;
    }

    /**
     * Returns the string representation of the symbol.
     *
     * @return the char related to a GameObject
     */
    public char getCharSymbol() {
        return symbol;
    }
}