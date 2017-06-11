/**
 * Created by xu on 2017/4/16.
 */
public class Customer {
    private int id = 0;
    private double arriveTime = 0.0;
    private double departureTime = 0.0;

    public Customer(int id, double arriveTime) {
        this.id = id;
        this.arriveTime = arriveTime;
    }

    public void setDepartureTime(double time) {
        this.departureTime = time;
    }
}
