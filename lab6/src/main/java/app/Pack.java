package app;

public abstract class Pack {

    public abstract byte[] toByteArray();

    public static Pack fromByteArray(byte[] bytes){
        return null;
    };

    public abstract boolean isEmpty();
}
