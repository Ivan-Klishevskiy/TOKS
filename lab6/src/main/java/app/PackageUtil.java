package app;

public class PackageUtil {

    public Pack getPackFromByteArray(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (bytes[0] == (byte) 'a') {
            return Package.fromByteArray(bytes);
        } else {
            return SpecPackage.fromByteArray(bytes);
        }
    }

    public Pack pack (byte src, byte dst, byte[] data) {
        Package pack = new Package();
        pack.setFlag((byte) 'a');
        pack.setSrcAddress(src);
        pack.setDestAddress(dst);
        pack.setDataSize((byte) data.length);
        pack.setData(data);
        pack.setField((byte) 127);
        return pack;
    }
}
