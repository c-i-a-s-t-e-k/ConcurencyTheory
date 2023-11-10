import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class World {
    private int lines = 0;
    private int value = 0;
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
            while (!canProduce(toProduce)){
                if (isFirstProducerEmpty){
                    firstProducerCond.await();
                    isFirstProducerEmpty = false;
                }else {
                    producerCond.await();
                }
                System.out.println(person.introduce() + " is waiting");
                newLine();
            }
            this.value += toProduce;
            if (isFirstConsumerEmpty){
                consumerCond.signal();
                isFirstConsumerEmpty = true;
            }
            else {
                firstConsumerCond.signal();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

    public void consume(int toConsume, Person person){
        try {
            lock.lock();
            while (!canConsume(toConsume)){
                if (isFirstConsumerEmpty){
                    isFirstConsumerEmpty = false;
                    firstConsumerCond.await();
                }
                else{
                    consumerCond.await();
                }
                System.out.println(person.introduce() + " is waiting");
                newLine();
            }
            this.value -= toConsume;
            if (isFirstProducerEmpty){
                producerCond.signal();
                isFirstProducerEmpty = true;
            }
            else {
                firstProducerCond.signal();
            }
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

    public void newLine(){
        try {
            lock.lock();
            lines += 1;
            if (lines % 5 == 0){
                System.out.println();
            }
        } finally {
            lock.unlock();
        }
    }

}

