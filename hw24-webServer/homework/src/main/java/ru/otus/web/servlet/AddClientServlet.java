package ru.otus.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.web.services.TemplateProcessor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AddClientServlet extends HttpServlet {
    private static final String ADD_CLIENT_PAGE_TEMPLATE = "addClient.html";
    private static final String TEMPLATE_ATTR_ADD_CLIENT = "addClient";

    private final TemplateProcessor templateProcessor;
    private final SessionFactory sessionFactory;

    public AddClientServlet(TemplateProcessor templateProcessor, SessionFactory sessionFactory) {
        this.templateProcessor = templateProcessor;
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        for (String parameter:Collections.list(req.getParameterNames())) {
            parameters.put(parameter, req.getParameter(parameter));
        }

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        dbServiceClient.saveClient(new Client(null, parameters.get("name")));
        response.sendRedirect(TEMPLATE_ATTR_ADD_CLIENT);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADD_CLIENT_PAGE_TEMPLATE));
    }
}
