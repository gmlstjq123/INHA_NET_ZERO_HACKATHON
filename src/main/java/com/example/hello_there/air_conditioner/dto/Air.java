package com.example.hello_there.air_conditioner.dto;

import com.example.hello_there.air_conditioner.AirConditioner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Air {
    private String companyName;
    private String modelName;
    private int coolingCapacity;
    private double monthlyConsumption;
    private double energyEfficiency;
    private String grade;
    private double emissionsPerHour;
    private String name;
    private int price;
    private int score;

    public static Air mapFromAirConditioner(AirConditioner airConditioner) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(airConditioner, Air.class);
    }
}