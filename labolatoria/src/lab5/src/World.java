package lab5.src;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class World {
    private int value = 0;
    private final int bufferSize;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition producerCond = lock.newCondition();
    private final Condition firstProducerCond = lock.newCondition();
    private boolean isFirstProducerEmpty = true;
    private final Condition consumerCond = lock.newCondition();
    private final Condition firstConsumerCond = lock.newCondition();
    private boolean isFirstConsumerEmpty = true;


    public void produce(int toProduce, Person person){
        try {
            lock.lock();
            System.out.println(person.introduce() + " want to produce " + toProduce + " Value = " + this.value);
            while (!isFirstProducerEmpty) {
//            while (lock.hasWaiters(firstProducerCond)) {
                    System.out.println(person.introduce() + " is waiting on lab2.lab3.Producer\n");
                    producerCond.await();
            }
            isFirstProducerEmpty = false;
            while (!canProduce(toProduce)) {
                System.out.println(person.introduce() + " is waiting on first Producers\n");
                firstProducerCond.await();
            }

//            2Cond
//            while (!canProduce(toProduce)) {
//                System.out.println(person.introduce() + " is waiting on producers\n");
//                producerCond.await();
//            }
            this.value += toProduce;
            System.out.println(person.introduce() + " Produced " + toProduce + "\n");

            isFirstProducerEmpty = true;
            firstConsumerCond.signal();
            producerCond.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void consume(int toConsume, Person person){
        try {
            lock.lock();
            System.out.println(person.introduce() + " want to consume " + toConsume + " Value = " + this.value);
            while (!isFirstConsumerEmpty) {
//            while (lock.hasWaiters(firstConsumerCond)) {
                System.out.println(person.introduce() + " is waiting on Consumer\n");
                consumerCond.await();
            }
            isFirstConsumerEmpty = false;
            while (!canConsume(toConsume)) {
                System.out.println(person.introduce() + " is waiting on first Consumers\n");
                firstConsumerCond.await();
            }
//            2Cond
//            while (!canConsume(toConsume)) {
//                System.out.println(person.introduce() + " is waiting on Consumers\n");
//                consumerCond.await();
//            }
            this.value -= toConsume;
            System.out.println(person.introduce() + " Consumed " + toConsume + "\n");

            isFirstConsumerEmpty = true;
            firstProducerCond.signal();
            consumerCond.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
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

