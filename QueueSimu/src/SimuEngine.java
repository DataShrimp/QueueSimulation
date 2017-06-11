/**
 * Created by xu on 2017/4/16.
 */

import java.util.ArrayList;

public class SimuEngine {
    // 仿真钟
    private SimuTimer simuTimer= null;
    // 仿真模型
    private Server server = null;
    // 事件表
    private ArrayList<SimuEvent> nextEvents = null;
    // 最后事件时间
    private double lastEventTime = 0.0;
    // TODO: 统计模块
    // 仿真实验设置
    private Experiment exp = null;

    public SimuEngine(Experiment exp) {
        this.exp = exp;
    }

    public void initialize() {
        simuTimer = new SimuTimer();
        server = new Server();
        nextEvents = new ArrayList<>();
        for (EventType event: EventType.values()) {
            nextEvents.add(new SimuEvent(event));
        }
        // 初始化事件时间
        double timeForArrival = RandomGenerator.getExpon(exp.meanInterArrival);
        setNextEventTime(EventType.ARRIVAL, timeForArrival);
        setNextEventTime(EventType.DEPARTURE, 1e30);
    }

    public void run() {
        if (this.simuTimer == null) {
            System.out.println("错误：仿真引擎未初始化");
            return;
        }

        for (int i=0; i<exp.numDelaysRequired; i++){
            // 仿真时间运转
            simuTimer.timing(nextEvents);
            // TODO：统计中间指标
            // 事件处理
            switch (simuTimer.getNextEventType()) {
                case ARRIVAL:
                    arrivalEventHandler();
                    break;
                case DEPARTURE:
                    departureEventHandler();
                    break;
            }
            // 测试打印仿真中间过程
            System.out.println(i);
            System.out.println("Wait Queue: " + server.getWaitQueueNumber());
            System.out.println(getNextEventTime());
        }
    }

    private void arrivalEventHandler() {
        // 更新仿真系统状态
        double arrivalTime = simuTimer.getSimuTime() + RandomGenerator.getExpon(exp.meanInterArrival);
        setNextEventTime(EventType.ARRIVAL, arrivalTime);

        if (server.getServerState() == ServerState.IDLE) {
            double departureTime = simuTimer.getSimuTime() + RandomGenerator.getExpon(exp.meanService);
            setNextEventTime(EventType.DEPARTURE, departureTime);
        }

        // 仿真逻辑
        server.customerArrive(simuTimer.getSimuTime());
    }

    private void departureEventHandler() {
        // 更新仿真系统状态
        if (server.getWaitQueueNumber() == 0) {
            setNextEventTime(EventType.DEPARTURE, 1e30);
        } else {
            double departureTime = simuTimer.getSimuTime() + RandomGenerator.getExpon(exp.meanService);
            setNextEventTime(EventType.DEPARTURE, departureTime);
        }

        // 仿真逻辑
        server.customerDepart(simuTimer.getSimuTime());
    }

    private void setNextEventTime(EventType type, double time) {
        for (SimuEvent event: this.nextEvents) {
            if (type == event.getEventType()) {
                event.setEventTime(time);
                break;
            }
        }
    }

    // 测试使用
    private String getNextEventTime() {
        String ret = "";
        for (SimuEvent event: this.nextEvents) {
            ret += event.getEventType().toString() + ":" + event.getEventTime() + "\n";
        }
        return ret;
    }

    /**
     * 单元测试
     * @param args
     */
    public static void main(String[] args) {
        Experiment exp = new Experiment(1.0, 0.5, 10);
        SimuEngine engine = new SimuEngine(exp);
        engine.initialize();
        engine.run();
    }
}
