package app;

import jssc.SerialPort;
import jssc.SerialPortException;
import lombok.Data;

@Data
public class Serial {

    private SerialPort port;

    private String name;

    public Serial(String name) throws SerialPortException {
        this.port = new SerialPort(name);
        this.name = name;
        this.port.openPort();
    }

    public void writeBytes(byte[] bytes) throws SerialPortException {
        port.writeBytes(bytes);
    }

    public byte[] readBytes() throws SerialPortException {
        return port.readBytes();
    }
}
