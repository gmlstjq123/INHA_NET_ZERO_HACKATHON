package com.example.hello_there.washing_machine.dto;

import com.example.hello_there.washing_machine.WashingMachine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wash {
    private String companyName;
    private String modelName;
    private int washingCapacity;
    private double oneTimeConsumption;
    private String grade;
    private double efficiencyRate;
    private double emissionsPerHour;
    private String name;
    private int price;
    private int score;

    public static Wash mapFromWashingMachine(WashingMachine washingMachine) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(washingMachine, Wash.class);
    }
}
