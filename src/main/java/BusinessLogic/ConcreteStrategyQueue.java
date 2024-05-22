package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;


public class ConcreteStrategyQueue implements Strategy {

    public void addTask(List<Server> servers, Task t) {
        int numberOfClients;
        int minNumberOfClients = Integer.MAX_VALUE - 1;
        for (Server server : servers) {
            if (server == null)
                numberOfClients = 0;
            else
                numberOfClients = server.findServerNumberOfClients();
            if (numberOfClients < minNumberOfClients) {
                minNumberOfClients = numberOfClients;
            }
        }

        for (Server server : servers) {
            if (server.findServerNumberOfClients() == minNumberOfClients) {
                System.out.println("Clientul " + t.getID() + " a intrat in coada " + server.getid());
                server.addTask(t);
                break;

            }
        }
    }
}
