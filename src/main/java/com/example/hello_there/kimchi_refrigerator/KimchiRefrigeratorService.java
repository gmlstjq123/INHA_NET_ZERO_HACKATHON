package com.example.hello_there.kimchi_refrigerator;

import com.example.hello_there.kimchi_refrigerator.dto.Kimchi;
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
public class KimchiRefrigeratorService {
    private final KimchiRefrigeratorRepository kimchiRefrigeratorRepository;
    @Transactional
    public void createKimchiRefrigerator(Kimchi kimchi) {
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


    public Page<Kimchi> getKimchiRefrigerators(Pageable pageable) {
        Page<KimchiRefrigerator> kimchiPage = kimchiRefrigeratorRepository.findAll(pageable);
        List<Kimchi> resultList = kimchiPage.getContent().stream().map(kimchi -> {
            Kimchi kimchiData = new Kimchi();
            kimchiData.setCompanyName(kimchi.getCompanyName());
            kimchiData.setModelName(kimchi.getModelName());
            kimchiData.setStroageEfficiency(kimchi.getStroageEfficiency());
            kimchiData.setMonthlyConsumption(kimchi.getMonthlyConsumption());
            kimchiData.setEfficiencyRate(kimchi.getEfficiencyRate());
            kimchiData.setGrade(kimchi.getGrade());
            kimchiData.setEmissionsPerHour(kimchi.getEmissionsPerHour());
            kimchiData.setName(kimchi.getName());
            kimchiData.setPrice(kimchi.getPrice());
            kimchiData.setScore(kimchi.getScore());
            return kimchiData;
        }).collect(Collectors.toList());

        return new PageImpl<>(resultList, pageable, kimchiPage.getTotalElements());
    }
}
