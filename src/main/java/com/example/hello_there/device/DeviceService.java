package com.example.hello_there.device;

import com.example.hello_there.board.photo.PostPhoto;
import com.example.hello_there.board.photo.PostPhotoService;
import com.example.hello_there.board.photo.dto.GetS3Res;
import com.example.hello_there.device.dto.GetDeviceRes;
import com.example.hello_there.device.dto.PostAutoRegisterReq;
import com.example.hello_there.exception.BaseException;
import com.example.hello_there.utils.S3Service;
import com.example.hello_there.utils.UtilService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
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
    private final DeviceRepository deviceRepository;
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

    public GetDeviceRes compareDevice(String modelName) throws BaseException {
        // 사용자 기기의 수치
        Device device = utilService.findByDeviceModelNameWithValidation(modelName);
        double annualCostDivByVol = device.getAnnualCost() / device.getVolume();
        double emissionPerHourDivByVol = device.getEmissionsPerHour() / device.getVolume();
        double maxPowerConsumptionDivByVol = device.getMaxPowerConsumption() / device.getVolume();

        // 1등급 제품의 평균 수치
        double avgFirstAnnualCostDivByVol = deviceRepository.getAvgFirstAnnualCostDivByVol();
        double avgFirstemissionPerHourDivByVol = deviceRepository.getAvgFirstAnnualCostDivByVol();
        double avgMaxPowerConsumptionDivByVol = deviceRepository.getAvgFirstAnnualCostDivByVol();

        GetDeviceRes getDeviceRes = new GetDeviceRes(
                annualCostDivByVol - avgFirstAnnualCostDivByVol,
                emissionPerHourDivByVol - avgFirstemissionPerHourDivByVol,
                maxPowerConsumptionDivByVol - avgMaxPowerConsumptionDivByVol);
        return getDeviceRes;
    }

    @Transactional
    public String uploadPhoto(MultipartFile multipartFiles) {
        if (multipartFiles != null) {
            GetS3Res getS3Res = s3Service.uploadSingleFile(multipartFiles);
            postPhotoService.savePhoto(getS3Res);
        }
        return "사진 업로드를 완료했습니다";
    }



}


