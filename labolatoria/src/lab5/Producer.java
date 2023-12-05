package lab5;

public class Producer extends Person {
    static int lastId = 0;
    private int id;
    public Producer(World world, int bound) {
        this(world, bound, 0);
    }
    public Producer(World world, int bound, int iterations) {
        super(world, bound, iterations);
        id = lastId + 1;
        lastId += 1;
    }

    @Override
    public void run(){
        int i = iterations;
        while (shouldWork) {
            int toProduce = 1 + super.random.nextInt(bound - 1);
            super.world.produce(toProduce, this);
            runs++;
            spentValue += toProduce;

            if(iterations != 0)
                if(i-- == 0)
                    Person.stopWorkers();
        }
    }

    @Override
    public String introduce() {
        return "P" + id;
    }
}
