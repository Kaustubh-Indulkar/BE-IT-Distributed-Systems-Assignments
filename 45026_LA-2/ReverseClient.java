import ReverseModule.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ReverseClient {
    public static void main(String[] args) {
        try {
            // Initialize ORB
            ORB orb = ORB.init(args, null);

            // Get reference to NameService
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Lookup for Reverse object
            Reverse reverse = ReverseHelper.narrow(ncRef.resolve_str("Reverse"));

            // Read input from user
            System.out.print("Enter a string: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();

            // Call remote method
            String result = reverse.reverse_string(input);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
