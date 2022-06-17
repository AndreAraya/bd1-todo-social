package tec.bd.authentication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthenticationResource {

    @GET("sessions/validate")
    Call<Session> validateInServer(@Query("session") String session);
}
