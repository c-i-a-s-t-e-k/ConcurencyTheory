package lab1;

class MyRun implements Runnable {
    private final int id;

    public MyRun(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Watek " + id);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}