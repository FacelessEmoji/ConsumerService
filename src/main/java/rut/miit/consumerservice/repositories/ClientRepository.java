package rut.miit.consumerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rut.miit.consumerservice.models.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
