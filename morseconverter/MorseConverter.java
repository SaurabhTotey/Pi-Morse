package morseconverter;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A class is basically just a namespace that handles converting text or characters to morse
 */
public class MorseConverter {
    
    //A map for characters to MorseSymbols
    public static HashMap<Character, MorseSymbol[]> charMap = new HashMap<Character, MorseSymbol[]>();
    //Loads the character to symbol map with the international standard of morse combinations
    static {
        //Initializes charMap with all the morse combinations of letters
        charMap.put('a', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DASH });
        charMap.put('b', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT });
        charMap.put('c', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT });
        charMap.put('d', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT });
        charMap.put('e', new MorseSymbol[]{ MorseSymbol.DOT });
        charMap.put('f', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT });
        charMap.put('g', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT });
        charMap.put('h', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT });
        charMap.put('i', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DOT });
        charMap.put('j', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH });
        charMap.put('k', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH });
        charMap.put('l', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT });
        charMap.put('m', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DASH });
        charMap.put('n', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DOT });
        charMap.put('o', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH });
        charMap.put('p', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT });
        charMap.put('q', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH });
        charMap.put('r', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT });
        charMap.put('s', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT });
        charMap.put('t', new MorseSymbol[]{ MorseSymbol.DASH });
        charMap.put('u', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH });
        charMap.put('v', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH });
        charMap.put('w', new MorseSymbol[]{ MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH });
        charMap.put('x', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH });
        charMap.put('y', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH });
        charMap.put('z', new MorseSymbol[]{ MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT });
        //Initializes numeralMorseSymbolPool with the ordered combination of symbols that all numbers follow
        MorseSymbol[] numeralMorseSymbolPool = new MorseSymbol[15];
        for (int i = 0; i < 15; i++) {
            numeralMorseSymbolPool[i] = (i / 5) % 2 == 0? MorseSymbol.DASH : MorseSymbol.DOT;
        }
        //Adds numerals to the charMap based on numeralMorseSymbolPool
        for (int i = 1; i <= 10; i++) {
            //i % 10 keeps digit the same as i unless i is 10, in which case digit becomes 0
            charMap.put((char) (i % 10 + '0'), Arrays.copyOfRange(numeralMorseSymbolPool, 10 - i, 15 - i));
        }
    }
}
