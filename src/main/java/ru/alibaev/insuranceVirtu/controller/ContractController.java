package ru.alibaev.insuranceVirtu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alibaev.insuranceVirtu.dto.ContractDTO;
import ru.alibaev.insuranceVirtu.entity.Contract;
import ru.alibaev.insuranceVirtu.enumeration.RealtyTypeEnum;
import ru.alibaev.insuranceVirtu.service.ContractService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping ("/contract")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping ("/createContract")
    public String getCreateContractPage (Model model) {
        model.addAttribute("yearsList", contractService.getYearsList(LocalDate.now().getYear(),LocalDate.now().minusYears(100).getYear()));
        model.addAttribute("contractDTO", new ContractDTO());
        model.addAttribute("realtyTypeEnum", RealtyTypeEnum.values());
        model.addAttribute("localDateNow", LocalDate.now());
        return "/contract/createContract";
    }

    @PostMapping ("/createContract")
    public @ResponseBody Contract createContract (@RequestBody Contract contract) {
        if (contract.getId() == null) {
            return contractService.saveContract(contract);
        } else {
            return contractService.updateContract(contract);
        }
    }

    @GetMapping ("/getContractPage")
    public String getContractPage () {
        return "/contract/createContract";
    }

    @GetMapping ("/getContracts")
    public String getAllContracts (Model model) {
        return "/contract/contracts";
    }
    @PostMapping ("/getContracts")
    public @ResponseBody List<Contract> getAllContractsInfo () {
        return contractService.getAllContracts();
    }

    @GetMapping ("/getContractById")
    public String getContractById (@RequestParam @NotNull long contractId, Model model) {
        model.addAttribute("yearsList", contractService.getYearsList(LocalDate.now().getYear(),LocalDate.now().minusYears(100).getYear()));
        model.addAttribute("realtyTypeEnum", RealtyTypeEnum.values());
        model.addAttribute("contract", contractService.getContractById(contractId));
        return "/contract/editContract";
    }
}
