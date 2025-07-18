import java.util.*;

public class FCFS implements Scheduler {
    @Override
    public List<String> run(List<Process> processes) {
        List<String> gantt = new ArrayList<>();
        processes.sort(Comparator.comparingInt(p -> p.arrival));
        int time = 0;

        for (Process p : processes) {
            if (time < p.arrival) {
                while (time < p.arrival) {
                    gantt.add("Idle");
                    time++;
                }
            }

            p.response = time - p.arrival;
            for (int i = 0; i < p.burst; i++) {
                gantt.add("P" + p.pid);
                time++;
            }
            p.completion = time;
            p.turnaround = p.completion - p.arrival;
        }

        return gantt;
    }
}
