package com.example.hello_there.washing_machine;

import com.example.hello_there.json.JSONFileController;
import com.example.hello_there.refrigerator.Refrigerator;
import com.example.hello_there.washing_machine.dto.Wash;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WashingMachineService {
    private final WashingMachineRepository washingMachineRepository;
    @Transactional
    public void createWashingMachine(Wash wash) {
        WashingMachine washingMachine = WashingMachine.builder()
                .companyName(wash.getCompanyName())
                .modelName(wash.getModelName())
                .washingCapacity(wash.getWashingCapacity())
                .oneTimeConsumption(wash.getOneTimeConsumption())
                .grade(wash.getGrade())
                .efficiencyRate(wash.getEfficiencyRate())
                .emissionsPerHour(wash.getEmissionsPerHour())
                .name(wash.getName())
                .price(wash.getPrice())
                .score(wash.getScore())
                .build();
        washingMachineRepository.save(washingMachine);
    }

    public Page<Wash> getWashingMachines(Pageable pageable) {
        Page<WashingMachine> washPage = washingMachineRepository.findAll(pageable);

        List<Wash> resultList = washPage.getContent().stream().map(wash -> {
            Wash washData = new Wash();
            washData.setCompanyName(wash.getCompanyName());
            washData.setModelName(wash.getModelName());
            washData.setOneTimeConsumption(wash.getOneTimeConsumption());
            washData.setGrade(wash.getGrade());
            washData.setEfficiencyRate(wash.getEfficiencyRate());
            washData.setEmissionsPerHour(wash.getEmissionsPerHour());
            washData.setName(wash.getName());
            washData.setPrice(wash.getPrice());
            washData.setScore(wash.getScore());
            return washData;
        }).collect(Collectors.toList());

        return new PageImpl<>(resultList, pageable, washPage.getTotalElements());
    }
}
