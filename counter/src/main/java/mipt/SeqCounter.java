package mipt;

public class SeqCounter implements Counter {
    private long value;
    @Override
    public void increment() {
        value++;
    }

    @Override
    public long getValue() {
        return value;
    }
}
