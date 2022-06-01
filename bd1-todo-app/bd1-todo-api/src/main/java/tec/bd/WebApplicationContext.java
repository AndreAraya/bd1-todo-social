package tec.bd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tec.bd.authentication.AuthenticationClient;
import tec.bd.authentication.AuthenticationClientImpl;
import tec.bd.authentication.AuthenticationResource;
import tec.bd.controller.TodoController;
import tec.bd.datasource.DBManager;
import tec.bd.datasource.HikariDBManager;
import tec.bd.repository.mysql.TodoRepositoryMySqlImpl;
import tec.bd.todo.repository.TodoRepository;

public class WebApplicationContext {

    private DBManager dbManager;

    private TodoRepository mysqlTodoRepository;

    private TodoController todoController;

    private ApplicationContext todoAppContext;

    private AuthenticationClient authenticationClient;

    private WebApplicationContext() {

    }

    public static WebApplicationContext init() {
        WebApplicationContext webAppContext = new WebApplicationContext();
        initAuthenticationClient(webAppContext);
        initDBManager(webAppContext);
        initTodoMySQLRepository(webAppContext);

        webAppContext.todoAppContext = ApplicationContext.init(webAppContext.mysqlTodoRepository);

        initTodoController(webAppContext);

        return webAppContext;
    }

    private static void initAuthenticationClient(WebApplicationContext webApplicationContext) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthenticationResource authenticationResource = retrofit.create(AuthenticationResource.class);
        webApplicationContext.authenticationClient = new AuthenticationClientImpl(authenticationResource);
    }

    private static void initDBManager(WebApplicationContext webApplicationContext) {
        HikariConfig hikariConfig = new HikariConfig();
        var jdbcUrl = System.getenv("TODO_DB_URL");
        var username = System.getenv("TODO_DB_USERNAME");
        var password = System.getenv("TODO_DB_PASSWORD");
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("connectionTestQuery", "SELECT 1");
        hikariConfig.addDataSourceProperty("maximumPoolSize", "4");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        DBManager dbManager = new HikariDBManager(hikariConfig);
        webApplicationContext.dbManager = dbManager;
    }

    private static void initTodoMySQLRepository(WebApplicationContext webApplicationContext) {
        var dbManager = webApplicationContext.dbManager;
        webApplicationContext.mysqlTodoRepository = new TodoRepositoryMySqlImpl(dbManager);
    }

    private static void initTodoController(WebApplicationContext webApplicationContext) {
        var todo = webApplicationContext.todoAppContext.getTodo();
        webApplicationContext.todoController = new TodoController(todo);
    }

    public ApplicationContext getTodoAppContext() {
        return todoAppContext;
    }

    public AuthenticationClient getAuthenticationClient() {
        return authenticationClient;
    }

    public TodoController getTodoController() {
        return todoController;
    }

    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

}
