package com.saurabhtotey.pimorse.web;

import com.saurabhtotey.pimorse.morseconverter.MorseConverter;
import com.saurabhtotey.pimorse.morseconverter.MorseSymbol;
import com.saurabhtotey.pimorse.raspberrypi.MessageEmitter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class that handles controlling the REST requests over the network to make the API available over the local network
 */
@RestController
public class WebController {

    /**
     * As a GET method, allows clients to send text and receive the morse version of the text
     */
    @RequestMapping(value = "/pi-morse", method = RequestMethod.GET)
    String getMorseOfMessage(@RequestParam(name = "message") String message) {
        MorseSymbol[] symbols = MorseConverter.getSymbolsForText(message);
        String output = "";
        for (MorseSymbol symbol : symbols) {
            output += symbol.toString();
        }
        return output;
    }

    /**
     * As a POST method, allows clients to post a message for the Raspberry Pi to emit with LEDs in morse
     */
    @RequestMapping(value = "/pi-morse", method = RequestMethod.POST)
    EmissionStatus makePiEmitMessage(@RequestParam(name = "message", defaultValue = "") String message) {
        return MessageEmitter.emitMessage(message);
    }

}
