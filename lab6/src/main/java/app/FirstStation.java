package app;

import jssc.SerialPortException;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FirstStation {

    private static String message;

    private static boolean flag;

    public static void main(String[] args) {
        PackageUtil packageUtil = new PackageUtil();
        Serial port = null;
        try {
            port = new Serial("com1");
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

        Serial finalPort = port;
        Runnable task = () -> {
            while (true) {
                try {
                    Pack pack = packageUtil.getPackFromByteArray(finalPort.readBytes());    //waiting for request
                    if (pack != null) {
                        if (pack instanceof SpecPackage) {
                            SpecPackage specPackage = (SpecPackage) pack;
                            if (specPackage.getCode() == (byte) 1) {
                                while (true) {
                                    if (getFlag()) {
                                        SpecPackage pack3 = new SpecPackage();
                                        pack3.setFlag((byte) 1);
                                        pack3.setSrc((byte) 2);
                                        pack3.setDst((byte) 1);
                                        pack3.setCode((byte) 2);
                                        pack3.setSum((byte) 127);
                                        try {
                                            finalPort.writeBytes(pack3.toByteArray());
                                        } catch (SerialPortException e) {
                                            e.printStackTrace();
                                        }
                                        Pack pack1 = packageUtil.pack(specPackage.getSrc(), specPackage.getDst(), getMessage().getBytes(StandardCharsets.UTF_8));
                                        finalPort.writeBytes(pack1.toByteArray());
                                        changeFlag();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        while (true) {
            if (!getFlag()) {
                System.out.print(">>> ");
                String msg = new Scanner(System.in).nextLine();
                setMessage(msg);
                changeFlag();
            }
        }
    }

    public static synchronized boolean getFlag() {
        return flag;
    }

    public static synchronized void changeFlag() {
        flag = !flag;
    }

    public static synchronized void setMessage(String str) {
        message = str;
    }

    public static synchronized String getMessage() {
        return message;
    }
}
