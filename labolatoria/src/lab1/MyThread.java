package lab1;

class MyThread extends Thread {
    private int id;

    public MyThread(int id) {
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