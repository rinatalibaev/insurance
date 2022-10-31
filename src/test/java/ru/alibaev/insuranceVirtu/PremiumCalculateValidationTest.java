package ru.alibaev.insuranceVirtu;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.alibaev.insuranceVirtu.controller.PremiumController;
import ru.alibaev.insuranceVirtu.service.PremiumService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PremiumCalculateValidationTest {
    private MockMvc mockMvc;

    public PremiumCalculateValidationTest() {
    }

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new PremiumController(new PremiumService())).build();
    }

    @Test
    public void testPremiumCalculate()
            throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("buildingYear", "2019");
        jsonObject.put("endValidity", "2023-11-30");
        jsonObject.put("startValidity", "2022-12-01");
        jsonObject.put("insuranceAmount", "1024844");
        jsonObject.put("realtyArea", "200.2");
        jsonObject.put("realtyCoefficient", "1.2");
        String json = jsonObject.toJSONString();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/premium/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}