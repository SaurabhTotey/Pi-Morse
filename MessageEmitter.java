import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import morseconverter.MorseConverter;
import morseconverter.MorseSymbol;

/**
 * The class that handles sending out morse signals to the LED on the Raspberry Pi
 */
public class MessageEmitter {
    
    //The actual utility object that represents the Raspberry Pi's GPIO board
    static final GpioController gpio = GpioFactory.getInstance();
    //The actual utility object that represents the pin that will be used to output electrical signals
    static final GpioPinDigitalOutput outputPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED Communicator", PinState.LOW);
    static {
        outputPin.setShutdownOptions(true, PinState.LOW);
    }
    
    /**
     * Emits the given message in morse code
     * LED lights up for dots and dashes
     */
    public static void emitMessage(String message) {
        for (MorseSymbol symbol : MorseConverter.getSymbolsForText(message)) {
            System.out.print(symbol);
            outputPin.setState(symbol.state);
            try {
                //Each symbol lasts its duration * .5 seconds
                Thread.sleep(symbol.duration * 500);
            } catch (InterruptedException e) {}
        }
        outputPin.setState(false);
    }
    
}
