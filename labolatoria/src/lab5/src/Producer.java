package lab5.src;

public class Producer extends Person {
    static int lastId = 0;
    private int id;
    public Producer(World world, int bound) {
        super(world, bound);
        id = lastId + 1;
        lastId += 1;
    }

    @Override
    public void run(){
        while (true) {
            int toProduce = 1 + super.random.nextInt(bound - 1);
            super.world.produce(toProduce, this);
            runs++;
        }
    }

    @Override
    public String introduce() {
        return "P" + id;
    }
}
