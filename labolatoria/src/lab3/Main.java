package lab3;


public class Main {
    public static void main(String[] args) {
        World world = new World(15);
        int bound = 7;
        int n = 1;

        for (int i = 0; i < n; i++){
            Client client = new Client(world, bound);
            client.startWork();
            Producer producer = new Producer(world, bound);
            producer.startWork();
        }
        Client client = new Client(world, 2);
        client.startWork();
        Runtime.getRuntime().addShutdownHook(new Thread(Person::killWorkers));

    }
}