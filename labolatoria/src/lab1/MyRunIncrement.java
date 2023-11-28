package lab1;

public class MyRunIncrement implements Runnable{
    static private int value;
    private final int id;
    private final int add;

    public MyRunIncrement(int id, int add) {
        this.id = id;
        this.add = add;
    }
    public void printId(){
        System.out.println("Wątek " + this.id);
    }

    static public void printValue(){
        System.out.println("Wartość: " + value);
    }

    @Override
    public void run() {
        value += add;
    }
}
