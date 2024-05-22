package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private int id;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    private int serverWaitingTime;

    public Server(int id) {
        this.id = id;
        tasks = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger();


    }


    public void addTask(Task newTask) {
        serverWaitingTime = serverWaitingTime + this.findServerWaitingTime();
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());

    }

    public void updateTask() {
        Task currentTask = tasks.peek();
        if (currentTask != null) {
            currentTask.decrementServiceTime();
            if (currentTask.getServiceTime() == 0) {
                tasks.remove();
                System.out.println("Task " + currentTask.getID() + " completed on server " + id);
            }

        }
    }

    public void run() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        waitingPeriod.decrementAndGet();
    }


    public int findServerWaitingTime() {
        int waitingTime = 0;
        for (Task client : tasks) {
            waitingTime = waitingTime + client.getServiceTime();
        }

        return waitingTime;
    }

    public int findServerNumberOfClients() {
        return tasks.size();
    }

    public BlockingQueue<Task> getTask() {
        return tasks;
    }

    public int getServerWaitingTime() {
        return serverWaitingTime;
    }

    public int getid() {
        return id;
    }

    public String toString() {
        String s = "";
        s = s + "Queue:" + id + " ";
        if (tasks.isEmpty()) {
            s = s + "Closed\n";
        } else {
            for (Task task : tasks) {
                s = s + task + "  ";
            }
            s = s + "\n";
        }
        return s;
    }
}
