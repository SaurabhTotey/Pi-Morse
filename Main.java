import morseconverter.MorseConverter;

/**
 * The main entry class for the program
 */
public class Main {
    
    /**
     * Entry point for the program
     */
    public static void main(String[] args) {
        for (morseconverter.MorseSymbol symbol : MorseConverter.charMap.get('1')) {
            System.out.println(symbol);
        }
    }
    
}
