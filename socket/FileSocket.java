package socket;

import lombok.SneakyThrows;
import models.Icon;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.IconRepository;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileSocket extends Thread {

    private IconRepository iconRepository;

    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "dREAM1cACAO";
    private static final String URL = "jdbc:postgresql://localhost:5432/bank_service";


    private Socket server;


    @SneakyThrows
    public FileSocket(Socket socket) {
        this.server = socket;

        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();

        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        iconRepository = new IconRepository(dataSource);
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
                String img = getPath(request);
                if (img != null) {
                    Optional<Icon> optionalIcon = iconRepository.findOneByName(img);
                    if (optionalIcon.isPresent()) {
                        Icon icon = optionalIcon.get();
                        response(new File(icon.getPath()));
                    }
                }
                response(new File("empty"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SneakyThrows
    private void response(File file) {
        File img = new File("/Users/danis_zam/IdeaProjects/bankservice/src/main/webapp" + file.getPath());
        System.out.println(img.getName());
       // System.out.println(Files.probeContentType(Paths.get(img.getName())));
        if (img.exists()) {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: "+Files.probeContentType(Paths.get(img.getName()))+ "\r\n" +
                    "Content-length: " + img.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            byte[] arr;
            try {
                arr = Files.readAllBytes(Paths.get(img.toURI()));
                this.server.getOutputStream().write(response.getBytes());
                this.server.getOutputStream().write(arr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String response = "HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Connection: close\r\n\r\n";
            try {
                String notFound = "404 Sorry, file not found";
                this.server.getOutputStream().write(response.getBytes());
                this.server.getOutputStream().write(notFound.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getPath(String request) {
        String[] get = request.split("\n");
        String[] parametr = get[0].split(" ");
        Pattern pattern = Pattern.compile("[a-z,0-9]+");
        Matcher matcher = pattern.matcher(parametr[1]);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;

    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = server.accept();
                new FileSocket(socket);
            }
        } finally {
            server.close();
        }
    }
}
