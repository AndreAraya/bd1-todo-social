package tec.bd.todo.auth;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.todo.auth.service.SessionService;
import tec.bd.todo.auth.service.SessionServiceImpl;

import java.util.*;

import static spark.Spark.*;


public class AuthApi {

    private final static Logger LOG = LoggerFactory.getLogger(AuthApi.class);

    public static void main(String[] args ) {


        var webAppContext = WebApplicationContext.init();
        SessionService sessionService = webAppContext.getSessionService();
        Gson gson = webAppContext.getGson();


        port(8080);
        options("/", (request, response) -> {
            response.header("Content-Type", "application/json");
            return Map.of(
                    "message", "AUTH API");
        }, gson::toJson);

        get("/clients", (request, response) -> {
            return sessionService.getAllClients();
        }, gson::toJson);

        post("/clients", (request, response) -> {
            var credentials = gson.fromJson(request.body(), ClientCredentials.class);
            sessionService.addNewClient(credentials);
            return credentials;
        }, gson::toJson);

        delete("/clients/:client-id", (request, response) -> {
            var clientId = request.params("client-id");
            sessionService.deleteClient(clientId);
            response.status(200);
            return Map.of("Deleted", "OK");
        }, gson::toJson);

        get("/sessions", (request, response) -> {
           return sessionService.getAllSessions();
        }, gson::toJson);

        post("/sessions", (request, response) -> {
            var credentials = gson.fromJson(request.body(), ClientCredentials.class);
            try {
                var session = sessionService.newSession(credentials);
                response.status(200);
                return session;
            } catch (SessionServiceImpl.CredentialsException e) {
                response.status(400);
                return Map.of("Message", "Bad Credentials");
            }
        }, gson::toJson);

        get("/sessions/validate", (request, response) -> {
            var sessionParam = request.queryParams("session");
            if(null == sessionParam) {
                response.status(400);
                return Map.of("Message", "Bad Request");
            }
            return sessionService.validateSession(sessionParam);
        }, gson::toJson);
    }
}
