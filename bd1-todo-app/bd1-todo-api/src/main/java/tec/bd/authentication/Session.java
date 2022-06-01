package tec.bd.authentication;

import java.util.Objects;

public class Session {

    public String sessionId;

    public SessionStatus status;

    public Session() {

    }

    public Session(String sessionId, SessionStatus status) {
        Objects.requireNonNull(sessionId);
        Objects.requireNonNull(status);
        this.sessionId = sessionId;
        this.status = status;
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
