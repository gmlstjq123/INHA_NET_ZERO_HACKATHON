package com.example.hello_there.device.washing_machine;

import com.example.hello_there.json.JSONFileController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WashingMachineService {
    private final WashingMachineRepository washingMachineRepository;
    @Transactional
    public void createWashingMachine(JSONFileController.Wash wash) {
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
}
