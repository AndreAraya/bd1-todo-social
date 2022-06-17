package tec.bd.social;

import com.google.gson.Gson;
import tec.bd.social.authentication.SessionStatus;

import java.util.Map;

import static spark.Spark.*;

public class SocialApi
{
    public static void main( String[] args )
    {

        WebApplicationContext context = WebApplicationContext.init();
        var authenticationClient = context.getAuthenticationClient();
        var ratingsService = context.getRatingsService();
        Gson gson = context.getGson();

        port(8082);

        // Autentication
//        before((request, response) -> {
//
//            var sessionParam = request.headers("x-session-id");
//            if(null == sessionParam) {
//                halt(401, "Unauthorized");
//            }
//            var session = authenticationClient.validateSession(sessionParam);
//            if(session.getStatus() == SessionStatus.INACTIVE) {
//                halt(401, "Unauthorized");
//            }
//        });

        options("/", (request, response) -> {
            response.header("Content-Type", "application/json");
            return Map.of(
                    "message", "SOCIAL API V1");
        }, gson::toJson);

        //Para esta prueba inicial vamos a obtener solamente un rating basado en un ID
        get ("/ratings/:rating-id", (request, response) -> {
            var ratingIdParam = request.params("rating-id");

            //Cuidado revisar que sea numero
            var ratingId = Integer.parseInt(ratingIdParam);

            var rating = ratingsService.getRating(ratingId);
            if (null != rating) {
                return rating;
            }

            response.status(404);
            return Map.of();
//            response.header("Content-Type", "application/json");
//            return Map.of(
//                    "message", "Get rating for todo-id " + todoId);
            }, gson::toJson);

        //Obtiene el valor promedio de los ratings de un todoId
//        get ("todos/:todo-id/rating", (request, response) -> {
//            var todoId = request.params("todo-id");
//            response.header("Content-Type", "application/json");
//            return Map.of(
//                    "message", "Get rating for todo-id " + todoId);
//        }, gson::toJson);
    }
}
