package lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        introduceThreads();
        incrementWithTreads();
    }

    private static void introduceThreads(){
        // Uruchamiamy wątek dziedzicząc z klasy Thread
        Thread thread1 = new MyThread(1);
        thread1.start();

        // Uruchamiamy wątek implementując interfejs Runnable
        Thread thread2 = new Thread(new MyRun(2));
        thread2.start();

        // Uruchamiamy wątek przy użyciu ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(new MyRun(3));

        // Zamykamy ExecutorService po zakończeniu pracy
        executor.shutdown();
    }

    private static void incrementWithTreads(){
        int n = 1000;
        int lastId = 0;
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < n; i++){
            threads.add(new Thread(new MyRunIncrement(lastId, 1)));
            lastId += 1;
            threads.add(new Thread(new MyRunIncrement(lastId, -1)));
            lastId += 1;
        }
        for (Thread e : threads){
            e.start();
        }
        MyRunIncrement.printValue();
    }
}

// czym jest wedł monitor wg bibliografia punkt 1
// synhronize notify() wait()
// trzy sposoby urochomienia wątków dlaczego i kiedy