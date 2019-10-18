package mipt;

/**
 * Не используя никаких примитов синхронизации, классов java.util.concurrent, а также операций RMW и CAS.
 *
 * Bakery
 */
public class MagicCounter implements Counter {
    private final int n;

    private final boolean[] flag;
    private final int[] label;

    private volatile int value = 0;

    public MagicCounter(final int n) {
        this.n = n;
        this.flag = new boolean[n];
        this.label = new int[n];
        for (int i = 0; i < n; i++){
            flag[i] = false;
            label[i] = 0;
        }
    }

    private void lock() {
        final int id = Integer.parseInt(Thread.currentThread().getName());

        flag[id] = true;
        label[id] = max() + 1;
        flag[id] = false;
        for (int i = 0; i < n; i++) {
            while (flag[i]) {
                Thread.yield();
            }
            while (0 != label[i] && (label[id] > label[i] || label[id] == label[i] && id > i)) {
                Thread.yield();
            }
        }
    }

    private int max() {
        int max = label[0];
        for (int i = 1; i < n; i++) {
            if (label[i] > max) {
                max = label[i];
            }
        }
        return max;
    }

    private void unlock() {
        final int id = Integer.parseInt(Thread.currentThread().getName());
        label[id] = 0;
    }

    @Override
    public void increment() {
        lock();
        try {
            value++;
        } finally {
            unlock();
        }
    }

    @Override
    public long getValue() {
        return value;
    }
}
