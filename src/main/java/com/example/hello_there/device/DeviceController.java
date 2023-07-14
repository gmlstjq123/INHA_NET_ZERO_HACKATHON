package com.example.hello_there.device;

import com.example.hello_there.device.air_conditioner.AirConditioner;
import com.example.hello_there.device.air_conditioner.AirConditionerRepository;


import com.example.hello_there.device.kimchi_refrigerator.KimchiRefrigerator;
import com.example.hello_there.device.kimchi_refrigerator.KimchiRefrigeratorRepository;
import com.example.hello_there.device.refrigerator.Refrigerator;
import com.example.hello_there.device.refrigerator.RefrigeratorRepository;
import com.example.hello_there.device.refrigerator.RefrigeratorService;
import com.example.hello_there.device.rice_cooker.RiceCooker;
import com.example.hello_there.device.rice_cooker.RiceCookerRepository;
import com.example.hello_there.device.vaccum_cleaner.VaccumCleaner;
import com.example.hello_there.device.vaccum_cleaner.VaccumCleanerRepository;
import com.example.hello_there.device.washing_machine.WashingMachine;
import com.example.hello_there.device.washing_machine.WashingMachineRepository;
import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.exception.BaseResponseStatus;
import com.example.hello_there.json.JSONFileController;
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
            JSONFileController.Air air = new JSONFileController.Air(airConditioner.getCompanyName(),
                    airConditioner.getModelName(), airConditioner.getCoolingCapacity(), airConditioner.getMonthlyConsumption(),
                    airConditioner.getEnergyEfficiency(), airConditioner.getGrade(), airConditioner.getEmissionsPerHour(),
                    airConditioner.getName(), airConditioner.getPrice(), airConditioner.getScore());
            return new BaseResponse<>(air);
        }
        KimchiRefrigerator kimchiRefrigerator = kimchiRefrigeratorRepository.findKimchiRefrigeratorByModelName(modelName).orElse(null);
        if(kimchiRefrigerator != null) {
            JSONFileController.Kimchi kimchi = new JSONFileController.Kimchi(kimchiRefrigerator.getCompanyName(),
                    kimchiRefrigerator.getModelName(), kimchiRefrigerator.getStroageEfficiency(), kimchiRefrigerator.getMonthlyConsumption(),
                    kimchiRefrigerator.getEfficiencyRate(), kimchiRefrigerator.getGrade(), kimchiRefrigerator.getEmissionsPerHour(),
                    kimchiRefrigerator.getName(), kimchiRefrigerator.getPrice(), kimchiRefrigerator.getScore());
            return new BaseResponse<>(kimchi);
        }
        Refrigerator refrigerator = refrigeratorRepository.findRefrigeratorByModelName(modelName).orElse(null);
        if(refrigerator != null) {
            JSONFileController.Ref ref = new JSONFileController.Ref(refrigerator.getCompanyName(), refrigerator.getModelName(),
                    refrigerator.getMonthlyConsumption(), refrigerator.getAnnualCost(), refrigerator.getVolume(), refrigerator.getGrade(),
                    refrigerator.getEmissionsPerHour(), refrigerator.getName(), refrigerator.getPrice(), refrigerator.getScore());
            return new BaseResponse<>(ref);
        }
        RiceCooker riceCooker = riceCookerRepository.findRiceCookerByModelName(modelName).orElse(null);
        if(riceCooker != null) {
            JSONFileController.Rice rice = new JSONFileController.Rice(riceCooker.getCompanyName(), riceCooker.getModelName(),
                    riceCooker.getPowerConsumption(), riceCooker.getMaximumCapcaity(), riceCooker.getStandbyPower(),
                    riceCooker.getGrade(), riceCooker.getEmissionsPerHour(), riceCooker.getName(), riceCooker.getPrice(), riceCooker.getScore());
            return new BaseResponse<>(rice);
        }
        VaccumCleaner vaccumCleaner = vaccumCleanerRepository.findVaccumCleanerByModelName(modelName).orElse(null);
        if(vaccumCleaner != null) {
            JSONFileController.Vac vac = new JSONFileController.Vac(vaccumCleaner.getCompanyName(), vaccumCleaner.getCompleteDate(),
                    vaccumCleaner.getModelName(), vaccumCleaner.getTestInstitude(), vaccumCleaner.getManufacturer(),
                    vaccumCleaner.getIsDomestic(), vaccumCleaner.getPowerConsumption(), vaccumCleaner.getAnnualCost(),
                    vaccumCleaner.getSunctionPower(), vaccumCleaner.getGrade(), vaccumCleaner.getEmissionsPerHour(), vaccumCleaner.getName(),
                    vaccumCleaner.getPrice(), vaccumCleaner.getScore());
            return new BaseResponse<>(vac);
        }
        WashingMachine washingMachine = washingMachineRepository.findWashingMachineByModelName(modelName).orElse(null);
        if(washingMachine != null) {
            JSONFileController.Wash wash = new JSONFileController.Wash(washingMachine.getCompanyName(), washingMachine.getModelName(),
                    washingMachine.getWashingCapacity(), washingMachine.getOneTimeConsumption(), washingMachine.getGrade(),
                    washingMachine.getEfficiencyRate(), washingMachine.getEmissionsPerHour(),
                    washingMachine.getName(), washingMachine.getPrice(), washingMachine.getScore());
            return new BaseResponse<>(wash);
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
