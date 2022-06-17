package tec.bd.social.authentication;

import java.util.Objects;

public class Session {

    private String clientId;

    private String sessionId;

    private SessionStatus status;

    public Session() {

    }

    public Session(String clientId, String sessionId, SessionStatus status) {
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(sessionId);
        Objects.requireNonNull(status);
        this.clientId = clientId;
        this.sessionId = sessionId;
        this.status = status;
    }

    public Session(String sessionId, SessionStatus status) {

        Objects.requireNonNull(sessionId);
        Objects.requireNonNull(status);

        this.sessionId = sessionId;
        this.status = status;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }
}
