package scheduling;

import java.util.List;

/**
 * First Come, First Served (FCFS)
 * @author Ayman Saleh, Khaled Taha, Mohmoud Ebrahim, Abdullah Abullwafa
 */
public class FCFS extends AllocStrat {

    int timeLine;
    int counter;
    
    double procAvgWaitingTime;
    double procAvgTurnAroundTime;

    int remainigProcessToFinish;
    int ProcessesNum;
    int[] finishedProcess;
    int[] numberOfJoins;

    public FCFS(List<Job> jobs) {
        super(jobs);
    }

    @Override
    public void run() {
        this.counter = 0;
        this.timeLine = 0;

        //Sort by arrival;
        super.Jobs.sort((Job o1, Job o2) -> o1.getprocessArrivalTime() - o2.getprocessArrivalTime());
        
        //loop on all 
        /*------------   initialize the remaining time, finish status   -------------*/
        for (Job jobs : Jobs) {
            jobs.setFinishStatus(false);
            jobs.setRemainingTime(jobs.getCpuTime());
            this.ProcessesNum++;
        }
        this.remainigProcessToFinish = this.ProcessesNum;
        this.finishedProcess = new int[ProcessesNum];
        this.numberOfJoins = new int[ProcessesNum];

        while (remainigProcessToFinish != 0) {
            counter = 0;
            for (Job jobs : Jobs) {
                /*----- check if the process is found AND is not finished yes   -----------*/
                if ((jobs.getprocessArrivalTime() <= this.timeLine) && (jobs.getFinishStatus() == false)) {

                    /*check if it is the frist time joning the CPU or not*/
                    if (jobs.getProcessStartTime() == -1) {
                        jobs.setProcessStartTime(this.timeLine);
                        this.numberOfJoins[counter]++;
                    }

                    /*--------- Simulate execute this process by subtract the remaing time by the RR quantitiy   ----------------*/
                    jobs.setFinishStatus(true);
                    /*----save the finished processes in order -----*/
                    this.finishedProcess[counter] = jobs.getProcessNO();

                    System.out.print("Process: " + jobs.getProcessNO() + "\t, joined at: " + this.timeLine + "\t to ");
                    this.timeLine = this.timeLine + jobs.getCpuTime();//Error Logic (- the lasr remain)
                    System.out.println(this.timeLine);
                    jobs.setProcessCompletionTime(this.timeLine);
                    jobs.calculateWaitingTime();
                    this.procAvgWaitingTime += jobs.getWaitingTime();
                    this.procAvgTurnAroundTime += jobs.getTurnAroundTime();
                    remainigProcessToFinish--;
                }
                counter++;
            }
            /*  to prevent stuck in the idle time if time line */
            this.timeLine++;

        }
        
        //Sort by Process No.;
        super.Jobs.sort((Job o1, Job o2) -> o1.getProcessNO()- o2.getProcessNO());
        
        this.display();
    }

    @Override
    public void display() {
        
        this.counter = 0 ;
        
        System.out.println("\n\n\n--------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------ Summary -----------------------------------------");
        System.out.println("------------------------------------------  FCFS   -----------------------------------------\n\n\n");
        this.procAvgWaitingTime /= this.ProcessesNum;
        this.procAvgTurnAroundTime /= this.ProcessesNum;
        
        System.out.println("__________________________________________________________________________");
        System.out.println("__________________________________________________________________________");
        System.out.println("||Process NO ,\tTurn around time ,\tWaiting time ,\tjoinTimes\t||");
        
        //Loop on all jobs one time...
        for (Job jobs : Jobs) {
            jobs.calculateTurnAroundTime();
            jobs.calculateWaitingTime();
            System.out.print("||Process: " + jobs.getProcessNO());
            System.out.print("\tjoined at: " + jobs.getProcessStartTime());
            System.out.print("\t\twaited: " + jobs.getWaitingTime());
            System.out.println("\tjoined: " + this.numberOfJoins[counter] + " times\t||");
            counter++;
        }
        System.out.println("__________________________________________________________________________");
        System.out.println("__________________________________________________________________________");
        
        System.out.print("Average Waiting time: " + this.procAvgWaitingTime);
        System.out.println("\tAverage Turn Around Time : " + this.procAvgTurnAroundTime);

        System.out.println("Ordering Processes by the frist Compeletion");
        for (this.counter = 0; this.counter < this.ProcessesNum; this.counter++) {
            System.out.println("Finised: " + (this.counter + 1) + " Process No." + this.finishedProcess[this.counter]);
        }
    }

    
}
