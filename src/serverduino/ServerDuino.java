package serverduino;

import java.io.IOException;
import gnu.io.SerialPort;

/**
 *
 * @author jvilarru
 */
public class ServerDuino {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        System.out.println("START");
        SerialReader prova = new SerialReader("COM5",9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE,SerialPort.FLOWCONTROL_NONE);
        prova.readLines();
        System.out.println("END");
    }
    
}
