package com.example.hello_there.device.rice_cooker;

import com.example.hello_there.json.JSONFileController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiceCookerService {
    private final RiceCookerRepository riceCookerRepository;
    @Transactional
    public void createRiceCooker(JSONFileController.Rice rice) {
        RiceCooker riceCooker = RiceCooker.builder()
                .companyName(rice.getCompanyName())
                .modelName(rice.getModelName())
                .powerConsumption(rice.getPowerConsumption())
                .maximumCapcaity(rice.getMaximumCapcaity())
                .standbyPower(rice.getStandbyPower())
                .grade(rice.getGrade())
                .emissionsPerHour(rice.getEmissionPerHour())
                .name(rice.getName())
                .price(rice.getPrice())
                .score(rice.getScore())
                .build();
        riceCookerRepository.save(riceCooker);
    }
}
