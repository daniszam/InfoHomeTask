package socket;

public class Client {

    private static String ipAddr = "localhost";
    private static int port = 8080;

    public static void main(String[] args) {
        new ClientSomthing(ipAddr, port);
    }
}
