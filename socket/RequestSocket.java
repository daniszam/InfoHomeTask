package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class RequestSocket {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket(InetAddress.getByName("yandex.ru"), 443);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.println("GET / HTTP/1.1");
        pw.println("Host: yandex.ru");
        pw.println("");
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String t;
        while((t = br.readLine()) != null) {
            System.out.println(t);
        }
        s.close();
        br.close();
    }
}
