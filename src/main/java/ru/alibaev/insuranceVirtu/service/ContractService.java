package ru.alibaev.insuranceVirtu.service;

import org.springframework.stereotype.Service;
import ru.alibaev.insuranceVirtu.entity.Contract;
import ru.alibaev.insuranceVirtu.repository.ContractRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService {

    private ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Integer> getYearsList(int currentYear, int pastYear) {
        List<Integer> yearsList = new ArrayList<>();
        for (int i = currentYear; i >= pastYear; i--) {
            yearsList.add(i);
        }
        return yearsList;
    }

    public Contract saveContract (Contract contract) {
        List<Contract> contractList = contractRepository.findAll();
        contractList = contractList.stream().filter(contract1 -> contract1.getContractNumber().equals(contract.getContractNumber())).collect(Collectors.toList());
        if (!contractList.isEmpty()) {
            return null;
        }
        return contractRepository.save(contract);
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractById(long contractId) {
        return contractRepository.getOne(contractId);
    }

    public Contract updateContract(Contract contract) {
        Contract contractFromDatabase = contractRepository.getOne(contract.getId());
        contractFromDatabase.setInsuranceAmount(contract.getInsuranceAmount());
        contractFromDatabase.setRealtyType(contract.getRealtyType());
        contractFromDatabase.setBuildingYear(contract.getBuildingYear());
        contractFromDatabase.setRealtyArea(contract.getRealtyArea());
        contractFromDatabase.setStartValidity(contract.getStartValidity());
        contractFromDatabase.setEndValidity(contract.getEndValidity());
        contractFromDatabase.setCalculateDate(contract.getCalculateDate());
        contractFromDatabase.setPremiumAmount(contract.getPremiumAmount());
        contractFromDatabase.setContractNumber(contract.getContractNumber());
        contractFromDatabase.setConclusionDate(contract.getConclusionDate());
        contractFromDatabase.setClientFio(contract.getClientFio());
        contractFromDatabase.setClientBirthday(contract.getClientBirthday());
        contractFromDatabase.setPassportSeries(contract.getPassportSeries());
        contractFromDatabase.setPassportNumber(contract.getPassportNumber());
        contractFromDatabase.setRealtyState(contract.getRealtyState());
        contractFromDatabase.setRealtyIndex(contract.getRealtyIndex());
        contractFromDatabase.setRealtyRegion(contract.getRealtyRegion());
        contractFromDatabase.setRealtyDistrict(contract.getRealtyDistrict());
        contractFromDatabase.setRealtyLocality(contract.getRealtyLocality());
        contractFromDatabase.setRealtyStreet(contract.getRealtyStreet());
        contractFromDatabase.setRealtyHouse(contract.getRealtyHouse());
        contractFromDatabase.setRealtyHousing(contract.getRealtyHousing());
        contractFromDatabase.setRealtyBuilding(contract.getRealtyBuilding());
        contractFromDatabase.setRealtyApartment(contract.getRealtyApartment());
        contractFromDatabase.setContractCommentText(contract.getContractCommentText());
        return contractRepository.save(contractFromDatabase);
    }
}
