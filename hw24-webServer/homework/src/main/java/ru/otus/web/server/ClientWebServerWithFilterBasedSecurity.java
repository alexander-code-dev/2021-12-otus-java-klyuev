package ru.otus.web.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.SessionFactory;
import ru.otus.web.services.TemplateProcessor;
import ru.otus.web.services.UserAuthService;
import ru.otus.web.servlet.AuthorizationFilter;
import ru.otus.web.servlet.LoginServlet;

import java.util.Arrays;

public class ClientWebServerWithFilterBasedSecurity extends ClientWebServerSimple {
    private final UserAuthService authService;

    public ClientWebServerWithFilterBasedSecurity(int port,
                                                  UserAuthService authService,
                                                  TemplateProcessor templateProcessor,
                                                  SessionFactory sessionFactory) {
        super(port, templateProcessor, sessionFactory);
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
