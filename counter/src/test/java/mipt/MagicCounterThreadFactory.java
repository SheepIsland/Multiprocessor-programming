package mipt;

import java.util.concurrent.ThreadFactory;

public class MagicCounterThreadFactory implements ThreadFactory {
    private static int id = 0;

    @Override
    public Thread newThread(final Runnable r) {
        final Thread thread = new Thread(r);
        thread.setName(String.valueOf(id));
        id ++;
        return thread;
    }
}