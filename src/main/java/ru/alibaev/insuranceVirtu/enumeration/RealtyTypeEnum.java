package ru.alibaev.insuranceVirtu.enumeration;

public enum RealtyTypeEnum {

    APARTMENT ("apartment", 1.7, "Квартира"),
    HOUSE ("house", 1.5, "Дом"),
    ROOM ("room", 1.3, "Комната");

    private String name;
    private String text;
    private Double value;

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }


    RealtyTypeEnum(String name, Double value, String text) {
        this.name = name;
        this.value = value;
        this.text = text;
    }
}
