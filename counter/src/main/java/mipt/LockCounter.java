package mipt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCounter implements Counter {
    private volatile long value = 0;
    private final Lock lock = new ReentrantLock(true);

    @Override
    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getValue() {
        return value;
    }
}
