public class Main {
    static long sec = 0;
    public static void main(String[] args) throws InterruptedException {
        Work work = new Work();
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    work.produser();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    work.consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread threadThree = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    work.produser2();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        threadOne.start();
        threadTwo.start();
        threadThree.start();

    }
}

class Work {
    public void consumer() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (true) {
            Main.sec = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println(Main.sec);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public void produser() throws InterruptedException {
        synchronized (this) {
            while (true) {
                wait();
                if ((Main.sec % 5 == 0) && (Main.sec > 0)) {
                    System.out.println("Сообщение каждые 5 сек");
                }
            }
        }
    }

    public void produser2() throws InterruptedException {
        synchronized (this) {
            while (true) {
                wait();
                if ((Main.sec % 7 == 0) && (Main.sec > 0)) {
                    System.out.println("Сообщение каждые 7 сек");
                }
            }
        }
    }
}