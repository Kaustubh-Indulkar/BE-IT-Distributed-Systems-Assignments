import ReverseModule.ReversePOA;

public class ReverseImpl extends ReversePOA {
    public ReverseImpl() {
        System.out.println("Reverse Object Created");
    }

    public String reverse_string(String name) {
        return "Server Send " + new StringBuilder(name).reverse();
    }
}
