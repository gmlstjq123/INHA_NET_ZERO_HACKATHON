package com.example.hello_there.device.vaccum_cleaner;

import com.example.hello_there.json.JSONFileController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VaccumCleanerService {
    private final VaccumCleanerRepository vaccumCleanerRepository;
    @Transactional
    public void createVaccumCleaner(JSONFileController.Vac vac) {
        VaccumCleaner vaccumCleaner = VaccumCleaner.builder()
                .companyName(vac.getCompanyName())
                .completeDate(vac.getCompleteDate())
                .modelName(vac.getModelName())
                .testInstitude(vac.getTestInstitude())
                .manufacturer(vac.getManufacturer())
                .isDomestic(vac.getIsDomestic())
                .powerConsumption(vac.getPowerConsumption())
                .annualCost(vac.getAnnualCost())
                .sunctionPower(vac.getSunctionPower())
                .grade(vac.getGrade())
                .emissionsPerHour(vac.getEmissionsPerHour())
                .name(vac.getName())
                .price(vac.getPrice())
                .score(vac.getScore())
                .build();
        vaccumCleanerRepository.save(vaccumCleaner);
    }
}

