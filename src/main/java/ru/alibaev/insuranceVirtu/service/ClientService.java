package ru.alibaev.insuranceVirtu.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alibaev.insuranceVirtu.dto.ClientDTO;
import ru.alibaev.insuranceVirtu.entity.Client;
import ru.alibaev.insuranceVirtu.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> searchClients(ClientDTO clientDTO) {
        return clientRepository.searchClients(clientDTO);
    }

    public Client saveClient(ClientDTO clientDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Client client = modelMapper.map(clientDTO, Client.class);
        return clientRepository.save(client);
    }

    public Client updateClient(ClientDTO clientDTO) {
        Client client = clientRepository.getOne(clientDTO.getId());
        client.setFirstname(clientDTO.getFirstname());
        client.setSurname(clientDTO.getSurname());
        client.setPatronymic(clientDTO.getPatronymic());
        client.setBirthday(clientDTO.getBirthday());
        client.setPassportSeries(clientDTO.getPassportSeries());
        client.setPassportNumber(clientDTO.getPassportNumber());
        return clientRepository.save(client);
    }

}
