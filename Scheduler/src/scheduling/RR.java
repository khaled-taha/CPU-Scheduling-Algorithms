package scheduling;

import java.util.List;

/**
 * Round Robin (RR)
 * @author Ayman Saleh, Khaled Taha, Mohmoud Ebrahim, Abdullah Abullwafa
 */
public class RR extends AllocStrat {

    int timeLine;
    int counter;
    double procAvgWaitingTime = 0;
    double procAvgTurnAroundTime = 0;

    int remainigProcessToFinish = 0;
    int ProcessesNum = 0;
    int timeQuantity;
    int pointerToFinished = 0;
    int[] finishedProcess;
    int[] numberOfJoins;

    
    public RR(List<Job> jobs, int quantitiy) {
        super(jobs);
        this.timeQuantity = quantitiy;
        this.remainigProcessToFinish = super.Jobs.size();
        this.ProcessesNum = this.remainigProcessToFinish;
        this.finishedProcess = new int[ProcessesNum];
        this.numberOfJoins = new int[ProcessesNum];
        /*------------   initialize the remaining time, finish status   -------------*/
        for (Job jobTemp : Jobs) {
            jobTemp.setRemainingTime(jobTemp.getCpuTime());
        }
    }

    @Override
    public void run() {
        this.counter=0;
        int preTimeLine;
        
        /*Sorting jobs by Process Arrival.*/
        super.Jobs.sort((Job o1, Job o2) -> o1.getprocessArrivalTime()- o2.getprocessArrivalTime());
        
        //loop on all 
        //Loop on all jobs till finish all jobs (loop again to continue or finish Process)...
        while (this.remainigProcessToFinish != 0) {
            preTimeLine = this.timeLine;
            //Loop on all jobs one time...
            /*reset counter for each itteration */
            counter = 0;
            for (Job jobs : Jobs) {
                int lastReaming;
                /*----- check if the process is found AND is not finished yes   -----------*/
                if ((jobs.getprocessArrivalTime() <= this.timeLine) && (jobs.getFinishStatus() == false)) {

                    /*check if it is the frist time joning the CPU or not*/
                    if (jobs.getProcessStartTime() == -1) {
                        jobs.setProcessStartTime(this.timeLine);
                        this.numberOfJoins[counter]++;
                    } /*    Not the frist time to join the CPU      */ 
                    else {
                        this.numberOfJoins[counter]++;
                    }

                    /*--------- Simulate execute this process by subtract the remaing time by the RR quantitiy   ----------------*/
                    lastReaming = jobs.getRemainingTime();
                    int tempCalculation = lastReaming - this.timeQuantity;
                    /* Validate the Remainig time */
                    if (tempCalculation > 0) {
                        jobs.setRemainingTime(tempCalculation);
                        System.out.print("Process: " + jobs.getProcessNO() + "\t, joined at: " + this.timeLine + "\t to ");
                        this.timeLine += this.timeQuantity;
                        System.out.println(this.timeLine);
                    }
                    else {
                        jobs.setRemainingTime(0);
                        jobs.setFinishStatus(true);
                        /*----save the finished processes in order -----*/
                        this.finishedProcess[this.pointerToFinished] = jobs.getProcessNO();
                        
                        System.out.print("Process: " + jobs.getProcessNO() + "\t, joined at: " + this.timeLine + "\t to ");
                        this.timeLine = this.timeLine + lastReaming;//Error Logic (- the lasr remain)
                        System.out.println(this.timeLine);
                        jobs.setProcessCompletionTime(this.timeLine);
                        jobs.calculateWaitingTime();
                        this.procAvgWaitingTime += jobs.getWaitingTime();
                        this.procAvgTurnAroundTime += jobs.getTurnAroundTime();
                        
                        this.pointerToFinished++;
                        this.remainigProcessToFinish--;
                    }
                }
                /*Current job is (not arrived yet or finished processed)*/
                counter++;
            }
            if(this.timeLine == preTimeLine) this.timeLine += 1;
        }
        
        /*Sorting jobs by Process No.*/
        super.Jobs.sort((Job o1, Job o2) -> o1.getProcessNO()- o2.getProcessNO());

        this.display();
    }

    @Override
    public void display() {
        
        this.counter=0;
        
        System.out.println("\n\n\n--------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------ Summary -----------------------------------------");
        System.out.println("------------------------------------------   RR    -----------------------------------------\n\n\n");
        this.procAvgWaitingTime /= this.ProcessesNum;
        this.procAvgTurnAroundTime /= this.ProcessesNum;
        //
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
