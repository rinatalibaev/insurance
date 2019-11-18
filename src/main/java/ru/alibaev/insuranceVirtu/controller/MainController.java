package ru.alibaev.insuranceVirtu.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping ("/")
public class MainController {

    @GetMapping
    public String getCreateContractPage (Model model) {
        return "redirect:/contract/getContracts";
    }
}
