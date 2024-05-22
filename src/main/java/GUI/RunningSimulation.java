package GUI;

import javax.swing.*;

public class RunningSimulation {

    private JPanel output = new JPanel();

    private JTextArea outputArea = new JTextArea(30, 80);

    public RunningSimulation() {
        JFrame frame = new JFrame("RUNNING SIMULATION");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JScrollPane scrollOutput = new JScrollPane(outputArea);
        outputArea.setEditable(false);
        output.add(scrollOutput);

        frame.add(output);


    }

    public JPanel getOutput() {
        return output;
    }

    public JTextArea getOutputArea() {
        return outputArea;
    }
}
