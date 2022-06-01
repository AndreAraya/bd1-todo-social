package tec.bd.authentication;

import retrofit2.Retrofit;

public class AuthenticationClientImpl implements AuthenticationClient{

    private AuthenticationResource authenticationResource;

    public AuthenticationClientImpl(AuthenticationResource authenticationResource) {
        this.authenticationResource = authenticationResource;
    }

    public Session validateSession(String session) {
        try {
            return authenticationResource.validateInServer(session).execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            return new Session(session, SessionStatus.INACTIVE);
        }
    }

}
