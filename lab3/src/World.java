import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class World {
    private int value = 14;
    private final int bufferSize;
    private final Lock lock = new ReentrantLock();
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
//            while (!isFirstProducerEmpty) {
//                System.out.println(person.introduce() + " is waiting on Producer\n");
//                producerCond.await();
//            }
//            while (!canProduce(toProduce)) {
//                System.out.println(person.introduce() + " is waiting on first Producers\n");
//                isFirstProducerEmpty = false;
//                firstProducerCond.await();
//            }

            while (!canProduce(toProduce)) {
                System.out.println(person.introduce() + " is waiting on producers\n");
                producerCond.await();
            }
            this.value += toProduce;
            System.out.println(person.introduce() + " Produced " + toProduce + "\n");

//            isFirstConsumerEmpty = true;
            consumerCond.signal();
//            firstConsumerCond.signal();
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
//            while (!isFirstConsumerEmpty) {
//                System.out.println(person.introduce() + " is waiting on Consumer\n");
//                consumerCond.await();
//            }
//            while (!canConsume(toConsume)) {
//                System.out.println(person.introduce() + " is waiting on first Consumers\n");
//                isFirstConsumerEmpty = false;
//                firstConsumerCond.await();
//            }
            while (!canConsume(toConsume)) {
                System.out.println(person.introduce() + " is waiting on Consumers\n");
//                isFirstConsumerEmpty = false;
                consumerCond.await();
            }
            this.value -= toConsume;
            System.out.println(person.introduce() + " Consumed " + toConsume + "\n");

//            isFirstProducerEmpty = true;
            producerCond.signal();
//            firstProducerCond.signal();
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

