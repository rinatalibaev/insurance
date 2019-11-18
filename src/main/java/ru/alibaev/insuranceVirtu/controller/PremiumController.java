package ru.alibaev.insuranceVirtu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.alibaev.insuranceVirtu.dto.PremiumCalculateDTO;
import ru.alibaev.insuranceVirtu.service.PremiumService;

import javax.validation.Valid;

@Controller
@RequestMapping ("/premium")
public class PremiumController {

    private PremiumService premiumService;

    public PremiumController(PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    @PostMapping ("/calculate")
    public @ResponseBody double calculatePremium (@RequestBody @Valid PremiumCalculateDTO premiumCalculateDTO) {
        return premiumService.calculatePremium(premiumCalculateDTO);
    }
}
