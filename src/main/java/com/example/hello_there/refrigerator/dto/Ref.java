package com.example.hello_there.refrigerator.dto;

import com.example.hello_there.refrigerator.Refrigerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ref {
    private String companyName;
    private String modelName;
    private double monthlyConsumption;
    private double volume;
    private String grade;
    private double emissionPerHour;
    private double maxPowerConsumption;
    private double annualCost;
    private String name;
    private int price;
    private int score;

    public static Ref mapFromRefrigerator(Refrigerator refrigerator) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(refrigerator, Ref.class);
    }
}
