package serverduino;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jvilarru
 */
public class SerialReader {

    private final String serialPortName;
    private final int baudRate;
    private SerialPort serialPort;
    private int databits;
    private int stopbits;
    private int parity;
    private int flowControl;

    public int getFlowControl() {
        return flowControl;
    }

    public int getDatabits() {
        return databits;
    }

    public int getStopbits() {
        return stopbits;
    }

    public int getParity() {
        return parity;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public String getSerialPortName() {
        return serialPortName;
    }

    public SerialReader(String serialPortName, int baudRate, int databits, int stopbits, int parity, int flowControl) {
        this.serialPortName = serialPortName;
        this.baudRate = baudRate;
        this.databits = databits;
        this.stopbits = stopbits;
        this.parity = parity;
        this.flowControl = flowControl;
        this.serialPort = null; //will be initialized after

        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL && portIdentifier.getName().equals(serialPortName)) {
                System.out.println("Trying to grab connection on " + serialPortName);
                try {
                    this.serialPort = (SerialPort) portIdentifier.open("ServerDuino", 5000);
                } catch (PortInUseException ex) {
                    System.err.println("Serial port already in use by " + portIdentifier.getCurrentOwner());
                    Logger.getLogger(SerialReader.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (serialPort != null) {
                    try {
                        this.serialPort.setSerialPortParams(baudRate, databits, stopbits, parity);
                        this.serialPort.setFlowControlMode(flowControl);
                    } catch (UnsupportedCommOperationException ex) {
                        System.err.println("Could not set the properties ( " + baudRate + "," + databits + "," + stopbits + "," + parity
                                + " )to the serial port.");
                        Logger.getLogger(SerialReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
        }
        if (serialPort == null) {
            System.err.println("There is no " + serialPortName + " serial port");
        }
    }

    public void readLines() throws IOException {
        //TBI
    }

}
