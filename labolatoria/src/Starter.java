import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Starter {
    private int n, bound, bufferSize, endValue;
    private EndType endType;
    private SymulationType symulationType;
    public Starter(
            int n, int bound, int bufferSize, EndType endType, int endValue, SymulationType symulationType
    ){
        this.n = n;
        this.bound = bound;
        this.endType = endType;
        this.bufferSize = bufferSize;
        this.endValue = endValue;
        this.symulationType = symulationType;
    }
    public void clear(){}
    public void startSymulation(){
        Object world = symulationType.getWorld(bufferSize);

        int iterations = endType.getIterations(endValue);

        symulationType.createWorkers(bound, n, world, iterations);


        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Przerwano nieskończoną pętlę po określonym czasie.");
                System.exit(0); // Opcjonalne: zakończ program po przerwaniu pętli
            }
        };

        endType.startWork(symulationType, timer, task, endValue);
    }
}
