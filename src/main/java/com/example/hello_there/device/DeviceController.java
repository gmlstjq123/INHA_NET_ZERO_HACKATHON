package com.example.hello_there.device;

import com.example.hello_there.device.dto.GetDeviceRes;
import com.example.hello_there.device.dto.PostAutoRegisterReq;
import com.example.hello_there.exception.BaseException;
import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.univ.SchoolType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/auto-register")
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

    @GetMapping("/compare")
    public BaseResponse<GetDeviceRes> compareDevice(@RequestParam String modelName) {
        try {
            return new BaseResponse<>(deviceService.compareDevice(modelName));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }
}
