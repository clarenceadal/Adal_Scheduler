import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class CPUSchedulingGUI extends JFrame {

    private JComboBox<String> algorithmBox;
    private JTextField arrivalField, burstField, rrQuantumField;
    private JTextArea outputArea;
    private DefaultListModel<Process> processListModel;
    private JList<Process> processJList;
    private JComboBox<String> modeBox;
    private JTextField randomCountField;
    private JButton addBtn, generateBtn, runBtn, clearBtn;

    private JTextField q0Field, q1Field, q2Field, q3Field; 
    private GanttChartPanel ganttChartPanel = new GanttChartPanel();

    public CPUSchedulingGUI() {
        setTitle("CPU Scheduling Simulator");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(new JLabel("Input Mode:"), gbc);
        modeBox = new JComboBox<>(new String[]{"Manual", "Random"});
        gbc.gridx = 1;
        inputPanel.add(modeBox, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(new JLabel("Arrival Time:"), gbc);
        arrivalField = new JTextField(10);
        gbc.gridx = 1;
        inputPanel.add(arrivalField, gbc);
        addBtn = new JButton("Add Process");
        gbc.gridx = 2;
        inputPanel.add(addBtn, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(new JLabel("Burst Time:"), gbc);
        burstField = new JTextField(10);
        gbc.gridx = 1;
        inputPanel.add(burstField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(new JLabel("# Random Processes:"), gbc);
        randomCountField = new JTextField(10);
        gbc.gridx = 1;
        inputPanel.add(randomCountField, gbc);
        generateBtn = new JButton("Generate Random");
        gbc.gridx = 2;
        inputPanel.add(generateBtn, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(new JLabel("RR Time Quantum:"), gbc);
        rrQuantumField = new JTextField("2", 10);
        gbc.gridx = 1;
        inputPanel.add(rrQuantumField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(new JLabel("Q0 Quantum:"), gbc);
        q0Field = new JTextField("4", 10);
        gbc.gridx = 1;
        inputPanel.add(q0Field, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(new JLabel("Q1 Quantum:"), gbc);
        q1Field = new JTextField("8", 10);
        gbc.gridx = 1;
        inputPanel.add(q1Field, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(new JLabel("Q2 Quantum:"), gbc);
        q2Field = new JTextField("12", 10);
        gbc.gridx = 1;
        inputPanel.add(q2Field, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(new JLabel("Q3 Quantum:"), gbc);
        q3Field = new JTextField("16", 10);
        gbc.gridx = 1;
        inputPanel.add(q3Field, gbc);

        row++;
        clearBtn = new JButton("Clear");
        runBtn = new JButton("Run Scheduler");
        gbc.gridx = 0; gbc.gridy = row;
        inputPanel.add(clearBtn, gbc);
        gbc.gridx = 1;
        inputPanel.add(runBtn, gbc);

        add(inputPanel, BorderLayout.NORTH);

       
        JPanel centerPanel = new JPanel(new BorderLayout());
        processListModel = new DefaultListModel<>();
        processJList = new JList<>(processListModel);
        centerPanel.add(new JScrollPane(processJList), BorderLayout.CENTER);

        ganttChartPanel.setPreferredSize(new Dimension(700, 100));
        ganttChartPanel.setBorder(BorderFactory.createTitledBorder("Gantt Chart"));
        centerPanel.add(ganttChartPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

   
        JPanel bottomPanel = new JPanel(new BorderLayout());
        algorithmBox = new JComboBox<>(new String[]{"FCFS", "SJF", "SRTF", "RR", "MLFQ"});
        bottomPanel.add(algorithmBox, BorderLayout.NORTH);

        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        bottomPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        
        modeBox.addActionListener(e -> toggleMode());
        algorithmBox.addActionListener(e -> toggleMLFQInputs());
        addBtn.addActionListener(e -> addProcess());
        generateBtn.addActionListener(e -> generateRandomProcesses());
        runBtn.addActionListener(e -> runScheduler());
        clearBtn.addActionListener(e -> clearAll());

        toggleMode();
        toggleMLFQInputs();

        setVisible(true);
    }

    private void toggleMode() {
        boolean isManual = modeBox.getSelectedItem().equals("Manual");
        arrivalField.setEnabled(isManual);
        burstField.setEnabled(isManual);
        addBtn.setEnabled(isManual);

        randomCountField.setEnabled(!isManual);
        generateBtn.setEnabled(!isManual);
    }

    private void toggleMLFQInputs() {
        boolean isMLFQ = algorithmBox.getSelectedItem().equals("MLFQ");
        q0Field.setEnabled(isMLFQ);
        q1Field.setEnabled(isMLFQ);
        q2Field.setEnabled(isMLFQ);
        q3Field.setEnabled(isMLFQ);
        rrQuantumField.setEnabled(algorithmBox.getSelectedItem().equals("RR"));
    }

    private void addProcess() {
        try {
            int arrival = Integer.parseInt(arrivalField.getText().trim());
            int burst = Integer.parseInt(burstField.getText().trim());
            int pid = processListModel.getSize() + 1;
            processListModel.addElement(new Process(pid, arrival, burst));
            arrivalField.setText("");
            burstField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers.");
        }
    }

    private void generateRandomProcesses() {
        try {
            int count = Integer.parseInt(randomCountField.getText().trim());
            Random rand = new Random();
            for (int i = 0; i < count; i++) {
                int pid = processListModel.getSize() + 1;
                int arrival = rand.nextInt(10);
                int burst = rand.nextInt(9) + 1;
                processListModel.addElement(new Process(pid, arrival, burst));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number.");
        }
    }

    private void runScheduler() {
        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < processListModel.getSize(); i++) {
            Process p = processListModel.getElementAt(i);
            processes.add(new Process(p.pid, p.arrival, p.burst));
        }

        Scheduler scheduler;
        switch ((String) algorithmBox.getSelectedItem()) {
            case "FCFS": scheduler = new FCFS(); break;
            case "SJF": scheduler = new SJF(); break;
            case "SRTF": scheduler = new SRTF(); break;
            case "RR":
                int rrQuantum = Integer.parseInt(rrQuantumField.getText().trim());
                scheduler = new RR(rrQuantum); break;
            case "MLFQ":
                int[] tq = {
                    Integer.parseInt(q0Field.getText()),
                    Integer.parseInt(q1Field.getText()),
                    Integer.parseInt(q2Field.getText()),
                    Integer.parseInt(q3Field.getText())
                };
                scheduler = new MLFQ(tq); break;
            default: return;
        }

        List<String> ganttChart = scheduler.run(processes);
        ganttChartPanel.setChart(ganttChart);

        StringBuilder result = new StringBuilder("Process Details:\n\n");
        int totalTurnaround = 0, totalResponse = 0;
        for (Process p : processes) {
            result.append(String.format("P%-4d Arrival: %-3d Burst: %-3d Completion: %-3d Turnaround: %-3d Response: %-3d\n",
                    p.pid, p.arrival, p.burst, p.completion, p.turnaround, p.response));
            totalTurnaround += p.turnaround;
            totalResponse += p.response;
        }

        int n = processes.size();
        result.append(String.format("\nAverage Turnaround Time: %.2f\n", totalTurnaround * 1.0 / n));
        result.append(String.format("Average Response Time: %.2f\n", totalResponse * 1.0 / n));

        if (scheduler instanceof MLFQ) {
            result.append("\n[MLFQ Queue Legend] Format: Pid(Qn), where Qn is queue level\n");
        }

        outputArea.setText(result.toString());
    }

    private void clearAll() {
        processListModel.clear();
        ganttChartPanel.setChart(new ArrayList<>());
        outputArea.setText("");
        arrivalField.setText("");
        burstField.setText("");
        randomCountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CPUSchedulingGUI::new);
    }
}
