package mipt;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;

public class CounterTest {
    public static final int NUMBER_OF_THREADS = 4;

    private static final int INCREMENTAL_CALLS_COUNT = 1000;
    private static final Counter lockCounter = new LockCounter();
    private static final Counter mutexCounter = new MutexCounter();
    private static final Counter concurrentCounter = new ConcurentCounter();
    private static final Counter magicCounter = new MagicCounter(NUMBER_OF_THREADS);

    @Test
    public void testLockExecution() throws Exception {
        final ExecutorService executors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        final List<Future> futures = range(0, INCREMENTAL_CALLS_COUNT)
                .mapToObj(i -> executors.submit(incrementRunnable(lockCounter)))
                .collect(toList());
        for (final Future future : futures) {
            future.get();
        }
        assertEquals("Oops! Smth is wrong!", INCREMENTAL_CALLS_COUNT, lockCounter.getValue());
    }

    @Test
    public void testMutexExecution() throws Exception {
        final ExecutorService executors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        final List<Future> futures = range(0, INCREMENTAL_CALLS_COUNT)
                .mapToObj(i -> executors.submit(incrementRunnable(mutexCounter)))
                .collect(toList());
        for (final Future future : futures) {
            future.get();
        }
        assertEquals("Oops! Smth is wrong!", INCREMENTAL_CALLS_COUNT, mutexCounter.getValue());
    }

    @Test
    public void testConcurrentExecution() throws Exception {
        final ExecutorService executors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        final List<Future> futures = range(0, INCREMENTAL_CALLS_COUNT)
                .mapToObj(i -> executors.submit(incrementRunnable(concurrentCounter)))
                .collect(toList());
        for (final Future future : futures) {
            future.get();
        }
        assertEquals("Oops! Smth is wrong!", INCREMENTAL_CALLS_COUNT, concurrentCounter.getValue());
    }

    private static Runnable incrementRunnable(final Counter counter){
        return counter::increment;
    }

    @Test
    public void testMagicExecution() throws Exception {
        final ExecutorService executors = Executors.newFixedThreadPool(NUMBER_OF_THREADS, new MagicCounterThreadFactory());
        final List<Future> futures = range(0, INCREMENTAL_CALLS_COUNT)
                .mapToObj(i -> executors.submit(incrementRunnable(magicCounter)))
                .collect(toList());
        for (final Future future : futures) {
            future.get();
        }
        assertEquals("Oops! Smth is wrong!", INCREMENTAL_CALLS_COUNT, magicCounter.getValue());
    }
}