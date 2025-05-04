import java.util.*;

public class Ring {
    int maxProcesses, coordinator;
    boolean[] processes;

    public Ring(int n) {
        maxProcesses = coordinator = n;
        processes = new boolean[n];
        for (int i = 0; i < n; i++) {
            processes[i] = true;
            System.out.println("P" + (i + 1) + " created.");
        }
        System.out.println("P" + coordinator + " is the coordinator.");
    }

    void displayProcesses() {
        for (int i = 0; i < maxProcesses; i++)
            System.out.println("P" + (i + 1) + " is " + (processes[i] ? "up" : "down") + ".");
        System.out.println("Coordinator is P" + coordinator);
    }

    void upProcess(int id) {
        if (processes[id - 1]) System.out.println("P" + id + " already up.");
        else {
            processes[id - 1] = true;
            System.out.println("P" + id + " is up.");
        }
    }

    void downProcess(int id) {
        if (!processes[id - 1]) System.out.println("P" + id + " already down.");
        else {
            processes[id - 1] = false;
            System.out.println("P" + id + " is down.");
        }
    }

    void initElection(int id) {
        if (!processes[id - 1]) {
            System.out.println("P" + id + " is down. Cannot start election.");
            return;
        }
        List<Integer> alive = new ArrayList<>();
        int temp = id % maxProcesses;
        alive.add(id);

        while (temp != id - 1) {
            if (processes[temp])
                alive.add(temp + 1);
            temp = (temp + 1) % maxProcesses;
        }

        coordinator = Collections.max(alive);
        System.out.println("Election complete. New coordinator is P" + coordinator);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ring ring = null;
        while (true) {
            System.out.println("\nRing Algorithm\n1. Create\n2. Display\n3. Up\n4. Down\n5. Election\n6. Exit\nChoice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter total processes: ");
                    ring = new Ring(sc.nextInt());
                    break;
                case 2:
                    if (ring != null) ring.displayProcesses();
                    else System.out.println("No ring created yet.");
                    break;
                case 3:
                    System.out.print("Process to up: ");
                    ring.upProcess(sc.nextInt());
                    break;
                case 4:
                    System.out.print("Process to down: ");
                    ring.downProcess(sc.nextInt());
                    break;
                case 5:
                    System.out.print("Initiator process: ");
                    ring.initElection(sc.nextInt());
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
            sc.close();
        }
        
    }
}
