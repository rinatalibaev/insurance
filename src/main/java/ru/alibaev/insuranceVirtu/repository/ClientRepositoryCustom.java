package ru.alibaev.insuranceVirtu.repository;

import ru.alibaev.insuranceVirtu.dto.ClientDTO;
import ru.alibaev.insuranceVirtu.entity.Client;

import java.util.List;

public interface ClientRepositoryCustom {
    List<Client> searchClients (ClientDTO clientDTO);
}
