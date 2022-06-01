package tec.bd.todo.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import tec.bd.todo.auth.datasource.DBManager;
import tec.bd.todo.auth.datasource.HikariDBManager;
import tec.bd.todo.auth.repository.ClientRepository;
import tec.bd.todo.auth.repository.ClientRepositoryImpl;
import tec.bd.todo.auth.repository.SessionRepository;
import tec.bd.todo.auth.repository.SessionRepositoryImpl;
import tec.bd.todo.auth.service.SessionService;
import tec.bd.todo.auth.service.SessionServiceImpl;

public class WebApplicationContext {

    private DBManager dbManager;

    private ClientRepository clientRepository;

    private SessionRepository sessionRepository;

    private SessionService sessionService;


    private WebApplicationContext() {


    }

    public static WebApplicationContext init() {
        WebApplicationContext webAppContext = new WebApplicationContext();
        initDBManager(webAppContext);
        initClientRepository(webAppContext);
        initSessionRepository(webAppContext);
        initSessionService(webAppContext);

        initSessionService(webAppContext);

        return webAppContext;
    }

    private static void initDBManager(WebApplicationContext webApplicationContext) {
        HikariConfig hikariConfig = new HikariConfig();
        var jdbcUrl = System.getenv("TODO_AUTH_DB_URL");
        var username = System.getenv("TODO_AUTH_DB_USERNAME");
        var password = System.getenv("TODO_AUTH_DB_PASSWORD");
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


    private static void initClientRepository(WebApplicationContext webApplicationContext) {
        var dbManager = webApplicationContext.dbManager;
        webApplicationContext.clientRepository = new ClientRepositoryImpl(dbManager);
    }

    private static void initSessionRepository(WebApplicationContext webApplicationContext) {
        var dbManager = webApplicationContext.dbManager;
        webApplicationContext.sessionRepository = new SessionRepositoryImpl(dbManager);
    }

    private static void initSessionService(WebApplicationContext webApplicationContext) {
        var clientRepository = webApplicationContext.clientRepository;
        var sessionRepository = webApplicationContext.sessionRepository;
        webApplicationContext.sessionService = new SessionServiceImpl(clientRepository, sessionRepository);
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public SessionRepository getSessionRepository() {
        return sessionRepository;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }
}
