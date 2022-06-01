package tec.bd.todo.auth;

import java.util.Date;
import java.util.Objects;

public class Session {

    private String clientId;

    private String sessionId;

    private SessionStatus status;

    private Date createdAt;


    public Session() {

    }

//    public Session(String sessionId, SessionStatus status) {
//        Objects.requireNonNull(sessionId);
//        Objects.requireNonNull(status);
//        this.sessionId = sessionId;
//        this.status = status;
//    }

    public Session(String sessionId, SessionStatus status) {
        this(null, sessionId, status, null);
    }

    public Session(String clientId, String sessionId, SessionStatus status, Date createdAt) {
        this.clientId = clientId;
        this.sessionId = sessionId;
        this.status = status;
        this.createdAt = createdAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
