package notificationservice.controller;

import notificationservice.model.Client;
import notificationservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping("/api/client/new")
    public Map<String, Long> addClient(@RequestBody @Valid Client client) {
        return clientService.addClient(client);
    }


    @PutMapping("/api/client/{id}")
    public void updateClient(@RequestBody @Valid Client client, @PathVariable long id) {
        clientService.updateClient(client, id);
    }

    @DeleteMapping("/api/client/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
    }
}
