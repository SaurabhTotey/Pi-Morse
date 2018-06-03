/**
 * The main entry class for the program
 */
public class Main {
    
    /**
     * Entry point for the program
     */
    public static void main(String[] args) {
        try {
            MessageEmitter.emitMessage(args[0]);
        } catch (Exception e) {
            MessageEmitter.emitMessage("Hello World");
        }
        MessageEmitter.gpio.shutdown();
    }
    
}
