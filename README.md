# CPU Scheduler

## Overview

This project is a simple simulation of how five well-known CPU scheduling algorithms work. It features a graphical user interface (GUI) to help visualize how processes are scheduled using different algorithms and provides a Gantt chart to represent scheduling order.

##How to Run the Simulation

1. **Run the GUI application.**
2. **Choose between:**
   - **Manual Input** – You enter each process manually.
   - **Random Generation** – The system creates random processes for you.
3. **For Manual Input:**
   - Enter `Arrival Time` and `Burst Time` for a process.
   - Click the **Add Process** button to save the process.
   - Repeat for as many processes as needed.
4. **Choose the Scheduler** from the dropdown under the Gantt chart:
   - FCFS (First Come, First Serve)
   - SJF (Shortest Job First)
   - RR (Round Robin)
   - SRTF (Shortest Remaining Time First)
   - MLFQ (Multilevel Feedback Queue)
5. **For Round Robin**, you will also need to input the **Time Quantum** manually.
6. Click **Run Scheduler** to begin the simulation and view the results.

## Scheduling Algorithms Implemented

### FCFS (First Come, First Serve)
Processes are executed in the order they arrive. Non-preemptive.

### SJF (Shortest Job First)
Executes the process with the shortest burst time first. Non-preemptive.

### RR (Round Robin)
Each process gets an equal slice of CPU time. Preemptive and fair. Requires a time quantum.

### SRTF (Shortest Remaining Time First)
Preemptive version of SJF. At each time unit, the process with the shortest remaining burst is scheduled.

### MLFQ (Multilevel Feedback Queue)
A complex scheduler that uses multiple queues with varying priorities and time quanta. Dynamically adjusts process priority based on behavior.

## SCREENSHOTS (Not In Order)
![image alt](https://github.com/clarenceadal/Adal_Scheduler/blob/main/Screenshot%202025-07-19%20132645.png)
![image alt](https://github.com/clarenceadal/Adal_Scheduler/blob/main/Screenshot%202025-07-19%20132733.png)
![image alt](https://github.com/clarenceadal/Adal_Scheduler/blob/main/Screenshot%202025-07-19%20132839.png)
![image alt](https://github.com/clarenceadal/Adal_Scheduler/blob/main/Screenshot%202025-07-19%20133018.png)
![image alt](https://github.com/clarenceadal/Adal_Scheduler/blob/main/Screenshot%202025-07-19%20133048.png)
![image alt](https://github.com/clarenceadal/Adal_Scheduler/blob/main/Screenshot%202025-07-19%20133149.png)
## Sample Input & Expected Output

### Sample Input:
-P1: Arrival Time = 0, Burst Time = 1
-P2: Arrival Time = 0, Burst Time = 2
-P3: Arrival Time = 0, Burst Time = 3
-Scheduler Chosen: SJF
### Expected Output:
P1: Completion = 1, Turnaround = 1, Response = 0
P2: Completion = 3, Turnaround = 3, Response = 1
P3: Completion = 6, Turnaround = 6, Response = 3

Average Turnaround Time: 3.33
Average Response Time: 1.33

### Known Bugs or Incomplete Features
No known bugs as of the moment. but it lacks the step by step execution mode, export results to a file, and live charts.
