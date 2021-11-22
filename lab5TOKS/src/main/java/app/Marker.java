package app;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Marker {

    private byte src;
    private byte dst;
    private byte available;
    private byte packageSize;
    private byte[] packageData;

    private static PackageUtil packageUtil = new PackageUtil();

    public Marker(byte src, byte dst, byte available) {
        this.src = src;
        this.dst = dst;
        this.available = available;
        this.packageSize = 0;
    }

    public byte[] toByteArray() {
        byte[] bytes = new byte[4 + packageSize];
        bytes[0] = src;
        bytes[1] = dst;
        bytes[2] = available;
        bytes[3] = packageSize;
        if (packageSize != 0) {
            System.arraycopy(packageData, 0, bytes, 4, packageData.length);
        }
        return bytes;
    }

    public boolean isAvailable() {
        return available == (byte) 0;
    }

    public void setPackage(Package pack) {
        this.packageData = pack.toByteArray();
        this.packageSize = (byte) this.packageData.length;
    }

    public static Marker fromByteArray(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Marker marker = new Marker(bytes[0], bytes[1], bytes[2]);
        marker.setPackageSize(bytes[3]);
        if (marker.getPackageSize() != 0) {
            marker.setPackageData(new byte[marker.getPackageSize()]);
            System.arraycopy(bytes, 4, marker.getPackageData(), 0, marker.getPackageSize());
        }
        return marker;
    }
}
