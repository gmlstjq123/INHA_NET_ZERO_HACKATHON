package com.example.hello_there.device.kimchi_refrigerator;

import com.example.hello_there.json.JSONFileController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KimchiRefrigeratorService {
    private final KimchiRefrigeratorRepository kimchiRefrigeratorRepository;
    @Transactional
    public void createKimchiRefrigerator(JSONFileController.Kimchi kimchi) {
        KimchiRefrigerator kimchiRefrigerator = KimchiRefrigerator.builder()
                .companyName(kimchi.getCompanyName())
                .modelName(kimchi.getModelName())
                .stroageEfficiency(kimchi.getStroageEfficiency())
                .monthlyConsumption(kimchi.getMonthlyConsumption())
                .efficiencyRate(kimchi.getEfficiencyRate())
                .grade(kimchi.getGrade())
                .emissionsPerHour(kimchi.getEmissionsPerHour())
                .name(kimchi.getName())
                .price(kimchi.getPrice())
                .score(kimchi.getScore())
                .build();
        kimchiRefrigeratorRepository.save(kimchiRefrigerator);
    }
}
