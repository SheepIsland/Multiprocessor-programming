package mipt;

/**
 * Используя synchronized из арсенала языка Java
 */
public class MutexCounter implements Counter {
    private volatile long value;

    @Override
    public synchronized void increment() {
        value++;
    }

    @Override
    public long getValue() {
        return value;
    }
}
