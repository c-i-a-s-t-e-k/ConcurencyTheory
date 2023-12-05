import java.util.Timer;
import java.util.TimerTask;

public enum EndType {
    ITERATIONS, TIME_OF_RUNNING;

    public int getIterations(int endValue) {
        switch (this) {
            case ITERATIONS -> {
                return endValue;
            }
            case TIME_OF_RUNNING -> {
                return 0;
            }
        }
        throw new EnumConstantNotPresentException(this.getDeclaringClass(), "not such enum");
    }

    public void startWork(SymulationType symulationType, Timer timer, TimerTask task, int endValue) {
        switch (this) {
            case ITERATIONS -> {
                symulationType.startWork();
            }
            case TIME_OF_RUNNING -> {
                timer.schedule(task, endValue);
                symulationType.startWork();
            }
        }
    }
}
