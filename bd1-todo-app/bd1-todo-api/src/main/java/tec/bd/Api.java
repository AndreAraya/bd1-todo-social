package tec.bd;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.authentication.SessionStatus;

import java.util.Map;

import static spark.Spark.*;

public class Api
{
    private final static Logger LOG = LoggerFactory.getLogger(Api.class);

    public static void main( String[] args )
    {

        var webAppContext = WebApplicationContext.init();
        Gson gson = webAppContext.getGson();
        var todoController = webAppContext.getTodoController();
        var authenticationClient = webAppContext.getAuthenticationClient();

        port(8081);

        // Autentication
        before((request, response) -> {

            var sessionParam = request.headers("x-session-id");
            if(null == sessionParam) {
                halt(401, "Unauthorized");
            }
            var session = authenticationClient.validateSession(sessionParam);
            if(session.getStatus() == SessionStatus.INACTIVE) {
                halt(401, "Unauthorized");
            }
        });

        // TODO: Agregar todos los URLs que se usan en el API
        options("/", (request, response) -> {
                response.header("Content-Type", "application/json");
                return Map.of(
                        "message", "TODOS API V1",
                        "find-all", "/api/v1/todos",
                        "find-by-status", "/api/v1/todos/{status}",
                        "create", "/api/v1/todos");
        }, gson::toJson);

        path("/api/v1/todos", () -> {
            before("/*", (q, a) -> LOG.info("Received api call"));
            get("", todoController::getAllTodos, gson::toJson);
            get("/", todoController::getAllTodos, gson::toJson);

            get("/status/:todo-status", todoController::getTodosByStatus, gson::toJson);

            get("/title", todoController::searchInTitle, gson::toJson);

            get("/startDate", todoController::startDateRange, gson::toJson);

            get("/:todo-id", todoController::getTodo, gson::toJson);

            // Create
            post("", "application/json", (request, response) -> todoController.createTodoRecord(request, response), gson::toJson);

            // Delete
            delete("/:todo-id", todoController::deleteTodoRecord, gson::toJson);

             // Update
            put("", "application/json", todoController::updatedTodoRecord, gson::toJson);

        });


    }



}
