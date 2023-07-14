package com.example.hello_there.device.refrigerator;

import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.json.JSONFileController;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refrigerator")
public class RefrigeratorController {
    private final RefrigeratorRepository refrigeratorRepository;
    @GetMapping("/read")
    public BaseResponse<List<JSONFileController.Ref>> getRefrigerators() {
        List<Refrigerator> refList = refrigeratorRepository.findAll();
        List<JSONFileController.Ref> resultList = new ArrayList<>();

        for (Refrigerator ref : refList) {
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
            resultList.add(refData);
        }

        return new BaseResponse<>(resultList);
    }
}
