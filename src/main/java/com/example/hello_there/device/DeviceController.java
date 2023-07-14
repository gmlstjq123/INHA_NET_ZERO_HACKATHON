package com.example.hello_there.device;

import com.example.hello_there.device.air_conditioner.AirConditioner;
import com.example.hello_there.device.air_conditioner.AirConditionerRepository;

import com.example.hello_there.device.air_conditioner.dto.FindAirConditionerRes;
import com.example.hello_there.device.dto.GetDeviceRes;
import com.example.hello_there.device.dto.PostAutoRegisterReq;
import com.example.hello_there.device.kimchi_refrigerator.KimchiRefrigerator;
import com.example.hello_there.device.kimchi_refrigerator.KimchiRefrigeratorRepository;
import com.example.hello_there.device.refrigerator.Refrigerator;
import com.example.hello_there.device.refrigerator.RefrigeratorRepository;
import com.example.hello_there.device.refrigerator.RefrigeratorService;
import com.example.hello_there.device.rice_cooker.RiceCooker;
import com.example.hello_there.device.rice_cooker.RiceCookerRepository;
import com.example.hello_there.device.rice_cooker.RiceCookerService;
import com.example.hello_there.device.vaccum_cleaner.VaccumCleaner;
import com.example.hello_there.device.vaccum_cleaner.VaccumCleanerRepository;
import com.example.hello_there.device.washing_machine.WashingMachine;
import com.example.hello_there.device.washing_machine.WashingMachineRepository;
import com.example.hello_there.exception.BaseException;
import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.exception.BaseResponseStatus;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final RefrigeratorService refrigeratorService;
    private final AirConditionerRepository airConditionerRepository;
    private final KimchiRefrigeratorRepository kimchiRefrigeratorRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final RiceCookerRepository riceCookerRepository;
    private final VaccumCleanerRepository vaccumCleanerRepository;
    private final WashingMachineRepository washingMachineRepository;

//    @GetMapping("/compare")
//    public BaseResponse<GetDeviceRes> compareDevice(@RequestParam String modelName) {
//        try {
//            return new BaseResponse<>(deviceService.compareDevice(modelName));
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

    @PostMapping("/find/text")
    public BaseResponse<?> findModelByText(@RequestParam String modelName) {
        AirConditioner airConditioner = airConditionerRepository.findAirConditionerByModelName(modelName).orElse(null);
        if(airConditioner != null) {
            FindAirConditionerRes findAirConditionerRes = new FindAirConditionerRes();
            return new BaseResponse<>(findAirConditionerRes);
        }
        KimchiRefrigerator kimchiRefrigerator = kimchiRefrigeratorRepository.findKimchiRefrigeratorByModelName(modelName).orElse(null);
        if(kimchiRefrigerator != null) {
            FindAirConditionerRes findAirConditionerRes = new FindAirConditionerRes();
            return new BaseResponse<>(findAirConditionerRes);
        }
        Refrigerator refrigerator = refrigeratorRepository.findRefrigeratorByModelName(modelName).orElse(null);
        if(refrigerator != null) {
            FindAirConditionerRes findAirConditionerRes = new FindAirConditionerRes();
            return new BaseResponse<>(findAirConditionerRes);
        }
        RiceCooker riceCooker = riceCookerRepository.findRiceCookerByModelName(modelName).orElse(null);
        if(riceCooker != null) {
            FindAirConditionerRes findAirConditionerRes = new FindAirConditionerRes();
            return new BaseResponse<>(findAirConditionerRes);
        }
        VaccumCleaner vaccumCleaner = vaccumCleanerRepository.findVaccumCleanerByModelName(modelName).orElse(null);
        if(vaccumCleaner != null) {
            FindAirConditionerRes findAirConditionerRes = new FindAirConditionerRes();
            return new BaseResponse<>(findAirConditionerRes);
        }
        WashingMachine washingMachine = washingMachineRepository.findWashingMachineByModelName(modelName).orElse(null);
        if(washingMachine != null) {
            FindAirConditionerRes findAirConditionerRes = new FindAirConditionerRes();
            return new BaseResponse<>(findAirConditionerRes);
        }
        else {
            return new BaseResponse<>(BaseResponseStatus.NONE_EXIST_DEVICE);
        }
    }

    @PostMapping("/find/photo")
    public BaseResponse<?> findModelByPhoto(@RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return new BaseResponse<>(deviceService.uploadPhoto(multipartFile));
    }


//    @PostMapping("/auto-register") // 모든 기기 자동 등록하는 임시의 저장소
//    public BaseResponse<String> registerDevice() {
//        try {
//            List<PostAutoRegisterReq> postAutoRegisterReqList = deviceService.getDeviceInfo();
//            for (PostAutoRegisterReq postAutoRegisterReq : postAutoRegisterReqList) {
//                deviceService.createDevice(postAutoRegisterReq);
//            }
//            return new BaseResponse<>("공공 데이터를 이용해 기기 정보를 DB에 추가합니다.");
//        } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
//            throw new RuntimeException(e);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @PostMapping("/auto-register/kimchi-refrigerator")
//    public BaseResponse<String> registerKimchiRefrigerator() {
//
//    }

//    @PostMapping("/auto-register/rice-cooker")
//    public BaseResponse<String> registerRiceCooker() {
//
//    }

//    @PostMapping("/auto-register/vaccum-cleaner")
//    public BaseResponse<String> registerRiceCooker() {
//
//    }
//
//    @PostMapping("/auto-register/wahing-machine")
//    public BaseResponse<String> registerRiceCooker() {
//
//    }

}
