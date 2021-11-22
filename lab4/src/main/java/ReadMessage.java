
import static java.nio.charset.StandardCharsets.UTF_8;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.List;

public class ReadMessage {
    public static void main(String[] args) throws SerialPortException {
        MessengerCore messengerCore = new MessengerCore();
        String portName;
        String baudName = "9600";


        List<String> portNames = messengerCore.getPortNames();

        if (portNames.isEmpty()) {
            Main.print("There is no ports in your computer.");
            return;
        } else {
            portName = portNames.get(0);
        }

        messengerCore.connect(portName, baudName);
    }

    public static void messageCreator(String s) {
        StringBuffer message = new StringBuffer();

        message.append(s);
        if (s.length() >= 5) {
            if (message.substring(message.length() - 5, message.length()).equals("$end$")) {
                Main.print("Message: " + message.substring(0, message.length() - 5));
                message = new StringBuffer();
            }
        }
    }

    //Слушатель срабатывающий по появлению данных на COM-порт
    public static class PortReader implements SerialPortEventListener {
        private final SerialPort serialPort;

        public PortReader(SerialPort serialPort) {
            this.serialPort = serialPort;
        }

        @Override
        public void serialEvent(SerialPortEvent event) {
            //Если происходит событие установленной маски и количество байтов в буфере более 0
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    //Создаем строковую переменную s, куда и сохраняем данные
                    String s = new String(ByteStuffing.inject(serialPort.readBytes(event.getEventValue())), UTF_8);
                    //Выводим данные на консоль
                    //System.out.println("Received message: " + s);
                    messageCreator(s);
                }
                catch (SerialPortException e) {
                    e.printStackTrace();
                    Main.print("Cannot read message: serialEvent(SerialPortEvent event)");
                }
            }
        }
    }
}
