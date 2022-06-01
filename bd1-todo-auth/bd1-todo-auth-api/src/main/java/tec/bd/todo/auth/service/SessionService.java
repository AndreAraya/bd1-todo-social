package tec.bd.todo.auth.service;

import ch.qos.logback.core.net.server.Client;
import tec.bd.todo.auth.ClientCredentials;
import tec.bd.todo.auth.Session;
import java.util.List;

public interface SessionService {

    void addNewClient(ClientCredentials credentials);

    List<ClientCredentials> getAllClients();

    void deleteClient(String clientId);

    List<Session> getAllSessions();

    Session newSession(ClientCredentials credentials);

    Session updateSession(ClientCredentials credentials);

    Session validateSession(String session);

}
