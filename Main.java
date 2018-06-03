import morseconverter.MorseConverter;

/**
 * The main entry class for the program
 */
public class Main {
    
    /**
     * Entry point for the program
     */
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(MorseConverter.getSymbolsForText("Hello World")));
    }
    
}
