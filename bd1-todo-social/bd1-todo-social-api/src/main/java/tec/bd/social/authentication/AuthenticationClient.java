package tec.bd.authentication;

public interface AuthenticationClient {

    Session validateSession(String session);
}
