import javax.swing.plaf.synth.SynthLookAndFeel;

public class World {
    private static int value = 0;

    public synchronized void produce(){
        while (World.canConsume()){
            try {
//                System.out.println("wait: produce");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        World.value = 1;
        notifyAll();
    }

    public synchronized void consume(){
        while (!World.canConsume()){
            try {
//                System.out.println("wait: consume");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        World.value = 0;
        notifyAll();
    }

    private static boolean canConsume(){
        return (value == 1);
    }
}
