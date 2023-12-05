
import java.util.List;

public enum SymulationType {
    LAB3, LAB5;

    public Object getWorld(int bufferSize){
        switch (this){
            case LAB3:
                return new lab3.World(bufferSize);
            case LAB5:
                return new lab5.World(bufferSize);
        }
        return null;
    }

    public void createWorkers(int bound, int n, Object worldObj,int iterations){
        switch (this){
            case LAB3:
                lab3.World world2 = (lab3.World) worldObj;
                for (int i = 0; i < n; i++){
                    lab3.Client client = new lab3.Client(world2, bound, iterations);
                    client.addWorker();
                    lab3.Producer producer = new lab3.Producer(world2, bound, iterations);
                    producer.addWorker();
                }
                break;
            case LAB5:
                lab5.World world1 = (lab5.World) worldObj;
                for (int i = 0; i < n; i++){
                    lab5.Client client = new lab5.Client(world1, bound, iterations);
                    client.addWorker();
                    lab5.Producer producer = new lab5.Producer(world1, bound, iterations);
                    producer.addWorker();
                }
                break;
        }
    }

    public void startWork(){
        switch (this){
            case LAB3:
                lab3.Person.startWork();
                Runtime.getRuntime().addShutdownHook(new Thread(lab3.Person::killWorkers));
                break;
            case LAB5:
                lab5.Person.startWork();
                Runtime.getRuntime().addShutdownHook(new Thread(lab5.Person::killWorkers));
                break;
        }
    }
    public List<Integer> getStats(){
        switch (this){
            case LAB3 -> {
                return lab3.Person.stats;
            }
            case LAB5 -> {
                return lab5.Person.stats;
            }
        }
        return null;
    }
}

