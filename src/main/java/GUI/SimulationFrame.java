package GUI;

import BusinessLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame {
    private JTextField textFieldNrClients = new JTextField(20);
    private JTextField textFieldNrQueues = new JTextField(20);
    private JTextField textFieldSimInt = new JTextField(20);
    private JTextField textFieldMinArrivalTime = new JTextField(20);
    private JTextField textFieldMaxArrivalTime = new JTextField(20);
    private JTextField textFieldMinServiceTime = new JTextField(20);
    private JTextField textFieldMaxServiceTime = new JTextField(20);

    private int isStartButtonPressed;
    private JButton startBtn = new JButton("Start Simulation");

    private JRadioButton shortestQueue = new JRadioButton("Shortest Queue Strategy");
    private JRadioButton shortestTime = new JRadioButton("Shortest Time Strategy");

    private JPanel output = new JPanel();

    private JTextArea outputArea = new JTextArea(120, 40);
    private int simulationTime;
    private int numberOfClients;
    private int numberOfServers;

    private int minServiceTime;
    private int maxServiceTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int isShortestTime;
    private int isShortestQueue;

    private JFrame frame;

    public SimulationFrame() {
        JFrame frame = new JFrame("QUEUES MANAGEMENT APPLICATION");
        frame.setSize(450, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        JPanel NumberOfClientsPanel = new JPanel();
        NumberOfClientsPanel.setLayout(new FlowLayout());
        JLabel NumberOfClientsLabel = new JLabel("Number of Clients:");
        NumberOfClientsPanel.add(NumberOfClientsLabel);
        NumberOfClientsPanel.add(textFieldNrClients);

        JPanel NrOfQueuesPanel = new JPanel();
        NrOfQueuesPanel.setLayout(new FlowLayout());
        JLabel NrOfQueuesLabel = new JLabel("Number of Queues:");
        NrOfQueuesPanel.add(NrOfQueuesLabel);
        NrOfQueuesPanel.add(textFieldNrQueues);

        JPanel SimIntervalPanel = new JPanel();
        SimIntervalPanel.setLayout(new FlowLayout());
        JLabel SimIntLabel = new JLabel("Simulation Interval:");
        SimIntervalPanel.add(SimIntLabel);
        SimIntervalPanel.add(textFieldSimInt);

        JPanel MinArrTimePanel = new JPanel();
        MinArrTimePanel.setLayout(new FlowLayout());
        JLabel MinArrTimeLabel = new JLabel("Minimum arrival time:");
        MinArrTimePanel.add(MinArrTimeLabel);
        MinArrTimePanel.add(textFieldMinArrivalTime);

        JPanel MaxArrTimePanel = new JPanel();
        MaxArrTimePanel.setLayout(new FlowLayout());
        JLabel MaxArrTimeLabel = new JLabel("Maximum arrival time:");
        MaxArrTimePanel.add(MaxArrTimeLabel);
        MaxArrTimePanel.add(textFieldMaxArrivalTime);

        JPanel MinServiceTimePanel = new JPanel();
        MinServiceTimePanel.setLayout(new FlowLayout());
        JLabel MinServiceTimeLabel = new JLabel("Minimum service time:");
        MinServiceTimePanel.add(MinServiceTimeLabel);
        MinServiceTimePanel.add(textFieldMinServiceTime);


        JPanel MaxServiceTimePanel = new JPanel();
        MaxServiceTimePanel.setLayout(new FlowLayout());
        JLabel MaxServiceTimeLabel = new JLabel("Maximum service time:");
        MaxServiceTimePanel.add(MaxServiceTimeLabel);
        MaxServiceTimePanel.add(textFieldMaxServiceTime);

        JPanel strategies = new JPanel();
        strategies.setLayout(new FlowLayout());
        strategies.add(shortestQueue);
        strategies.add(shortestTime);
        ButtonGroup strategiesGroup = new ButtonGroup();
        strategiesGroup.add(shortestQueue);
        strategiesGroup.add(shortestTime);


        GridLayout gridLayout = new GridLayout(10, 1);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        frame.setLayout(gridLayout);
        frame.add(NumberOfClientsPanel);
        frame.add(NrOfQueuesPanel);
        frame.add(SimIntervalPanel);
        frame.add(MinArrTimePanel);
        frame.add(MaxArrTimePanel);
        frame.add(MinServiceTimePanel);
        frame.add(MaxServiceTimePanel);
        frame.add(strategies);
        frame.add(startBtn);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    simulationTime = getSimulationTime();
                    numberOfClients = getNumberOfClients();
                    numberOfServers = getNumberOfServers();
                    minServiceTime = getMinServiceTime();
                    maxServiceTime = getMaxServiceTime();
                    minArrivalTime = getMinArrivalTime();
                    maxArrivalTime = getMaxArrivalTime();
                    isShortestTime = isShortestTimeSelected();
                    isShortestQueue = isShortestQueueSelected();

                    verifyRadioButtons(isShortestTime, isShortestQueue);
                    verifyMinMax(minArrivalTime, maxArrivalTime);
                    verifyMinMax(minServiceTime, maxServiceTime);
                    RunningSimulation simulation = new RunningSimulation();
                    outputArea = simulation.getOutputArea();
                    SimulationManager sim = new SimulationManager(simulationTime, maxServiceTime, minServiceTime, numberOfServers, numberOfClients, isShortestQueue, isShortestTime, outputArea, minArrivalTime, maxArrivalTime);
                    System.out.println(simulationTime + " " + maxServiceTime + " " + minServiceTime + " " + numberOfServers + " " + numberOfClients + " " + isShortestQueue + " " + isShortestTime);
                    Thread t = new Thread(sim);
                    t.start();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "WARNING.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }


    public int getSimulationTime() {
        String textSim = textFieldSimInt.getText();
        return Integer.parseInt(textSim);
    }

    public int getNumberOfClients() {
        String textNoOfClients = textFieldNrClients.getText();
        return Integer.parseInt(textNoOfClients);
    }

    public int getNumberOfServers() {
        String textNoOfServers = textFieldNrQueues.getText();
        return Integer.parseInt(textNoOfServers);
    }

    public int getMinServiceTime() {
        String textMinService = textFieldMinServiceTime.getText();
        return Integer.parseInt(textMinService);
    }

    public int getMaxServiceTime() {
        String textMaxService = textFieldMaxServiceTime.getText();
        return Integer.parseInt(textMaxService);
    }

    public int getMinArrivalTime() {
        String textMinArrival = textFieldMinArrivalTime.getText();
        return Integer.parseInt(textMinArrival);
    }

    public int getMaxArrivalTime() {
        String textMaxArrival = textFieldMaxArrivalTime.getText();
        return Integer.parseInt(textMaxArrival);
    }

    public int isShortestQueueSelected() {
        if (shortestQueue.isSelected()) {
            return 1;
        } else
            return 0;
    }

    public int isShortestTimeSelected() {
        if (shortestTime.isSelected()) {
            return 1;
        } else
            return 0;
    }


    public void verifyRadioButtons(int shortStrat, int timeStrat) throws Exception {
        if (shortStrat == 0 && timeStrat == 0) {
            throw new Exception("No sorting strategy chosen");
        }

    }

    public void verifyMinMax(int min, int max) throws Exception {
        if (min > max)
            throw new Exception("Wrong Logic");
    }
}
