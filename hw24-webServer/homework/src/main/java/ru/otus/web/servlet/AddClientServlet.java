package ru.otus.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.web.services.TemplateProcessor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddClientServlet extends HttpServlet {
    private static final String ADD_CLIENT_PAGE_TEMPLATE = "addClient.html";
    private static final String TEMPLATE_ATTR_ADD_CLIENT = "addClient";

    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;

    public AddClientServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        for (String parameter:Collections.list(req.getParameterNames())) {
            parameters.put(parameter, req.getParameter(parameter));
        }

        Phone phone = new Phone(null, parameters.get("phone"));
        Address address = new Address(null, parameters.get("address"));
        dbServiceClient.saveClient(new Client(null, parameters.get("name"), address, List.of(phone)));
        response.sendRedirect(TEMPLATE_ATTR_ADD_CLIENT);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADD_CLIENT_PAGE_TEMPLATE));
    }
}
