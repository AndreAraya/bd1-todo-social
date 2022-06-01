package tec.bd.todo.auth.service;

import tec.bd.todo.auth.ClientCredentials;
import tec.bd.todo.auth.Session;
import tec.bd.todo.auth.SessionStatus;
import tec.bd.todo.auth.repository.ClientRepository;
import tec.bd.todo.auth.repository.SessionRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SessionServiceImpl implements SessionService {

    private ClientRepository clientRepository;
    private SessionRepository sessionRepository;

    public SessionServiceImpl(ClientRepository clientRepository, SessionRepository sessionRepository) {
        this.clientRepository = clientRepository;
        this.sessionRepository = sessionRepository;
    }


    @Override
    public void addNewClient(ClientCredentials credentials) {
        // TODO: validar - clientId y el clientSecret minimo 5 caracteres max 50. Sino lanzar excepcion
        // TODO: validar - el clientId ya existe en la BD? Lanzar excepcion
        var newClient = this.clientRepository.save(credentials);
        if(null == newClient) {
            throw new RuntimeException("No se pudo salvar cliente");
        }
    }

    @Override
    public List<ClientCredentials> getAllClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public void deleteClient(String clientId) {
        // TODO: validar - clientId y el clientSecret minimo 5 caracteres max 50. Sino lanzar excepcion
        // TODO: validar si el clientId existe en la BD
        this.clientRepository.remove(clientId);
    }

    @Override
    public List<Session> getAllSessions() {
        return this.sessionRepository.findAll();
    }

    @Override
    public Session newSession(ClientCredentials credentials) {
        var dbCredentials = clientRepository.findByClientId(credentials.getClientId());
        if(null == dbCredentials) {
            throw new CredentialsException("client secret not found");
        }
        if (!dbCredentials.getClientSecret().equals(credentials.getClientSecret())) {
            throw new CredentialsException("Client secret not equal");
        }
        var sessionId = UUID.randomUUID().toString();
        var newSession = sessionRepository.save(
                new Session(dbCredentials.getClientId(), sessionId, SessionStatus.ACTIVE, new Date())
        );
        if(null == newSession) {
            throw new RuntimeException("La sesion NO se creo exitosamente");
        }
        return newSession;
    }

    @Override
    public Session updateSession(ClientCredentials credentials) {
        return null;
    }

    @Override
    public Session validateSession(String session) {
        var sessionInDb = this.sessionRepository.findBySessionId(session);
        if(null == sessionInDb) {
            return new Session(session, SessionStatus.INACTIVE);

        }

        return sessionInDb;
    }

    public class CredentialsException extends RuntimeException {

        public CredentialsException(String message) {
            super(message);
        }

    }
}
