/**
 * Created by xu on 2017/4/16.
 */

import java.util.ArrayList;

public class Server {
    // 暂假设服务容量是无限的
    //public static final int QUEUE_SIZE = 100;
    private ServerState state = ServerState.IDLE;
    private ArrayList<Customer> customerArray = new ArrayList<>();
    private ArrayList<Customer> waitQueue = new ArrayList<>();
    private int delayedCustomerNumber = 0;

    public void setServerState(ServerState state) {
        this.state = state;
    }

    public ServerState getServerState() {
        return this.state;
    }

    public void addDelayedCustomerNumber() {
        this.delayedCustomerNumber++;
    }

    public int getDelayedCustomerNumber() {
        return this.delayedCustomerNumber;
    }

    public void customerArrive(double arrivingTime) {
        Customer customer = new Customer(customerArray.size(), arrivingTime);
        customerArray.add(customer);
        if (state == ServerState.BUSY) {
            waitQueue.add(customer);
        }
        // 顺序影响业务逻辑
        if (state == ServerState.IDLE) {
            state = ServerState.BUSY;
        }
    }

    public void customerDepart(double departingTime) {
        if (waitQueue.isEmpty()) {
            state = ServerState.IDLE;
        } else {
            Customer customer = waitQueue.get(0);
            customer.setDepartureTime(departingTime);
            waitQueue.remove(0);
        }
    }

    public int getWaitQueueNumber() {
        return this.waitQueue.size();
    }

    /**
     * 单元测试
     */
    public static void main(String[] args) {
        Server server = new Server();
        System.out.println(server.getWaitQueueNumber());
        System.out.println(server.getServerState());
    }
}
