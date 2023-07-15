package com.example.hello_there.device.kimchi_refrigerator;

import com.example.hello_there.device.air_conditioner.AirConditioner;
import com.example.hello_there.device.air_conditioner.AirConditionerRepository;
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
//@RequestMapping("/kimchi-refigerator")
//public class KimchiRefrigeratorController {
//    private final KimchiRefrigeratorRepository kimchiRefrigeratorRepository;
//    @GetMapping("/read")
//    public BaseResponse<Page<JSONFileController.Kimchi>> getKimchiRefrigerator() {
//        Pageable pageable = PageRequest.of(0, 50);
//        Page<KimchiRefrigerator> airPage = kimchiRefrigeratorRepository.findAll(pageable);
//
//        List<JSONFileController.Wash> resultList = airPage.getContent().stream().map(air -> {
//            JSONFileController.Wash wash = new JSONFileController.Wash();
//            wash.setCompanyName(air.getCompanyName());
//            wash.setModelName(air.getModelName());
//            wash.setCoolingCapacity(air.getCoolingCapacity());
//            wash.setMonthlyConsumption(air.getMonthlyConsumption());
//            wash.setEnergyEfficiency(air.getEnergyEfficiency());
//            wash.setGrade(air.getGrade());
//            wash.setEmissionsPerHour(air.getEmissionsPerHour());
//            wash.setName(air.getName());
//            wash.setPrice(air.getPrice());
//            wash.setScore(air.getScore());
//            return wash;
//        }).collect(Collectors.toList());
//
//        Page<JSONFileController.Wash> resultPage = new PageImpl<>(resultList, pageable, airPage.getTotalElements());
//
//        return new BaseResponse<>(resultPage);
//    }
//
//}
