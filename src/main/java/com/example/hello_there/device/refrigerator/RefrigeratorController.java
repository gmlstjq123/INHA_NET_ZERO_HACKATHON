package com.example.hello_there.device.refrigerator;

import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.json.JSONFileController;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refrigerator")
public class RefrigeratorController {
    private final RefrigeratorRepository refrigeratorRepository;
    @GetMapping("/read")
    public BaseResponse<Page<JSONFileController.Ref>> getRefrigerators() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Refrigerator> refPage = refrigeratorRepository.findAll(pageable);

        List<JSONFileController.Ref> resultList = refPage.getContent().stream().map(ref -> {
            JSONFileController.Ref refData = new JSONFileController.Ref();
            refData.setCompanyName(ref.getCompanyName());
            refData.setModelName(ref.getModelName());
            refData.setMonthlyConsumption(ref.getMonthlyConsumption());
            refData.setVolume(ref.getVolume());
            refData.setGrade(ref.getGrade());
            refData.setEmissionPerHour(ref.getEmissionsPerHour());
            refData.setMaxPowerConsumption(ref.getMaxPowerConsumption());
            refData.setAnnualCost(ref.getAnnualCost());
            refData.setName(ref.getName());
            refData.setPrice(ref.getPrice());
            refData.setScore(ref.getScore());
            return refData;
        }).collect(Collectors.toList());

        Page<JSONFileController.Ref> resultPage = new PageImpl<>(resultList, pageable, refPage.getTotalElements());

        return new BaseResponse<>(resultPage);
    }
}
