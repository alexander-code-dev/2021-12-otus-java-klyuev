package ru.otus.web;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.web.server.ClientWebServer;
import ru.otus.web.server.ClientWebServerWithFilterBasedSecurity;
import ru.otus.web.services.TemplateProcessor;
import ru.otus.web.services.TemplateProcessorImpl;
import ru.otus.web.services.UserAuthService;
import ru.otus.web.services.UserAuthServiceImpl;

public class WebServer {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        // db
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        String dbUrl = configuration.getProperty("hibernate.connection.url");
        String dbUserName = configuration.getProperty("hibernate.connection.username");
        String dbPassword = configuration.getProperty("hibernate.connection.password");
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration,
                Client.class, Phone.class, Address.class);
        // web server
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl();
        ClientWebServer clientWebServer = new ClientWebServerWithFilterBasedSecurity(
                WEB_SERVER_PORT,
                authService,
                templateProcessor,
                sessionFactory);
        clientWebServer.start();
        clientWebServer.join();
    }
}
