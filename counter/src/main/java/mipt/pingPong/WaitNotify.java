package mipt.pingPong;

public class WaitNotify {
    private static final Object LOCK_OBJ = new Object();
    private static final Integer DELAY = 1000;

    private static class Ping extends Thread {
        private static final String PING = "PING";

        @Override
        public void run() {
            while(true) {
                synchronized (LOCK_OBJ) {
                    System.out.print(PING + " -> ");
                    LOCK_OBJ.notify();
                    try {
                        LOCK_OBJ.wait();
                    } catch (final InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private static class Pong extends Thread {
        public static final String PONG = "PONG";

        @Override
        public void run() {
            while(true) {
                synchronized (LOCK_OBJ) {
                    try {
                        LOCK_OBJ.wait();
                        Thread.sleep(DELAY);
                    } catch (final InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(PONG);
                    LOCK_OBJ.notify();
                }
            }
        }
    }

    public static void main(final String... args) throws InterruptedException {
        new Pong().start();
        new Ping().start();
    }
}
