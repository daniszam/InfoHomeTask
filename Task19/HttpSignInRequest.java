package ru.itis.kpfu.darZam.BattleRoyal.Task19;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;


import java.io.IOException;
import java.net.URISyntaxException;


public class HttpSignInRequest {

    private static final String HOST ="http://localhost:8080/signIn";

    public static int sendRequest(String email, String password) throws URISyntaxException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(HOST);
        uriBuilder
                .setParameter("email", email)
                .setParameter("password", password);
        HttpPost post = new HttpPost(uriBuilder.build());
        HttpResponse response = httpClient.execute(post);
        return response.getStatusLine().getStatusCode();
    }
}
