import java.util.*;

public class RR implements Scheduler {
    private int timeQuantum = 2;

    public RR() {}
    public RR(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    @Override
    public List<String> run(List<Process> processes) {
        List<String> gantt = new ArrayList<>();
        Queue<Process> queue = new LinkedList<>();
        processes.sort(Comparator.comparingInt(p -> p.arrival));
        int time = 0, index = 0, completed = 0;
        int n = processes.size();
        boolean[] visited = new boolean[n];

        while (completed < n) {
            while (index < n && processes.get(index).arrival <= time) {
                queue.add(processes.get(index));
                visited[index] = true;
                index++;
            }

            if (queue.isEmpty()) {
                gantt.add("Idle");
                time++;
                continue;
            }

            Process current = queue.poll();

            if (current.remaining == current.burst) {
                current.response = time - current.arrival;
            }

            int execTime = Math.min(timeQuantum, current.remaining);
            for (int i = 0; i < execTime; i++) {
                gantt.add("P" + current.pid);
                time++;
                
               
                while (index < n && processes.get(index).arrival <= time) {
                    queue.add(processes.get(index));
                    visited[index] = true;
                    index++;
                }
            }

            current.remaining -= execTime;

            if (current.remaining > 0) {
                queue.add(current);
            } else {
                current.completion = time;
                current.turnaround = current.completion - current.arrival;
                completed++;
            }
        }

        return gantt;
    }
}
