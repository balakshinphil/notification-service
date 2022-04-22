package notificationservice.repository;

import notificationservice.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findAll();

    boolean existsByPhoneNumber(String phoneNumber);
}
