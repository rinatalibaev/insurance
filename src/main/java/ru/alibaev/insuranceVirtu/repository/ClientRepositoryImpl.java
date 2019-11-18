package ru.alibaev.insuranceVirtu.repository;

import org.springframework.util.StringUtils;
import ru.alibaev.insuranceVirtu.dto.ClientDTO;
import ru.alibaev.insuranceVirtu.entity.Client;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepositoryCustom {

    private EntityManager entityManager;

    public ClientRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Client> searchClients (ClientDTO clientDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> clientRoot = cq.from(Client.class);
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(clientDTO.getSurname())) {
            Predicate predicateSurname = cb.like(clientRoot.get("surname"), clientDTO.getSurname());
            predicates.add(predicateSurname);
        }
        if (!StringUtils.isEmpty(clientDTO.getFirstname())) {
            Predicate predicateFirstname = cb.like(clientRoot.get("firstname"), clientDTO.getFirstname());
            predicates.add(predicateFirstname);
        }
        if (!StringUtils.isEmpty(clientDTO.getPatronymic())) {
            Predicate predicatePatronymic = cb.like(clientRoot.get("patronymic"), clientDTO.getPatronymic());
            predicates.add(predicatePatronymic);
        }
        Predicate predicate = cb.and(predicates.toArray(new Predicate[0]));
        cq.where(predicate);
        TypedQuery<Client> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
