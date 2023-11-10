public class Client extends Person{
    static int lastId = 0;
    private int id;
    public Client(World world, int bound) {
        super(world, bound);
        id = lastId + 1;
        lastId += 1;
    }

    @Override
    public void run(){
        while (true){
            int toConsume = 1 + super.random.nextInt(bound - 1);
            super.world.consume(toConsume, this);
            System.out.println(introduce() + " Consumed " + toConsume);
            world.newLine();
            runs ++;
        }
    }

    @Override
    public String introduce() {
        return "C" + id;
    }
}


//na dwóch condition
//na 4 conditions