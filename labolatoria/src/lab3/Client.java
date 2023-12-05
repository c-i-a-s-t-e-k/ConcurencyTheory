package lab3;


public class Client extends Person{
    static int lastId = 0;
    private int id;
    public Client(World world, int bound){
        this(world, bound, 0);
    }
    public Client(World world, int bound, int iterations) {
        super(world, bound, iterations);
        id = lastId + 1;
        lastId += 1;
    }

    @Override
    public void run(){
        int i = iterations;

        while (shouldWork){
            int toConsume = 1 + super.random.nextInt(bound - 1);
            super.world.consume(toConsume, this);
            runs ++;
            spentValue += toConsume;

            if(iterations != 0)
                if(i-- == 0)
                    Person.stopWorkers();
        }
    }

    @Override
    public String introduce() {
        return "C" + id;
    }
}


//na dwóch condition
//na 4 conditions