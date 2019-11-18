package ru.alibaev.insuranceVirtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alibaev.insuranceVirtu.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, ClientRepositoryCustom {
}
