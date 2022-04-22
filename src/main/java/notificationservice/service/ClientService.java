package notificationservice.service;

import notificationservice.exception.ClientExistsException;
import notificationservice.exception.ClientNotFoundException;
import notificationservice.model.Client;
import notificationservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }



    public Map<String, Long> addClient(Client client) {
        if (clientRepository.existsByPhoneNumber(client.getPhoneNumber())) {
            throw new ClientExistsException(String.format("Client with phone number %s already exists!",
                    client.getPhoneNumber()));
        }

        clientRepository.save(client);
        return Map.of("id", client.getId());
    }


    public void updateClient(Client newClient, long oldClientId) {
        if (!clientRepository.existsById(oldClientId)) {
            throw new ClientNotFoundException(String.format("Client with id %d not found!", oldClientId));
        }

        newClient.setId(oldClientId);

        clientRepository.save(newClient);
    }


    public void deleteClient(long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ClientNotFoundException(String.format("Client with id %d not found!", clientId));
        }
        clientRepository.deleteById(clientId);
    }

}
