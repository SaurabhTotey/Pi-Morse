package com.saurabhtotey.pimorse.web;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.saurabhtotey.pimorse.web.morseconverter.MorseConverter;
import com.saurabhtotey.pimorse.web.morseconverter.MorseSymbol;

/**
 * The class that handles sending out morse signals to the LED on the Raspberry Pi
 * Also handles receiving the REST requests over HTTP to signal the Pi to send the message
 */
@RestController
public class MessageEmitter {

    //The utility object that represents the pin that will be used to output electrical signals
    static private GpioPinDigitalOutput outputPin;
    //How much duration is left before the MessageEmitter is free: if is 0, MessageEmitter is free
    static private int durationLeftToEmit = 0;
    //How many milliseconds each duration is
    static private final long millisecondsPerDuration = 500;
    
    /**
     * Emits the given message in morse code given the message is sendable and the Raspberry Pi is available
     * Will fail if Raspberry Pi is unavailable or the message is not sendable
     * Success doesn't mean message has been sent, but rather that the Pi is in process of sending it
     * LED lights up for dots and dashes based on the morse pattern of the given message
     */
    @RequestMapping(value = "/pi-morse", method = RequestMethod.POST)
    public static EmissionStatus emitMessage(@RequestParam(name = "message", defaultValue = "") String message) {
        //Previous message is sending
        if (durationLeftToEmit > 0) {
            return new EmissionStatus(false, "A previous message is still printing... Please wait for " + timeUntilAvailable() + " more milliseconds before trying again.");
        }
        //Input sanitation
        String sendableMessage = sanitizeMessageForOutput(message);
        //Given message is not sendable
        if (sendableMessage.isEmpty()) {
            return new EmissionStatus(false, "Malformed or unsendable message... :(");
        }
        //Initializes the GPIO pin if uninitialized
        if (outputPin == null) {
            outputPin = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED Communicator", PinState.LOW);
        }
        //Starts a new thread to interact with the Raspberry Pi GPIO and send the message
        new Thread(() -> {
            System.out.println(sendableMessage);
            MorseSymbol[] encodedMessage = MorseConverter.getSymbolsForText(sendableMessage);
            for (MorseSymbol symbol : encodedMessage) {
                durationLeftToEmit += symbol.duration;
            }
            for (MorseSymbol symbol : encodedMessage) {
                System.out.print(symbol);
                outputPin.setState(symbol.state);
                try {
                    Thread.sleep(symbol.duration * millisecondsPerDuration);
                } catch (InterruptedException ignored) {}
                durationLeftToEmit -= symbol.duration;
            }
            //Ensures that the pin is off and that the Pi is available to send another message
            outputPin.setState(false);
            durationLeftToEmit = 0;
            System.out.println();
        }).start();
        //Waits for the Raspberry Pi to start sending the message
        while (durationLeftToEmit == 0) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {}
        }
        //Messaging thread to interface with Raspberry Pi GPIO has successfully started
        return new EmissionStatus(true, "Message is sending! It will have finished sending in " + timeUntilAvailable() + " milliseconds.");
    }

    /**
     * How much time in milliseconds is remaining before the Raspberry Pi is free again
     */
    private static long timeUntilAvailable() {
        return durationLeftToEmit * millisecondsPerDuration;
    }

    /**
     * Sanitizes a given input string so that it can be turned into morse without hassle
     */
    private static String sanitizeMessageForOutput(String input) {
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
