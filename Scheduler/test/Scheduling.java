
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import scheduling.*;

/**
 * @author Ayman Saleh, Khaled Taha, Mohmoud Ebrahim, Abdullah Abullwafa
 * These are Scheduling Algorithms for a single processing core.
 * First Come, First Served (FCFS)          --> processId, arrivalTime, cpuTime
 * Priority Scheduling                      --> processId, arrivalTime, cpuTime, priority
 * Round Robin (RR)                         --> processId, arrivalTime, cpuTime
 * Shortest Remaining Time (Preemptive SRT) --> processId, arrivalTime, cpuTime
 */
public class Scheduling {

    static final String FILE_NAME = "ProcessData.txt";
    static String allocationStrategy;
    static BufferedReader br;

    public Scheduling() {
        try {
            br = new BufferedReader(new FileReader(FILE_NAME));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        run();
    }

    public static void priority(ArrayList<Job> jobList, String fileName) {
        try {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                String a[] = sCurrentLine.split(",");
                int processId = new Integer(a[0]);
                int priority = new Integer(a[1]);
                int cpuTime = new Integer(a[2]);
                int arrivalTime = new Integer(a[3]);
                Job job = new Job(processId, arrivalTime, cpuTime, priority);
                jobList.add(job);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        Priority priority = new Priority(jobList);
        priority.run();
    }

    public static void FCFS(ArrayList<Job> jobList, String fileName) {
        try {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                String a[] = sCurrentLine.split(",");
                int processId = new Integer(a[0]);
                int cpuTime = new Integer(a[1]);
                int arrivalTime = new Integer(a[2]);
                Job job = new Job(processId, arrivalTime, cpuTime);
                jobList.add(job);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        FCFS FCFS_obj = new FCFS(jobList);
        FCFS_obj.run();
    }

    public static void SRT(ArrayList<Job> jobList, String fileName) {
        try {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                String a[] = sCurrentLine.split(",");
                int processId = new Integer(a[0]);
                int cpuTime = new Integer(a[1]);
                int arrivalTime = new Integer(a[2]);
                Job job = new Job(processId, arrivalTime, cpuTime);
                jobList.add(job);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        SRT SJF_obj = new SRT(jobList);
        SJF_obj.run();
    }

    public static void RR(ArrayList<Job> jobList, String fileName) {
        try {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                String a[] = sCurrentLine.split(",");
                int processId = new Integer(a[0]);
                int cpuTime = new Integer(a[1]);
                int arrivalTime = new Integer(a[2]);
                Job job = new Job(processId, arrivalTime, cpuTime);
                jobList.add(job);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        RR RR_obj = new RR(jobList, 4);
        RR_obj.run();
    }

    public static void intro() {
        System.out.println("Algorihm\t [Press]");
        System.out.println("FCFS\t [1]");
        System.out.println("SJF\t [2]");
        System.out.println("RR\t [3]");
        System.out.println("Priority\t [4]");
    }

    public static void run() {
        new Scheduling();
        ArrayList<Job> jobList = new ArrayList<>();
        intro();
        //get num
        try {
            int number = new Scanner(System.in).nextInt();
            switch (number) {
                case 1: {
                    FCFS(jobList, FILE_NAME);
                    break;
                }
                case 2: {
                    SRT(jobList, FILE_NAME);
                    break;
                }
                case 3: {
                    RR(jobList, FILE_NAME);
                    break;
                }
                case 4: {
                    priority(jobList, FILE_NAME);
                    break;
                }
                default: {
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException | InputMismatchException ex) {
            System.out.println("Invalid Input...");
            run();
        }
    }
}
