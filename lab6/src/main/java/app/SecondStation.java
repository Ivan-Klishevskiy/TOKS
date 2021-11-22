package app;

import jssc.SerialPortException;

public class SecondStation {

    private static boolean flag;

    private static String message;

    public static void main(String[] args) {
        PackageUtil packageUtil = new PackageUtil();
        Serial port = null;
        try {
            port = new Serial("com2");
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

        Serial finalPort = port;
        Runnable task = () -> {
            while (true) {
                try {
                    Pack pack1 = packageUtil.getPackFromByteArray(finalPort.readBytes());
                    if (pack1 instanceof SpecPackage) {
                        SpecPackage pack2 = (SpecPackage) pack1;
                        if (pack2.getCode() == (byte) 2) {
                            while (true) {
                                Pack pack3 = packageUtil.getPackFromByteArray(finalPort.readBytes());
                                if (pack3 != null) {
                                    System.out.println(pack3);
                                    changeFlag();
                                    break;
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
                SpecPackage pack = new SpecPackage();
                pack.setFlag((byte) 1);
                pack.setSrc((byte) 2);
                pack.setDst((byte) 1);
                pack.setCode((byte) 1);
                pack.setSum((byte) 127);
                try {
                    port.writeBytes(pack.toByteArray());
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
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
