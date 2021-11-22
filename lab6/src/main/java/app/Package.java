package app;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Package extends Pack{

    private byte flag;

    private byte srcAddress;

    private byte destAddress;

    private byte dataSize;

    private byte[] data;

    private byte field;

    public Package() {
        this.flag = -1;
        this.srcAddress = -1;
        this.destAddress = -1;
        this.dataSize = -1;
        this.field = -1;
    }

    @Override
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

    public static Pack fromByteArray(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (bytes.length <= 5) {
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
    public boolean isEmpty() {
        return flag == -1 || srcAddress == -1 || destAddress == -1 || dataSize == -1 || data == null || field == -1;
    }

    @Override
    public String toString() {
        return "flag: " + flag +
                ", srcAddress: COM" + (int) srcAddress +
                ", destAddress: COM" + (int) destAddress +
                ", dataSize: " + (int) dataSize +
                ", data: " + new String(data) +
                ", field: " + (int) field;
    }
}
