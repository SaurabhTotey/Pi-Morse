package com.saurabhtotey.pimorse;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.saurabhtotey.pimorse.morseconverter.MorseConverter;
import com.saurabhtotey.pimorse.morseconverter.MorseSymbol;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class that handles sending out morse signals to the LED on the Raspberry Pi
 */
@RestController
public class MessageEmitter {
    
    //The actual utility object that represents the Raspberry Pi's GPIO board
    static private final GpioController gpio = GpioFactory.getInstance();
    //The actual utility object that represents the pin that will be used to output electrical signals
    static private final GpioPinDigitalOutput outputPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED Communicator", PinState.LOW);
    static {
        outputPin.setShutdownOptions(true, PinState.LOW);
    }
    
    /**
     * Emits the given message in morse code
     * LED lights up for dots and dashes
     */
    @RequestMapping(value = "/pi-morse", method = RequestMethod.POST)
    public static void emitMessage(@RequestParam(name = "message", defaultValue = "") String message) {
        for (MorseSymbol symbol : MorseConverter.getSymbolsForText(sanitizeMessageForOutput(message))) {
            System.out.print(symbol);
            outputPin.setState(symbol.state);
            try {
                //Each symbol lasts its duration * .5 seconds
                Thread.sleep(symbol.duration * 500);
            } catch (InterruptedException ignored) {}
        }
        outputPin.setState(false);
    }

    /**
     * Sanitizes a given input string so that it can be turned into morse without hassle
     */
    public static String sanitizeMessageForOutput(String input) {
        String cleanString = "";
        for (int i = 0; i < input.length(); i++) {
            char containedChar = Character.toLowerCase(input.charAt(i));
            if (!MorseConverter.charMap.containsKey(containedChar)) {
                continue;
            }
            cleanString += containedChar;
        }
        return cleanString;
    }

}
