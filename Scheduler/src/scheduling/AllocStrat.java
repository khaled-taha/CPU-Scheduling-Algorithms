
package scheduling;

import java.util.List;
public abstract class AllocStrat {
     List<Job> Jobs;

    public AllocStrat(List<Job> jobs) {
        super();
        Jobs = jobs;
    }

    public abstract void run();
    public abstract void display();

}
