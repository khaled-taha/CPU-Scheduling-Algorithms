package scheduling;

public class Job {

    int ProcessStartTime = -1;
    int ProcessCompletionTime;
    int processArrivalTime;
    int waitingTime = 0;
    int turnAroundTime;
    int cpuTime, processNO;

    int remainingTime;
    boolean finishStatus = false;
    
    int processPriority;

    public Job(int processNO, int processArrivalTime, int cpuTime, int processPriority) {
        this.processNO = processNO;
        this.processArrivalTime = processArrivalTime;
        this.cpuTime = cpuTime;
        this.processPriority = processPriority;
    }

    public Job(int processNO, int processArrivalTime, int cpuTime) {
        this.processNO = processNO;
        this.processArrivalTime = processArrivalTime;
        this.cpuTime = cpuTime;        
    }
    
    int getProcessPriority() {
        return processPriority;
    }
    /*
    Hence  Priority is fixed in Priority Algorithm then there is no meaning of access processPriority to change its value...
    */

    int getTurnAroundTime() {
        this.calculateTurnAroundTime();
        return turnAroundTime;
    }

    void calculateTurnAroundTime() {
        this.turnAroundTime = this.ProcessCompletionTime - processArrivalTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void calculateWaitingTime() {
        this.waitingTime = this.getTurnAroundTime() - this.getCpuTime();
    }

    public boolean getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(boolean finishStatus) {
        this.finishStatus = finishStatus;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        /*the parameter  passed should be validated to protect hacking th remaining time of any Process*/
        this.remainingTime = remainingTime;
    }

    public int getProcessStartTime() {
        return ProcessStartTime;

    }

    public void setProcessStartTime(int ProcessStartTime) {
        this.ProcessStartTime = ProcessStartTime;
    }

    public int getProcessCompletionTime() {
        return ProcessCompletionTime;
    }

    public void setProcessCompletionTime(int ProcessCompletionTime) {
        this.ProcessCompletionTime = ProcessCompletionTime;
    }

    public int getprocessArrivalTime() {
        return processArrivalTime;
    }

    /*
    Not applicable to chaned the Process arrival time for a founded Process so we can delete setArrivalTime()
     */
    public int getCpuTime() {
        return cpuTime;
    }

    /*
    Not applicable to chaned the CPU time for a founded Process so we can delete setCPUtime()
     */
    public int getProcessNO() {
        return processNO;
    }

    /*
    Not applicable to chaned the Process Number for a founded Process so we can delete setProcessNO()
     */
}
