package app;

import jssc.SerialPort;
import jssc.SerialPortException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Serial {

    private String name;

    private SerialPort serialPort;

    private byte priority;

    public Serial(String name, byte priority) throws SerialPortException {
        this.name = name;
        this.priority = priority;
        this.serialPort = new SerialPort(name);
        this.serialPort.openPort();
    }

    public boolean writeBytes(byte[] bytes) throws SerialPortException {
        return serialPort.writeBytes(bytes);
    }

    public byte[] readBytes() throws SerialPortException {
        return serialPort.readBytes();
    }
}
