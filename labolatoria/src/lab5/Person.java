package lab5;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Person extends Thread {
    public static List<Integer> stats = new ArrayList<>();
    private static final List<Person> peoples = new ArrayList<>();
    private static long startTime;
    private static long endTime;
    protected boolean shouldWork = true;
    protected int iterations;

    protected final World world;
    protected int runs = 0;
    protected int spentValue = 0;
    protected Random random = new Random();
    protected int bound;
    public Person(World world, int bound){
        this(world, bound, 0);
    };
    public Person(World world, int bound, int iterations){
        this.world = world;
        this.bound = bound;
        this.iterations = iterations;
    }

    public void addWorker(){
        peoples.add(this);
    }

    @Override
    public abstract void run();
    public abstract String introduce();
    public static void startWork(){
        startTime = System.currentTimeMillis();

        for(Person person : peoples){
            person.start();
        }
    }
    public String stats(){
        return introduce() + " runs " + runs + " times, spent " + spentValue;
    }

    public static void stopWorkers(){
        for(Person person : peoples){
            person.shouldWork = false;
        }
    }

    public static void killWorkers() {
        long cpuTime =0;
        for(Person person : peoples){
            cpuTime += getCpuTime(person.getId());
            person.interrupt();
        }
        long endTime = System.currentTimeMillis();
        int allSpent = 0;
        int allRuns = 0;
        for (Person person : peoples){
            allSpent += person.spentValue;
            allRuns += person.runs;
            System.out.println(person.stats());
        }
        stats.addAll(List.of(new Integer[]{(int) (endTime - startTime), (int) cpuTime, allSpent, allRuns}));
        System.out.println("Time: " + (endTime - startTime) + " ms\nCpu Time" + (cpuTime) + "ns" +
                "\nTotal value buffer change: " + allSpent + "\nTotal runs: " + allRuns);
        System.out.println(stats);
    }
    private static long getCpuTime(long id) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isThreadCpuTimeSupported() ?
                bean.getThreadCpuTime(id) : 0L;
    }
}

