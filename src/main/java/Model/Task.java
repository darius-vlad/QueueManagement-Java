package Model;

public class Task {

    private int ID;
    private int arrivalTime;
    private int serviceTime;


    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getID() {
        return ID;
    }

    public String toString() {
        return "(" + ID + "," + arrivalTime + "," + serviceTime + ")";
    }

    public void decrementServiceTime() {
        serviceTime--;
    }
}
