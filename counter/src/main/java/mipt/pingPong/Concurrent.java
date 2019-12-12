package mipt.pingPong;

import java.util.concurrent.Semaphore;

public class Concurrent {
    private static final Semaphore mutexPing = new Semaphore(1);
    private static final Semaphore mutexPong = new Semaphore(0);

    private static class Ping extends Thread {
        private static final String PING = "PING";

        @Override
        public void run() {
            while (true) {
                try {
                    mutexPing.acquireUninterruptibly();
                    System.out.print(PING + " -> ");
                    mutexPong.release();
                } catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static class Pong extends Thread {
        public static final String PONG = "PONG";

        @Override
        public void run() {
            while (true) {
                try {
                    mutexPong.acquireUninterruptibly();
                    System.out.println(PONG);
                    mutexPing.release();
                } catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(final String... args) {
        new Ping().start();
        new Pong().start();
    }
}
