/**
 * Created by xu on 2017/4/16.
 */

public class RandomGenerator {
    public static double getExpon(double mean) {
        return -1*mean*Math.log(Math.random());
    }

    public static void main(String[] args) {
        System.out.println(RandomGenerator.getExpon(3));
    }
}
