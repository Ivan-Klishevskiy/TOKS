package app;

import jssc.SerialPortException;

import java.util.Random;
import java.util.Scanner;

public class Station3 {

    private static boolean flag = false;
    private static String message;

    public static void main(String[] args) throws SerialPortException {

        final Serial port = new Serial("com2", (byte) 0);
        final byte src = 1;

        Random random = new Random();
        PackageUtil packageUtil = new PackageUtil();

        Runnable task = () -> {
            while (true) {
                Marker marker = null;
                try {
                    marker = Marker.fromByteArray(port.readBytes());
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
                if (marker != null) {
                    if (marker.isAvailable()) {
                        if (getFlag()) {
                            marker.setSrc(src);
                            marker.setDst((byte) random.nextInt(2));
                            marker.setAvailable((byte) 1);
                            marker.setPackage(packageUtil.pack("com2", "com1", getMessage()));
                            changeFlag();
                            try {
                                port.writeBytes(marker.toByteArray());
                            } catch (SerialPortException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                port.writeBytes(marker.toByteArray());
                            } catch (SerialPortException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        if (marker.getDst() == src) {
                            System.out.println(Package.fromByteArray(marker.getPackageData()));
                            marker.setSrc((byte) 0);
                            marker.setDst((byte) 0);
                            marker.setAvailable((byte) 0);
                        }
                        try {
                            port.writeBytes(marker.toByteArray());
                        } catch (SerialPortException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        while (true) {
            if (!getFlag()) {
                System.out.print(">>> ");
                setMessage(new Scanner(System.in).nextLine());
                changeFlag();
            }
        }
    }

    public static synchronized void changeFlag() {
        flag = !flag;
    }

    public static synchronized boolean getFlag() {
        return flag;
    }

    public static synchronized void setMessage(String msg) {
        message = msg;
    }

    public static synchronized String getMessage() {
        return message;
    }
}
