package com.example.hello_there.device.washing_machine;

import com.example.hello_there.device.air_conditioner.AirConditioner;
import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.json.JSONFileController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/washing-machine")
public class WashingMachineController {
    private final WashingMachineRepository washingMachineRepository;
    @GetMapping("/read")
    public BaseResponse<Page<JSONFileController.Wash>> getWashingMachines() {
        Pageable pageable = PageRequest.of(0, 50);
        Page<WashingMachine> washPage = washingMachineRepository.findAll(pageable);

        List<JSONFileController.Wash> resultList = washPage.getContent().stream().map(wash -> {
            JSONFileController.Wash washData = new JSONFileController.Wash();
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

        Page<JSONFileController.Wash> resultPage = new PageImpl<>(resultList, pageable, washPage.getTotalElements());

        return new BaseResponse<>(resultPage);
    }

}
