package app;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpecPackage extends Pack{

    /*
    flag = 1 -- request;
    flag = 2 -- response;
    */
    private byte flag;

    private byte src;

    private byte dst;

    /*
    code = 1 -- waiting for data;
    code = 2 -- data sent;
    */
    private byte code;

    private byte sum;

    public SpecPackage() {
        this.flag = -1;
        this.src = -1;
        this.dst = -1;
        this.code = -1;
        this.sum = -1;
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[5];
        bytes[0] = flag;
        bytes[1] = src;
        bytes[2] = dst;
        bytes[3] = code;
        bytes[4] = sum;
        return bytes;
    }

    public static Pack fromByteArray(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (bytes.length < 5) {
            return null;
        }
        SpecPackage pack = new SpecPackage();
        pack.setFlag(bytes[0]);
        pack.setSrc(bytes[1]);
        pack.setDst(bytes[2]);
        pack.setCode(bytes[3]);
        pack.setSum(bytes[4]);
        return pack;
    }

    @Override
    public boolean isEmpty() {
        return flag == -1 || src == -1 || dst == -1 || code == -1 || sum == -1;
    }
}
