package com.example.hello_there.device;

import com.example.hello_there.air_conditioner.AirConditioner;
import com.example.hello_there.air_conditioner.dto.Air;
import com.example.hello_there.device.dto.GetDeviceRes;
import com.example.hello_there.kimchi_refrigerator.dto.Kimchi;
import com.example.hello_there.refrigerator.RefrigeratorService;
import com.example.hello_there.refrigerator.dto.Ref;
import com.example.hello_there.washing_machine.dto.Wash;
import com.example.hello_there.kimchi_refrigerator.KimchiRefrigerator;
import com.example.hello_there.refrigerator.Refrigerator;
import com.example.hello_there.washing_machine.WashingMachine;
import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.exception.BaseResponseStatus;
import com.example.hello_there.photo.PostPhotoService;
import com.example.hello_there.photo.dto.GetS3Res;
import com.example.hello_there.air_conditioner.AirConditionerRepository;
import com.example.hello_there.device.dto.PostAutoRegisterReq;
import com.example.hello_there.kimchi_refrigerator.KimchiRefrigeratorRepository;
import com.example.hello_there.refrigerator.RefrigeratorRepository;
import com.example.hello_there.washing_machine.WashingMachineRepository;
import com.example.hello_there.utils.S3Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final S3Service s3Service;
    private final PostPhotoService postPhotoService;
    private final AirConditionerRepository airConditionerRepository;
    private final KimchiRefrigeratorRepository kimchiRefrigeratorRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final WashingMachineRepository washingMachineRepository;

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

    public BaseResponse<?> findModelByText(String modelName) {
        AirConditioner airConditioner = airConditionerRepository.findAirConditionerByModelName(modelName).orElse(null);
        if (airConditioner != null) {
            return new BaseResponse<>(Air.mapFromAirConditioner(airConditioner));
        }

        KimchiRefrigerator kimchiRefrigerator = kimchiRefrigeratorRepository.findKimchiRefrigeratorByModelName(modelName).orElse(null);
        if(kimchiRefrigerator != null) {
            return new BaseResponse<>(Kimchi.mapFromKimchiRefrigerator(kimchiRefrigerator));
        }

        Refrigerator refrigerator = refrigeratorRepository.findRefrigeratorByModelName(modelName).orElse(null);
        if(refrigerator != null) {
            return new BaseResponse<>(Ref.mapFromRefrigerator(refrigerator));
        }

        WashingMachine washingMachine = washingMachineRepository.findWashingMachineByModelName(modelName).orElse(null);
        if(washingMachine != null) {
            return new BaseResponse<>(Wash.mapFromWashingMachine(washingMachine));
        }

        // 완전히 일치하는 모델명을 찾지 못한 경우 비슷한 모델의 리스트를 반환
        return findModelBySimilarText(modelName);
    }

    private BaseResponse<?> findModelBySimilarText(String modelName) {
        List<AirConditioner> airConditionerList = airConditionerRepository.findAirConditionerBySimilarModelName(modelName);
        List<Air> airList = new ArrayList<>();
        for (AirConditioner airConditioner : airConditionerList) {
            Air air = Air.mapFromAirConditioner(airConditioner);
            airList.add(air);
        }

        List<KimchiRefrigerator> kimchiRefrigeratorList = kimchiRefrigeratorRepository.findKimchiRefrigeratorBySimilarModelName(modelName);
        List<Kimchi> kimchiList = new ArrayList<>();
        for (KimchiRefrigerator kimchiRefrigerator : kimchiRefrigeratorList ) {
            Kimchi kimchi = Kimchi.mapFromKimchiRefrigerator(kimchiRefrigerator);
            kimchiList.add(kimchi);
        }

        List<Refrigerator> refrigeratorList = refrigeratorRepository.findRefrigeratorBySimilarModelName(modelName);
        List<Ref> refList = new ArrayList<>();
        for (Refrigerator refrigerator : refrigeratorList) {
            Ref ref = Ref.mapFromRefrigerator(refrigerator);
            refList.add(ref);
        }

        List<WashingMachine> washingMachineList = washingMachineRepository.findWashingMachineBySimilarModelName(modelName);
        List<Wash> washList = new ArrayList<>();
        for (WashingMachine washingMachine : washingMachineList) {
            Wash wash = Wash.mapFromWashingMachine(washingMachine);
            washList.add(wash);
        }

        if(airList.isEmpty() && kimchiList.isEmpty() && refList.isEmpty() && washList.isEmpty()) {
            return new BaseResponse<>(BaseResponseStatus.NONE_EXIST_DEVICE);
        }

        return new BaseResponse<>(new GetDeviceRes(airList, kimchiList, refList, washList));
    }

    public List<PostAutoRegisterReq> getDeviceInfo() throws UnsupportedEncodingException {
        WebClient webClient = WebClient.create();
        String serviceKey = "3bOb7HStMQDC44spijePkGaD6QUjfK02jgBW2JVhYWPbqMQkdmHpUX5RnR94WyF4YdnbIBauhir7yZ/saAoBAg==";
        int totalPageCount = getTotalPageCount(serviceKey);
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
}


