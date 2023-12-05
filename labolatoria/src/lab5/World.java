package lab5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class World {
    private int value = 0;
    private final int bufferSize;
    private final ReentrantLock mainLock = new ReentrantLock();
    private final ReentrantLock producerLock = new ReentrantLock();
    private final ReentrantLock consumerLock = new ReentrantLock();

    private final Condition cond = mainLock.newCondition();


    public void produce(int toProduce, Person person){
        producerLock.lock();
        System.out.println(person.introduce() + " want to produce " + toProduce + " Value = " + this.value);


        mainLock.lock();
        while (!canProduce(toProduce)) {
            System.out.println(person.introduce() + " is waiting\n");
            try {
                cond.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.value += toProduce;
        System.out.println(person.introduce() + " Produced " + toProduce + "\n");
        cond.signal();
        mainLock.unlock();

        producerLock.unlock();
    }

    public void consume(int toConsume, Person person){
        consumerLock.lock();
        System.out.println(person.introduce() + " want to consume " + toConsume + " Value = " + this.value);

        mainLock.lock();
        while (!canConsume(toConsume)) {
            System.out.println(person.introduce() + " is waiting\n");
            try {
                cond.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.value -= toConsume;
        System.out.println(person.introduce() + " Consumed " + toConsume + "\n");
        cond.signal();
        mainLock.unlock();

        consumerLock.unlock();
    }

    private boolean canConsume(int toConsume){
        return (value - toConsume >= 0);
    }
    private boolean canProduce(int toProduce) {
        return ((this.value + toProduce) <= bufferSize);
    }

    public World(int size){
        this.bufferSize = size;
    }
}

