package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers = new ArrayList<Server>();
    private int MaxNoServers;
    private int MaxTasksPerServer;

    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        MaxNoServers = maxNoServers;
        MaxTasksPerServer = maxTasksPerServer;
        int i = 0;
        while (i < maxNoServers) {
            Server s = new Server(i);
            servers.add(s);
            Thread serverThread = new Thread(s);
            serverThread.start();
            i++;

        }
    }

    public void changeStrategy(SelectionPolicy policy) {

        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }

        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t) {
        strategy.addTask(servers, t);

    }

    public int findServersTotalTime() {

        int totalTime = 0;
        for (Server server : servers) {
            totalTime = totalTime + server.getServerWaitingTime();
        }
        return totalTime;
    }

    public int findServersTotalClients() {
        int totalSize = 0;
        for (Server server : servers) {
            totalSize = totalSize + server.findServerNumberOfClients();
        }

        return totalSize;
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
}
