package lab5;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
public class Main {
//    ["n", "bound", "time_of_run", "iterations"]
    public static void main(String[] args) {
        World world = new World(15);
        int bound = 7;
        int n = 1;

        for (int i = 0; i < n; i++){
            Client client = new Client(world, bound, 100);
            client.addWorker();
            Producer producer = new Producer(world, bound, 100);
            producer.addWorker();
        }

        Person.startWork();
        Runtime.getRuntime().addShutdownHook(new Thread(Person::killWorkers));

    }

}