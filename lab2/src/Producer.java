import java.nio.channels.WritePendingException;

public class Producer implements Runnable{
    private World world;
    Producer (World world){
        this.world = world;
    }

    @Override
    public void run() {
        while (true){
            world.produce();
            System.out.println("Produced");
        }
    }
}
