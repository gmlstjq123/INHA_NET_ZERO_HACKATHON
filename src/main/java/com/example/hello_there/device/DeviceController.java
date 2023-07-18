package com.example.hello_there.device;

import com.example.hello_there.air_conditioner.AirConditionerRepository;


import com.example.hello_there.kimchi_refrigerator.KimchiRefrigeratorRepository;
import com.example.hello_there.refrigerator.RefrigeratorRepository;
import com.example.hello_there.refrigerator.RefrigeratorService;
import com.example.hello_there.washing_machine.WashingMachineRepository;
import com.example.hello_there.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final RefrigeratorService refrigeratorService;
    private final AirConditionerRepository airConditionerRepository;
    private final KimchiRefrigeratorRepository kimchiRefrigeratorRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final WashingMachineRepository washingMachineRepository;

//    @GetMapping("/compare")
//    public BaseResponse<GetDeviceRes> compareDevice(@RequestParam String modelName) {
//        try {
//            return new BaseResponse<>(deviceService.compareDevice(modelName));
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

    @GetMapping("/find/text")
    public BaseResponse<?> findModelByText(@RequestParam String modelName) {
        return deviceService.findModelByText(modelName);
    }

    @PostMapping("/find/photo")
    public BaseResponse<String> findModelByPhoto(@RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return new BaseResponse<>(deviceService.uploadPhoto(multipartFile));
    }

}
