package socket;

public class Client1 {

    private static String ipAddr = "localhost";
    private static int port = 8080;

    public static void main(String[] args) {
        new ClientSomthing(ipAddr, port);
    }
}
