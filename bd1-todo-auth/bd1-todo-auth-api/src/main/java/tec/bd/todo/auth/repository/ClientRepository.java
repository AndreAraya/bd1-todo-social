package tec.bd.todo.auth.repository;

import tec.bd.todo.auth.ClientCredentials;

import java.util.List;

public interface ClientRepository {

    List<ClientCredentials> findAll();

    ClientCredentials findByClientId(String clientId);

    ClientCredentials save(ClientCredentials clientCredentials);

    void remove(String clientId);
}
