public class Client implements Runnable{
    private World world;
    Client (World world){
        this.world = world;
    }

    @Override
    public void run() {
        while (true){
            world.consume();
            System.out.println("Consumed");
        }
    }
}
