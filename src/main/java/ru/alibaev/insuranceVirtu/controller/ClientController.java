package ru.alibaev.insuranceVirtu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.alibaev.insuranceVirtu.dto.ClientDTO;
import ru.alibaev.insuranceVirtu.entity.Client;
import ru.alibaev.insuranceVirtu.service.ClientService;

import java.util.List;

@Controller
@RequestMapping ("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping ("/searchClients")
    public @ResponseBody List<Client> searchClient (@RequestBody ClientDTO clientDTO) {
        return clientService.searchClients(clientDTO);
    }

    @PostMapping ("/saveClient")
    public @ResponseBody Client saveClient (@RequestBody ClientDTO clientDTO) {
        if (clientDTO.getId() != null) {
            return clientService.updateClient(clientDTO);
        } else {
            return clientService.saveClient(clientDTO);
        }
    }
}
