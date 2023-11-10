import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        World world = new World(15);
        int bound = 3;

        for (int i = 0; i < 5; i++){
            Client client = new Client(world, bound);
            client.startWork();
            Producer producer = new Producer(world, bound);
            producer.startWork();
        }
        Client client = new Client(world, 7);
        client.startWork();
        Runtime.getRuntime().addShutdownHook(new Thread(Person::killWorkers));

    }
}