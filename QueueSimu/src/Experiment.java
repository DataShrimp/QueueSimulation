/**
 * Created by xu on 2017/4/16.
 */
public class Experiment {
    double meanInterArrival = 0.0;
    double meanService = 0.0;
    double numDelaysRequired = 0.0;

    public Experiment(double meanInterArrival, double meanService, double numDelaysRequired) {
        this.meanInterArrival = meanInterArrival;
        this.meanService = meanService;
        this.numDelaysRequired = numDelaysRequired;
    }
}
