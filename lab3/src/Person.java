import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Person extends Thread {
    private static final List<Person> peoples = new ArrayList<>();
    protected final World world;
    protected int runs = 0;
    protected Random random = new Random();
    protected int bound;
    public Person(World world, int bound){
        this.world = world;
        this.bound = bound;
    }

    public void startWork(){
        peoples.add(this);
        this.start();
    }

    @Override
    public abstract void run();
    public abstract String introduce();
    public String stats(){
        return introduce() + " runs " + runs + " times";
    }

    public static void killWorkers() {
        for(Person person : peoples){
            person.interrupt();
        }
        for (Person person : peoples){
            System.out.println(person.stats());
        }
    }

}

