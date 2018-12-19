package socket;

import lombok.SneakyThrows;
import repositories.IconRepository;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SumSocket extends Thread {

    private IconRepository iconRepository;



    private Socket server;


    @SneakyThrows
    public SumSocket(Socket socket) {
        this.server = socket;
        start();
    }


    @Override
    public void run() {
        byte bug[] = new byte[64 * 64 * 16];
        try {
            int r = this.server.getInputStream().read(bug);
            if (r != -1) {
                String request = new String(bug, 0, r);
                System.out.println(request);
                double sum = getSum(request);

                response(sum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void response(Double sum) {
        String response = "HTTP/1.1 200 Ok\r\n" +
                "Content-Type: text/html\r\n" +
                "Connection: close\r\n\r\n";
        try {
            String notFound = sum.toString();
            this.server.getOutputStream().write(response.getBytes());
            this.server.getOutputStream().write(notFound.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private double getSum(String request) {
        String[] get = request.split("\n");
        System.out.println(get[0]);
        String[] parametr = get[0].split(" ");
        System.out.println(parametr[1]);

        String[] numbers = parametr[1].split("&");
        double sum = 0;
        for(int i =0 ; i< numbers.length; i++){
            String[] num = numbers[i].split("=");
           sum += Double.parseDouble(num[1]);
        }
        System.out.println(sum);
        return (sum);

    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = server.accept();
                new SumSocket(socket);
            }
        } finally {
            server.close();
        }
    }
}

