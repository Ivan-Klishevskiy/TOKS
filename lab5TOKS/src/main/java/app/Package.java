package app;

public class Package {

    private byte flag;

    private byte srcAddress;

    private byte destAddress;

    private byte dataSize;

    private byte[] data;

    private byte field;

    public Package() {
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public byte getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(byte destAddress) {
        this.destAddress = destAddress;
    }

    public byte getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(byte srcAddress) {
        this.srcAddress = srcAddress;
    }

    public byte getDataSize() {
        return dataSize;
    }

    public void setDataSize(byte dataSize) {
        this.dataSize = dataSize;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte getField() {
        return field;
    }

    public void setField(byte field) {
        this.field = field;
    }

    public byte[] toByteArray() {
        byte[] bytes = new byte[5 + dataSize];
        bytes[0] = flag;
        bytes[1] = srcAddress;
        bytes[2] = destAddress;
        bytes[3] = dataSize;
        System.arraycopy(data, 0, bytes, 4, data.length);
        bytes[bytes.length - 1] = field;
        return bytes;
    }

    public static Package fromByteArray(byte[] bytes) {
        if(bytes == null) {
            return null;
        }
        if(bytes.length <=5 ) {
            return null;
        }
        Package pack = new Package();
        pack.setFlag(bytes[0]);
        pack.setSrcAddress(bytes[1]);
        pack.setDestAddress(bytes[2]);
        pack.setDataSize((byte) (bytes.length - 5));
        pack.setData(new byte[bytes.length - 5]);
        System.arraycopy(bytes, 4, pack.getData(), 0, bytes.length - 5);
        pack.setField(bytes[bytes.length - 1]);
        if (pack.getField() == (byte) 0b11111111) {
            return null;
        }
        return pack;
    }

    @Override
    public String toString() {
        return "flag: " + flag +
                ", srcAddress: COM" + (char) srcAddress +
                ", destAddress: COM" + (char) destAddress +
                ", dataSize: " + (int) dataSize +
                ", data: " + new String(data) +
                ", field: " + (int) field;
    }
}
