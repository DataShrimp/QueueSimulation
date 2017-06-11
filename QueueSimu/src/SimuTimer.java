import java.awt.*;
import java.util.ArrayList;

/**
 * Created by xu on 2017/4/16.
 */

public class SimuTimer {
    private double simuTime = 0.0;
    private EventType nextEventType = null;

    public void timing(ArrayList<SimuEvent> nextEvents) {
        double minNextEventTime = 1e29;

        for (SimuEvent event: nextEvents) {
            if (event.getEventTime() < minNextEventTime) {
                minNextEventTime = event.getEventTime();
                nextEventType = event.getEventType();
            }
        }

        this.simuTime = minNextEventTime;
    }

    public double getSimuTime() {
        return this.simuTime;
    }

    public EventType getNextEventType() {
        return this.nextEventType;
    }

    /**
     * 单元测试
     */
    public static void main(String[] args) {
        SimuTimer timer = new SimuTimer();
        ArrayList<SimuEvent> events= new ArrayList<>();
        events.add(new SimuEvent(EventType.ARRIVAL));
        events.add(new SimuEvent(EventType.DEPARTURE));
        timer.timing(events);
        System.out.println(timer.getSimuTime());
    }
}
