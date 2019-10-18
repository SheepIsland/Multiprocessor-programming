package mipt;

import java.util.concurrent.atomic.AtomicLong;

/**
 *  Используя любой другой способ из арсенала языка Java (не используя synchronized и Locks)
 */
public class ConcurentCounter implements Counter {
    private final AtomicLong count = new AtomicLong(0);

    @Override
    public void increment() {
        count.incrementAndGet();
    }

    @Override
    public long getValue() {
        return count.get();
    }
}
