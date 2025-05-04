import java.util.*;

public class Bully {
    int coordinator, n;
    boolean[] processes;

    public Bully(int n) {
        this.n = n;
        this.coordinator = n;
        processes = new boolean[n];
        for (int i = 0; i < n; i++) {
            processes[i] = true;
            System.out.println("P" + (i + 1) + " created");
        }
        System.out.println("Coordinator is P" + coordinator);
    }

    void display() {
        for (int i = 0; i < n; i++)
            System.out.println("P" + (i + 1) + " is " + (processes[i] ? "up" : "down"));
        System.out.println("Coordinator is P" + coordinator);
    }

    void up(int id) {
        if (processes[id - 1]) System.out.println("P" + id + " already up");
        else {
            processes[id - 1] = true;
            System.out.println("P" + id + " is now up");
        }
    }

    void down(int id) {
        if (!processes[id - 1]) System.out.println("P" + id + " already down");
        else {
            processes[id - 1] = false;
            System.out.println("P" + id + " is down");
        }
    }

    void elect(int id) {
        if (!processes[id - 1]) {
            System.out.println("P" + id + " is down. Can't start election.");
            return;
        }

        System.out.println("Election initiated by P" + id);
        int newCoordinator = id;

        for (int i = id; i < n; i++) {
            if (processes[i]) {
                System.out.println("P" + (id) + " sends message to P" + (i + 1));
                newCoordinator = i + 1;
            }
        }

        coordinator = newCoordinator;
        System.out.println("New Coordinator is P" + coordinator);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bully bully = null;
        
        while (true) {
            System.out.println("\nBully Algorithm\n1.Create 2.Display 3.Up 4.Down 5.Elect 6.Exit\nChoice: ");
            int choice = sc.nextInt(); 
        
            switch (choice) {
                case 1:
                    System.out.print("Enter number of processes: ");
                    bully = new Bully(sc.nextInt());
                    break;
                case 2:
                    if (bully != null) bully.display();
                    else System.out.println("Create processes first.");
                    break;
                case 3:
                    System.out.print("Enter process to up: ");
                    bully.up(sc.nextInt());
                    break;
                case 4:
                    System.out.print("Enter process to down: ");
                    bully.down(sc.nextInt());
                    break;
                case 5:
                    System.out.print("Enter process to start election: ");
                    bully.elect(sc.nextInt());
                    break;
                case 6:
                    sc.close(); 
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    } 
}        