import java.util.*;

public class SRTF implements Scheduler {
    @Override
    public List<String> run(List<Process> processes) {
        List<String> gantt = new ArrayList<>();
        processes.sort(Comparator.comparingInt(p -> p.arrival));
        int time = 0, completed = 0, n = processes.size();
        Process current = null;
        int lastPid = -1;

        while (completed < n) {
            Process shortest = null;
            int minRemaining = Integer.MAX_VALUE;

            for (Process p : processes) {
                if (p.arrival <= time && p.remaining > 0 && p.remaining < minRemaining) {
                    shortest = p;
                    minRemaining = p.remaining;
                }
            }

            if (shortest == null) {
                gantt.add("Idle");
                time++;
                continue;
            }

            if (shortest.pid != lastPid) {
                lastPid = shortest.pid;
            }

            if (shortest.remaining == shortest.burst) {
                shortest.response = time - shortest.arrival;
            }

            gantt.add("P" + shortest.pid);
            shortest.remaining--;
            time++;

            if (shortest.remaining == 0) {
                shortest.completion = time;
                shortest.turnaround = shortest.completion - shortest.arrival;
                completed++;
            }
        }

        return gantt;
    }
}
