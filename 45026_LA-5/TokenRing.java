import java.util.*;

public class TokenRing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of Nodes: ");
        int n = sc.nextInt();

        // Printing the ring structure
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
        }
        System.out.println("0");  // Completing the ring

        int ch = 0;  // Choice for repeating transmission

        do {
            System.out.print("Enter Sender: ");
            int sender = sc.nextInt();

            System.out.print("Enter Receiver: ");
            int receiver = sc.nextInt();

            System.out.print("Enter Data to send: ");
            int data = sc.nextInt();
            
            System.out.print("Token Passing: ");

            int token = 0;
            for (int i = token; i < sender; i++) {
                System.out.print(i + " => ");
            }
            System.out.println(sender);

            System.out.println("Sender " + sender + " Sending Data: " + data);

            // Forwarding data through nodes
            for (int i = sender; i != receiver; i = (i + 1) % n) {
                System.out.println("Data " + data + " Forwarded by " + i);
            }

            System.out.println("Receiver " + receiver + " Received the Data: " + data + "\n");

            // Update token position
            token = sender;

            System.out.print("Do you want to send data again? Enter 1 for Yes and 0 for No: ");
            ch = sc.nextInt();

        } while (ch == 1);

        sc.close();  // Closing the Scanner to prevent resource leaks
    }
}
