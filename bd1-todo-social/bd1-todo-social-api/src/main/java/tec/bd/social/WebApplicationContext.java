package tec.bd.social;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tec.bd.social.authentication.AuthenticationClient;
import tec.bd.social.authentication.AuthenticationClientImpl;
import tec.bd.social.authentication.AuthenticationResource;
import tec.bd.social.datasource.DBManager;
import tec.bd.social.datasource.HikariDBManager;
import tec.bd.social.repository.RatingsRepository;
import tec.bd.social.repository.RatingsRepositoryImpl;
import tec.bd.social.service.RatingsService;
import tec.bd.social.service.RatingsServiceImpl;

public class WebApplicationContext {

    private AuthenticationClient authenticationClient;

    private DBManager dbManager;

    private RatingsRepository ratingsRepository;
    private RatingsService ratingsService;

    private WebApplicationContext() {

    }

    public static WebApplicationContext init() {
        WebApplicationContext webAppContext = new WebApplicationContext();
        initAuthenticationClient(webAppContext);

        initDBManager(webAppContext);
        initRatingsRepository(webAppContext);
        initRatingsService(webAppContext);

        return webAppContext;
    }

    private static void initAuthenticationClient(WebApplicationContext webApplicationContext) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/") //url del servidor de autenticacion
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthenticationResource authenticationResource = retrofit.create(AuthenticationResource.class);
        webApplicationContext.authenticationClient = new AuthenticationClientImpl(authenticationResource);
    }

    private static void initDBManager(WebApplicationContext webApplicationContext) {
        HikariConfig hikariConfig = new HikariConfig();
        var jdbcUrl = System.getenv("JDBC_SOCIAL_DB_URL");
        var username = System.getenv("JDBC_SOCIAL_DB_USERNAME");
        var password = System.getenv("JDBC_SOCIAL_DB_PASSWORD");
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

    private static void initRatingsRepository(WebApplicationContext webApplicationContext) {
        var dbManager = webApplicationContext.dbManager;
        webApplicationContext.ratingsRepository = new RatingsRepositoryImpl(dbManager);
    }

    private static void initRatingsService(WebApplicationContext webApplicationContext){
        var ratingsRepository = webApplicationContext.ratingsRepository;
        webApplicationContext.ratingsService = new RatingsServiceImpl(ratingsRepository);
    }

    public AuthenticationClient getAuthenticationClient(){
        return this.authenticationClient;
    }

    public RatingsRepository getRatingsRepository(){
        return this.ratingsRepository;
    }

    public RatingsService getRatingsService(){
        return this.ratingsService;
    }

    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }
}
