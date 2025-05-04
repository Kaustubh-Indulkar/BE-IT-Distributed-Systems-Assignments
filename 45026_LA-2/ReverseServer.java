import ReverseModule.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

public class ReverseServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            ReverseImpl reverseImpl = new ReverseImpl();
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(reverseImpl);
            Reverse h_ref = ReverseHelper.narrow(ref);

            NamingContextExt ncRef = NamingContextExtHelper.narrow(
                orb.resolve_initial_references("NameService")
            );

            NameComponent[] path = ncRef.to_name("Reverse");
            ncRef.rebind(path, h_ref);

            System.out.println("Reverse Server is running...");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
