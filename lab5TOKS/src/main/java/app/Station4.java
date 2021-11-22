package app;

import jssc.SerialPortException;

public class Station4 {

    private static boolean flag = false;

    public static void main(String[] args) throws SerialPortException {

        final Serial port = new Serial("com1", (byte) 5);
        final byte src = 0;

        Runnable task = () -> {
            while (true) {
                Marker marker = null;
                try {
                    marker = Marker.fromByteArray(port.readBytes());
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
                if (marker != null) {
                    if (marker.getDst() == src) {
                        if (!marker.isAvailable()) {
                            System.out.println(Package.fromByteArray(marker.getPackageData()));
                        }
                        changeFlag();
                    } else {
                        System.out.println("Marker re-sent.");
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
                Marker marker = new Marker((byte) 0, (byte) 0, (byte) 0);
                //System.out.println("Marker created.");
                changeFlag();
                port.writeBytes(marker.toByteArray());
                //System.out.println("Marker sent.");
            }
        }
    }

    public static synchronized void changeFlag() {
        flag = !flag;
    }

    public static synchronized boolean getFlag() {
        return flag;
    }
}
