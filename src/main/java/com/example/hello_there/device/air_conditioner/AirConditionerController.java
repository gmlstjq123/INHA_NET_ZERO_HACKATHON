package com.example.hello_there.device.air_conditioner;

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

//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/air-conditioner")
//public class AirConditionerController {
//    private final AirConditionerRepository airConditionerRepository;
//    @GetMapping("/read")
//    public BaseResponse<Page<JSONFileController.Wash>> getAirConditioners() {
//        Pageable pageable = PageRequest.of(0, 50);
//        Page<AirConditioner> airPage = airConditionerRepository.findAll(pageable);
//
//        List<JSONFileController.Wash> resultList = airPage.getContent().stream().map(air -> {
//            JSONFileController.Wash airData = new JSONFileController.Wash();
//            airData.setCompanyName(air.getCompanyName());
//            airData.setModelName(air.getModelName());
//            airData.setCoolingCapacity(air.getCoolingCapacity());
//            airData.setMonthlyConsumption(air.getMonthlyConsumption());
//            airData.setEnergyEfficiency(air.getEnergyEfficiency());
//            airData.setGrade(air.getGrade());
//            airData.setEmissionsPerHour(air.getEmissionsPerHour());
//            airData.setName(air.getName());
//            airData.setPrice(air.getPrice());
//            airData.setScore(air.getScore());
//            return airData;
//        }).collect(Collectors.toList());
//
//        Page<JSONFileController.Wash> resultPage = new PageImpl<>(resultList, pageable, airPage.getTotalElements());
//
//        return new BaseResponse<>(resultPage);
//    }
//
//}
