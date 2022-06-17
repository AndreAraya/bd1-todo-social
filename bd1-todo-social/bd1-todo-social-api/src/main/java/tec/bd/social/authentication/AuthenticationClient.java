package tec.bd.social.authentication;

public interface AuthenticationClient {

    Session validateSession(String session);
}
