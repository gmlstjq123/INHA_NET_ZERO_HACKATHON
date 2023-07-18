package com.example.hello_there.kimchi_refrigerator.dto;

import com.example.hello_there.kimchi_refrigerator.KimchiRefrigerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Kimchi {
    private String companyName;
    private String modelName;
    private double stroageEfficiency;
    private double monthlyConsumption;
    private double efficiencyRate;
    private String grade;
    private double emissionsPerHour;
    private String name;
    private int price;
    private int score;

    public static Kimchi mapFromKimchiRefrigerator(KimchiRefrigerator kimchiRefrigerator) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(kimchiRefrigerator, Kimchi.class);
    }
}