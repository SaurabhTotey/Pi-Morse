package com.saurabhtotey.pimorse.morseconverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A class is basically just a namespace that handles converting text or characters to morse
 */
@RestController
public class MorseConverter {
    
    //A map for characters to MorseSymbols; contains combination of symbols, but excludes spaces
    public static HashMap<Character, MorseSymbol[]> charMap = new HashMap<>();
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
        charMap.put(' ', new MorseSymbol[]{ MorseSymbol.WORD_SPACE });
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
    
    /**
     * This method gets the morse symbols for the given letter
     * This method is different than calling .get for the charMap because this method also inserts necessary spaces
     */
    private static MorseSymbol[] getSymbolsForLetter(char letter) {
        MorseSymbol[] dotsAndDashes = charMap.get(Character.toLowerCase(letter));
        MorseSymbol[] allLetterSymbols = new MorseSymbol[dotsAndDashes.length * 2 - 1];
        for (int i = 0; i < allLetterSymbols.length; i++) {
            allLetterSymbols[i] = i % 2 == 0? dotsAndDashes[i / 2] : MorseSymbol.SYMBOL_SPACE;
        }
        return allLetterSymbols;
    }
    
    /**
     * This method gets the morse symbols for the given text
     */
    @RequestMapping(value = "/pi-morse", method = RequestMethod.GET)
    public static MorseSymbol[] getSymbolsForText(@RequestParam(name = "text") String text) {
        ArrayList<MorseSymbol> allTextSymbols = new ArrayList<>();
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (int j = 0; j < word.length(); j++) {
                char letter = word.charAt(j);
                allTextSymbols.addAll(Arrays.asList(getSymbolsForLetter(letter)));
                if (j != word.length() - 1) {
                    allTextSymbols.add(MorseSymbol.LETTER_SPACE);
                }
            }
            if (i != words.length - 1) {
                allTextSymbols.add(MorseSymbol.WORD_SPACE);
            }
        }
        Object[] symbolsAsObjects = allTextSymbols.toArray();
        return Arrays.copyOf(symbolsAsObjects, symbolsAsObjects.length, MorseSymbol[].class);
    }
    
}
