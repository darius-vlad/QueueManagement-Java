package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SimulationManager implements Runnable {

    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int numberOfServers;
    private int numberOfClients;

    private int minArrivalTime;
    private int maxArrivalTime;
    private SelectionPolicy selectionPolicy;

    private Scheduler scheduler;
    private JTextArea output;
    private List<Task> generatedTasks;

    private double totalServiceTime;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int numberOfServers, int numberOfClients, int queueStrat, int timeStrat, JTextArea output, int minArrivalTime, int maxArrivalTime) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        generatedTasks = new ArrayList<Task>();
        if (queueStrat == 1) {
            selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;

        }

        if (timeStrat == 1) {
            selectionPolicy = SelectionPolicy.SHORTEST_TIME;
        }

        scheduler.changeStrategy(selectionPolicy);


        this.output = output;

        generateNRandomTasks(numberOfClients);


    }


    public void generateNRandomTasks(int N) {
        int taskID;
        int taskArrivalTime;
        int taskServiceTime;
        int i;
        for (i = 0; i < N; i++) {
            taskID = i + 1;
            taskArrivalTime = (int) Math.floor(Math.random() * (maxArrivalTime - minArrivalTime + 1) + minArrivalTime);
            taskServiceTime = (int) Math.floor(Math.random() * (maxProcessingTime - minProcessingTime + 1) + minProcessingTime);
            Task task = new Task(taskID, taskArrivalTime, taskServiceTime);
            generatedTasks.add(task);
            totalServiceTime = totalServiceTime + taskServiceTime;
        }
        Collections.sort(generatedTasks, new SortyByArrivalTime());
    }


    public void run() {
        int currentTime = 0;
        createFile();
        int peakHour = 0;
        int maxSize = 0;
        String output = "";
        File logFile = createFile();
        System.out.println("Current Time: " + currentTime);
        while (currentTime <= timeLimit) {
            output = "Current Time: " + currentTime + "\n";
            Iterator<Task> iterator = generatedTasks.iterator();
            try {
                updateServers();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getArrivalTime() == currentTime) {

                    scheduler.dispatchTask(task);
                    iterator.remove();
                }
            }

            if (scheduler.findServersTotalClients() > maxSize) {
                maxSize = scheduler.findServersTotalClients();
                peakHour = currentTime;
            }
            output = output + printStatus();
            this.output.append(output);
            appendToFile(output);
            output = "";
            currentTime++;

        }

        writeStats(peakHour);

    }

    public void writeStats(int peakHour) {
        String output;
        output = "Average service time: " + totalServiceTime / numberOfClients + "\n";
        this.output.append(output);
        appendToFile(output);

        output = "Peak Hour was: " + peakHour + "\n";
        this.output.append(output);
        appendToFile(output);

        output = "Waiting time: " + (double) scheduler.findServersTotalTime() / numberOfClients + "\n";
        this.output.append(output);
        appendToFile(output);

    }

    public String printStatus() {
        String output = "";
        output = output + "Waiting clients: ";

        for (Task task : generatedTasks)
            output = output + task + " ";

        output = output + "\n";
        for (Server server : scheduler.getServers()) {

            output = output + server;
        }
        output = output + "\n";
        return output;
    }

    public void updateServers() throws InterruptedException {
        for (Server server : scheduler.getServers()) {
            server.updateTask();
        }
        Thread.sleep(1000);
    }

    public static void main(String[] args) {
        SimulationFrame frame = new SimulationFrame();
    }

    public File createFile() {
        File logFile = null;
        try {
            logFile = new File("log.txt");
            if (logFile.createNewFile()) {
                System.out.println("File created: " + logFile.getName());
            } else {
                System.out.println("File already exists. Clearing its content.");
                // Clearing file content
                FileWriter fw = new FileWriter(logFile);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("");
                bw.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return logFile;
    }

    public void appendToFile(String message) {
        try {
            FileWriter myWriter = new FileWriter("log.txt", true);
            myWriter.write(message);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}



