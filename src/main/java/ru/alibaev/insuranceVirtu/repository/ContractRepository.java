package ru.alibaev.insuranceVirtu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alibaev.insuranceVirtu.entity.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
