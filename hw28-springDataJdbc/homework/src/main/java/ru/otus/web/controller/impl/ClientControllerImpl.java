package ru.otus.web.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.entity.Address;
import ru.otus.dao.entity.Client;
import ru.otus.dao.entity.Phone;
import ru.otus.dao.service.DBService;
import ru.otus.web.form.ClientForm;

import java.util.List;
import java.util.Set;

@Controller
public class ClientControllerImpl {

    private final DBService<Client> dbService;

    public ClientControllerImpl(DBService<Client> dbService) {
        this.dbService = dbService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("message", "message");
        return "index";
    }

    @GetMapping(value = { "/clients" })
    public String clients(Model model) {
        List<Client> clients = dbService.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping(value = { "/addClient" })
    public String addClient(Model model) {
        ClientForm clientForm = new ClientForm();
        model.addAttribute("clientForm", clientForm);
        return "addClient";
    }

    @PostMapping(value = { "/addClient" })
    public String addClient(@ModelAttribute("personForm") ClientForm clientForm) {

        String name = clientForm.getName();
        String address = clientForm.getAddress();
        String phone = clientForm.getPhone();

        if (name != null && name.length() > 0) {
            dbService.saveEntity(new Client(null, name,
                    new Address(null, address, true),
                    Set.of(new Phone(null, phone, true)),
                    true)
            );
            return "redirect:/clients";
        }
        return "addClient";
    }
}
