package rut.miit.consumerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rut.miit.consumerservice.models.entities.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master, String> {
//    TODO: Получить самого разгруженного мастера по определенной специальности. Для RMQ

}
