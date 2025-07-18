public class Process {
    public int pid, arrival, burst, remaining, completion, turnaround, response, queueLevel;

    public Process(int pid, int arrival, int burst) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
        this.remaining = burst;
        this.completion = 0;
        this.turnaround = 0;
        this.response = -1;
        this.queueLevel = 0;
    }

    @Override
    public String toString() {
        return "P" + pid + " (Arrival: " + arrival + ", Burst: " + burst + ")";
    }
}
