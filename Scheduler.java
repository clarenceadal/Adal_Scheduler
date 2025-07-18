import java.util.List;

public interface Scheduler {
    List<String> run(List<Process> processes);
}
