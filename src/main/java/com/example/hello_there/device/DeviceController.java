package com.example.hello_there.device;

import com.example.hello_there.device.dto.GetDeviceRes;
import com.example.hello_there.device.dto.PostAutoRegisterReq;
import com.example.hello_there.exception.BaseException;
import com.example.hello_there.exception.BaseResponse;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;
    @PostMapping("/auto-register") // 모든 기기 자동 등록하는 임시의 저장소
    public BaseResponse<String> registerDevice() {
        try {
            List<PostAutoRegisterReq> postAutoRegisterReqList = deviceService.getDeviceInfo();
            for (PostAutoRegisterReq postAutoRegisterReq : postAutoRegisterReqList) {
                deviceService.createDevice(postAutoRegisterReq);
            }
            return new BaseResponse<>("공공 데이터를 이용해 기기 정보를 DB에 추가합니다.");
        } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/auto-register/air-conditioner")
    public BaseResponse<String> registerAirConditioner() {
        try {
            List<PostAutoRegisterReq> postAutoRegisterReqList = deviceService.getDeviceInfo();
            for (PostAutoRegisterReq postAutoRegisterReq : postAutoRegisterReqList) {
                deviceService.createDevice(postAutoRegisterReq);
            }
            return new BaseResponse<>("공공 데이터를 이용해 기기 정보를 DB에 추가합니다.");
        } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

//    @PostMapping("/auto-register/kimchi-refrigerator")
//    public BaseResponse<String> registerKimchiRefrigerator() {
//
//    }

    @PostMapping("/auto-register/refrigerator")
    public BaseResponse<String> registerRefrigerator() {
        try{
            deviceService.converetCsvToJson();
            return new BaseResponse<>("성공");
        } catch (IOException ioException) {
            return new BaseResponse<>("입력 파일 에러");
        } catch (CsvValidationException csvException) {
            return new BaseResponse<>("입력 파일 에러");
        }

    }

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

    @GetMapping("/compare")
    public BaseResponse<GetDeviceRes> compareDevice(@RequestParam String modelName) {
        try {
            return new BaseResponse<>(deviceService.compareDevice(modelName));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
