package app;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PackageUtil {

    public Package pack(String srcAddress, String destAddress, String data) {
        Package pack = new Package();
        pack.setFlag((byte) 'a');
        pack.setSrcAddress((byte) srcAddress.charAt(srcAddress.length() - 1));
        pack.setDestAddress((byte) destAddress.charAt(destAddress.length() - 1));
        pack.setData(byteStaffing(data.getBytes(StandardCharsets.UTF_8), pack.getFlag()));
        pack.setDataSize((byte) pack.getData().length);
        pack.setField((byte) 0b01111111);
        return pack;
    }

    public String unPack(Package pack) {
        String data = null;
        if (pack.getData() != null) {
            data = new String(byteUnStaffing(pack.getData(), pack.getFlag()));
        }
        return data;
    }

    public byte[] byteStaffing(byte[] bytes, byte flag) {
        List<Byte> list = IntStream.range(0, bytes.length).mapToObj(i -> bytes[i]).collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(flag)) {
                list.set(i, (byte) (list.get(i) - 1));
                list.add(i + 1, (byte) (list.get(i) - 2));
            }
        }
        byte[] temp = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            temp[i] = list.get(i);
        }
        return temp;
    }

    public byte[] byteUnStaffing(byte[] bytes, byte flag) {
        List<Byte> list = IntStream.range(0, bytes.length).mapToObj(i -> bytes[i]).collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals((byte) (flag - 1))) {
                list.set(i, (byte) (list.get(i) + 1));
                list.remove(i + 1);
            }
        }
        byte[] temp = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            temp[i] = list.get(i);
        }
        return temp;
    }
}
