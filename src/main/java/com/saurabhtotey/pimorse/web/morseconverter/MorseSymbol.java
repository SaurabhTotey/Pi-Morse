package com.saurabhtotey.pimorse.web.morseconverter;


/**
 * The symbols used in Morse Code: Dots and Dashes as well as the different types of spaces
 */
public enum MorseSymbol {
    
    DOT(1, true), DASH(3, true), SYMBOL_SPACE(1, false), LETTER_SPACE(3, false), WORD_SPACE(7, false);
    
    //How long the symbol is displayed in seconds
    public final int duration;
    //Whether the symbol means a transmission or a lack of one
    public final boolean state;
    
    /**
     * Creates a MorseSymbol given how long it lasts
     */
    MorseSymbol(int duration, boolean state) {
        this.duration = duration;
        this.state = state;
    }
    
    /**
     * Gets a string representation of the morse symbol
     */
    @Override
    public String toString() {
        if (this.state) {
            return this.duration == 1? "*" : "-";
        } else {
            String spaceString = "";
            for (int i = 0; i < this.duration; i++) {
                spaceString += " ";
            }
            return spaceString;
        }
    }
    
}
