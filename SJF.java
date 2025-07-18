import java.util.*;

public class SJF implements Scheduler {
    @Override
    public List<String> run(List<Process> processes) {
        List<String> gantt = new ArrayList<>();
        List<Process> readyQueue = new ArrayList<>();
        processes.sort(Comparator.comparingInt(p -> p.arrival));
        int time = 0, completed = 0;
        int n = processes.size();
        boolean[] visited = new boolean[n];

        while (completed < n) {
            for (int i = 0; i < n; i++) {
                Process p = processes.get(i);
                if (p.arrival <= time && !visited[i]) {
                    readyQueue.add(p);
                    visited[i] = true;
                }
            }

            if (readyQueue.isEmpty()) {
                gantt.add("Idle");
                time++;
            } else {
                readyQueue.sort(Comparator.comparingInt(p -> p.burst));
                Process current = readyQueue.remove(0);
                current.response = time - current.arrival;
                for (int i = 0; i < current.burst; i++) {
                    gantt.add("P" + current.pid);
                    time++;
                }
                current.completion = time;
                current.turnaround = current.completion - current.arrival;
                completed++;
            }
        }

        return gantt;
    }
}
