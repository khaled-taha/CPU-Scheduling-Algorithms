package scheduling;

import java.util.List;

/**
 * Priority Scheduling
 * @author Ayman Saleh, Khaled Taha, Mohmoud Ebrahim, Abdullah Abullwafa
 */
public class Priority extends AllocStrat {

    int timeLine;
    int counter;
    double procAvgWaitingTime = 0;
    double procAvgTurnAroundTime = 0;

    int remainigProcessToFinish = 0;
    int ProcessesNum = 0;

    int pointerToFinished = 0;
    int[] finishedProcess;
    int[] numberOfJoins;
    
    public Priority(List<Job> jobs) {
        super(jobs);
        /*------------   initialize the remaining time, finish status   -------------*/
        this.remainigProcessToFinish = super.Jobs.size();
        this.ProcessesNum            = this.remainigProcessToFinish;
        this.finishedProcess         = new int[ProcessesNum];
        this.numberOfJoins           = new int[ProcessesNum];
    }
    
    @Override
    public void run() {
        
        this.counter=0;
        int preTimeLine;
        
        //loop on all 
        
        /*Sorting jobs by Priority*/
        super.Jobs.sort((Job o1, Job o2) -> o1.getProcessPriority()- o2.getProcessPriority());
        
        //Loop on all jobs till finish all jobs (loop again to continue or finish Process)...
        while (this.remainigProcessToFinish != 0) {
            preTimeLine = this.timeLine;
            //Loop on all jobs one time...
            /*reset counter for each itteration */
            for(counter = 0; counter < this.ProcessesNum; counter++) {
         
                /*----- check if the *High priority* process is found AND is not finished yes   -----------*/
                if ((this.Jobs.get(counter).getprocessArrivalTime() <= this.timeLine) && 
                    (this.Jobs.get(counter).getFinishStatus() == false)){

                    /*check if it is the frist time joning the CPU or not*/
                    if (this.Jobs.get(counter).getProcessStartTime() == -1) {
                        this.Jobs.get(counter).setProcessStartTime(this.timeLine);
                        this.numberOfJoins[counter]++;
                    }

                    this.Jobs.get(counter).setFinishStatus(true);
                    /*----save the finished processes in order -----*/
                    this.finishedProcess[this.pointerToFinished] = this.Jobs.get(counter).getProcessNO();

                    System.out.print("Process: " + Jobs.get(counter).getProcessNO() + "\t, joined at: " + this.timeLine + "\t to ");
                    this.timeLine = this.timeLine + Jobs.get(counter).getCpuTime();
                    System.out.println(this.timeLine);
                    this.Jobs.get(counter).setProcessCompletionTime(this.timeLine);
                    this.Jobs.get(counter).calculateWaitingTime();
                    this.procAvgWaitingTime += Jobs.get(counter).getWaitingTime();
                    this.procAvgTurnAroundTime += Jobs.get(counter).getTurnAroundTime();

                    this.pointerToFinished++;
                    this.remainigProcessToFinish--;
                    /*To rescan the Job List after finishing executing the last Process*/
                    counter = -1;
                }
                /* else Current job is (not arrived yet or finished processed)*/
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
        System.out.println("------------------------------------------ Summary  ----------------------------------------");
        System.out.println("------------------------------------------ Priority ----------------------------------------\n\n\n");
        this.procAvgWaitingTime /= this.ProcessesNum;
        this.procAvgTurnAroundTime /= this.ProcessesNum;
        //
        System.out.println("__________________________________________________________________________");
        System.out.println("__________________________________________________________________________");
        System.out.println("||Process NO ,\tTurn around time ,\tWaiting time ,\tjoinTimes\t||");
        
        //Loop on all jobs one time...
        for (Job jobs : this.Jobs) {
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
