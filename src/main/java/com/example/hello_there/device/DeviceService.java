package com.example.hello_there.device;

import com.example.hello_there.board.photo.PostPhoto;
import com.example.hello_there.board.photo.PostPhotoService;
import com.example.hello_there.board.photo.dto.GetS3Res;
import com.example.hello_there.device.air_conditioner.AirConditioner;
import com.example.hello_there.device.air_conditioner.AirConditionerRepository;
import com.example.hello_there.device.dto.GetDeviceRes;
import com.example.hello_there.device.dto.PostAutoRegisterReq;
import com.example.hello_there.device.kimchi_refrigerator.KimchiRefrigerator;
import com.example.hello_there.device.kimchi_refrigerator.KimchiRefrigeratorRepository;
import com.example.hello_there.device.refrigerator.Refrigerator;
import com.example.hello_there.device.refrigerator.RefrigeratorRepository;
import com.example.hello_there.device.rice_cooker.RiceCooker;
import com.example.hello_there.device.rice_cooker.RiceCookerRepository;
import com.example.hello_there.device.vaccum_cleaner.VaccumCleaner;
import com.example.hello_there.device.vaccum_cleaner.VaccumCleanerRepository;
import com.example.hello_there.device.washing_machine.WashingMachine;
import com.example.hello_there.device.washing_machine.WashingMachineRepository;
import com.example.hello_there.exception.BaseException;
import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.exception.BaseResponseStatus;
import com.example.hello_there.json.JSONFileController;
import com.example.hello_there.utils.S3Service;
import com.example.hello_there.utils.UtilService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final AirConditionerRepository airConditionerRepository;
    private final KimchiRefrigeratorRepository kimchiRefrigeratorRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final RiceCookerRepository riceCookerRepository;
    private final VaccumCleanerRepository vaccumCleanerRepository;
    private final WashingMachineRepository washingMachineRepository;
    private final UtilService utilService;
    private final S3Service s3Service;
    private final PostPhotoService postPhotoService;

    @Transactional
    public void createDevice(PostAutoRegisterReq postAutoRegisterReq) {
        Device device = Device.builder()
                .grade(postAutoRegisterReq.getGrade())
                .modelName(postAutoRegisterReq.getModelName())
                .emissionsPerHour(postAutoRegisterReq.getEmissionsPerHour())
                .annualCost(postAutoRegisterReq.getAnnualCost())
                .volume(postAutoRegisterReq.getVolume())
                .maxPowerConsumption(postAutoRegisterReq.getMaxPowerConsumption())
                .itemName(postAutoRegisterReq.getItemName())
                .build();
        deviceRepository.save(device);
    }
    public List<PostAutoRegisterReq> getDeviceInfo() throws UnsupportedEncodingException {
        WebClient webClient = WebClient.create();
        String serviceKey = "3bOb7HStMQDC44spijePkGaD6QUjfK02jgBW2JVhYWPbqMQkdmHpUX5RnR94WyF4YdnbIBauhir7yZ/saAoBAg==";
        // int totalPageCount = getTotalPageCount(serviceKey);
        int totalPageCount = 100;
        List<PostAutoRegisterReq> deviceInfoList = new ArrayList<>();

        for (int page = 1; page <= totalPageCount; page++) {
            String url = URLDecoder.decode("https://api.odcloud.kr/api/15083315/v1/uddi:e2de2c4d-e81b-421d-94bc-ad0f9cf70c44?perPage=10&page=" + page + "&serviceKey=" + serviceKey, "UTF-8");

            Mono<String> responseMono = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class);

            String univInfo = responseMono.block();

            Gson gsonObj = new Gson();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gsonObj.fromJson(univInfo, type);

            List<Map<String, Object>> deviceList = (List<Map<String, Object>>) data.get("data");
            deviceInfoList.addAll(deviceList.stream()
                    .map(device -> {
                        String grade = (String) device.get("등급");
                        String modelName = (String) device.get("모델명");
                        double emissionsPerHour = Double.parseDouble((String) device.get("시간당 이산화탄소 배출량"));
                        double annualCost = Double.parseDouble(String.valueOf(device.get("연간 에너지 비용")));
                        double volume = Double.parseDouble((String) device.get("용량"));
                        double maxPowerConsumption = Double.parseDouble((String) device.get("최대소비전력량"));
                        String itemName = (String) device.get("품목명");

                        return new PostAutoRegisterReq(grade, modelName, emissionsPerHour, annualCost, volume, maxPowerConsumption, itemName);
                    })
                    .collect(Collectors.toList()));
        }

        return deviceInfoList;
    }

    private int getTotalPageCount(String serviceKey) throws UnsupportedEncodingException {
        WebClient webClient = WebClient.create();
        String url = URLDecoder.decode("https://api.odcloud.kr/api/15083315/v1/uddi:e2de2c4d-e81b-421d-94bc-ad0f9cf70c44?page=1&perPage=1&serviceKey=" + serviceKey, "UTF-8");

        Mono<String> responseMono = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        String univInfo = responseMono.block();

        Gson gsonObj = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gsonObj.fromJson(univInfo, type);

        int totalCount = Integer.parseInt(String.valueOf(data.get("totalCount")));
        int perPage = Integer.parseInt(String.valueOf(data.get("perPage")));
        int totalPageCount = (int) Math.ceil((double) totalCount / perPage);

        return totalPageCount;
    }

    @Transactional
    public BaseResponse<?> uploadPhoto(MultipartFile multipartFiles) {
        if (multipartFiles != null) {
            GetS3Res getS3Res = s3Service.uploadSingleFile(multipartFiles);
            postPhotoService.savePhoto(getS3Res);
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.exec("import test");
            PyObject result = interpreter.eval("text.process_text_detection()");
            String modelName = result.toString();

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
                        refrigerator.getMonthlyConsumption(), refrigerator.getVolume(), refrigerator.getGrade(),
                        refrigerator.getEmissionsPerHour(), refrigerator.getMaxPowerConsumption(), refrigerator.getAnnualCost(),
                        refrigerator.getName(), refrigerator.getPrice(), refrigerator.getScore());
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
        else {
            return new BaseResponse<>(BaseResponseStatus.NONE_EXIST_PHOTO);
        }
    }
}


