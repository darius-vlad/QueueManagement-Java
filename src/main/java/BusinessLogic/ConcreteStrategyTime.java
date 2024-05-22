package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task t) {

        int minServerTime = Integer.MAX_VALUE - 1;
        int time;

        for (Server server : servers) {
            if (server == null)
                time = 0;
            else
                time = server.findServerWaitingTime();
            if (time < minServerTime) {
                minServerTime = time;

            }
        }

        for (Server server : servers) {
            if (server.findServerWaitingTime() == minServerTime) {
                server.addTask(t);
                break;
            }
        }
    }
}
