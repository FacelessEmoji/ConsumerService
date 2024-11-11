package rut.miit.consumerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rut.miit.consumerservice.models.entities.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, String> {
    Part findByName(String name);
}
