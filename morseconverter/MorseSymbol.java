package morseconverter;


/**
 * The symbols used in Morse Code: Dots and Dashes as well as the different types of spaces
 */
public enum MorseSymbol {
    
    DOT(1, true), DASH(3, true), SYMBOL_SPACE(1, false), LETTER_SPACE(3, false), WORD_SPACE(7, false);
    
    //How long the symbol is displayed in seconds
    int duration;
    //Whether the symbol means a transmission or a lack of one
    boolean state;
    
    /**
     * Creates a MorseSymbol given how long it lasts
     */
    private MorseSymbol(int duration, boolean state) {
        this.duration = duration;
        this.state = state;
    }
    
}
