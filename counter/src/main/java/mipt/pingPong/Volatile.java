package mipt.pingPong;

public class Volatile {
    private static volatile boolean ping = true;

    private static class Ping extends Thread {
        private static final String PING = "PING";

        @Override
        public void run() {
            while(true) {
                while (ping) {
                    System.out.print(PING + " -> ");
                    ping = false;
                }
            }
        }
    }

    private static class Pong extends Thread {
        public static final String PONG = "PONG";

        @Override
        public void run() {
            while(true) {
                while(!ping) {
                    System.out.println(PONG);
                    ping = true;
                }
            }
        }
    }

    public static void main(final String... args) {
        new Ping().start();
        new Pong().start();
    }
}

