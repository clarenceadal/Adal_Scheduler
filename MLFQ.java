import java.util.*;

public class MLFQ implements Scheduler {
    private final int[] timeQuantums;
    private final int maxQueues = 4;

    public MLFQ(int[] timeQuantums) {
        if (timeQuantums.length != maxQueues) throw new IllegalArgumentException("Must provide 4 quantums");
        this.timeQuantums = timeQuantums;
    }

    @Override
    public List<String> run(List<Process> processes) {
        List<String> gantt = new ArrayList<>();
        processes.sort(Comparator.comparingInt(p -> p.arrival));

        int time = 0, completed = 0, n = processes.size();
        List<Queue<Process>> queues = new ArrayList<>();
        for (int i = 0; i < maxQueues; i++) queues.add(new LinkedList<>());

        int index = 0;

        while (completed < n) {
            while (index < n && processes.get(index).arrival <= time) {
                processes.get(index).queueLevel = 0;
                queues.get(0).add(processes.get(index));
                index++;
            }

            boolean executed = false;

            for (int q = 0; q < maxQueues; q++) {
                Queue<Process> queue = queues.get(q);
                if (!queue.isEmpty()) {
                    Process current = queue.poll();

                    if (current.remaining == current.burst)
                        current.response = time - current.arrival;

                    int tq = timeQuantums[q];
                    int run = Math.min(current.remaining, tq);
                    for (int i = 0; i < run; i++) {
                        gantt.add("P" + current.pid + "(Q" + q + ")");
                        time++;

                        while (index < n && processes.get(index).arrival <= time) {
                            processes.get(index).queueLevel = 0;
                            queues.get(0).add(processes.get(index));
                            index++;
                        }
                    }

                    current.remaining -= run;

                    if (current.remaining == 0) {
                        current.completion = time;
                        current.turnaround = current.completion - current.arrival;
                        completed++;
                    } else {
                        int nextQueue = Math.min(q + 1, maxQueues - 1);
                        current.queueLevel = nextQueue;
                        queues.get(nextQueue).add(current);
                    }

                    executed = true;
                    break;
                }
            }

            if (!executed) {
                gantt.add("Idle");
                time++;
            }
        }

        return gantt;
    }
}
