package com.example.hello_there.device;

import com.example.hello_there.board.photo.PostPhotoService;
import com.example.hello_there.board.photo.dto.GetS3Res;
import com.example.hello_there.device.air_conditioner.AirConditioner;
import com.example.hello_there.device.air_conditioner.AirConditionerRepository;
import com.example.hello_there.device.dto.PostAutoRegisterReq;
import com.example.hello_there.device.kimchi_refrigerator.KimchiRefrigerator;
import com.example.hello_there.device.kimchi_refrigerator.KimchiRefrigeratorRepository;
import com.example.hello_there.device.refrigerator.Refrigerator;
import com.example.hello_there.device.refrigerator.RefrigeratorRepository;
import com.example.hello_there.device.washing_machine.WashingMachine;
import com.example.hello_there.device.washing_machine.WashingMachineRepository;
import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.exception.BaseResponseStatus;
import com.example.hello_there.json.JSONFileController;
import com.example.hello_there.utils.S3Service;
import com.example.hello_there.utils.UtilService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final AirConditionerRepository airConditionerRepository;
    private final KimchiRefrigeratorRepository kimchiRefrigeratorRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final WashingMachineRepository washingMachineRepository;
    private final UtilService utilService;
    private final S3Service s3Service;
    private final PostPhotoService postPhotoService;

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
    public String uploadPhoto(MultipartFile multipartFiles) {
        if (multipartFiles != null) {
            GetS3Res getS3Res = s3Service.uploadSingleFile(multipartFiles);
            postPhotoService.savePhoto(getS3Res);
            return "사진 업로드가 완료되었습니다.";
        }
        else {
            return "사진을 업로드해주세요";
        }
    }
}


