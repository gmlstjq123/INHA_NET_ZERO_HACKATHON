package com.example.hello_there.device;

import com.example.hello_there.device.dto.GetDeviceRes;
import com.example.hello_there.device.dto.PostAutoRegisterReq;
import com.example.hello_there.exception.BaseException;
import com.example.hello_there.utils.UtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    // 엑셀파일을 Json으로 바꾸는 메서드
    @Transactional
    public void converetCsvToJson() throws CsvValidationException, IOException {
        String csvFilePath = "C:\\inha_net_zero/ref.csv"; // CSV 파일 경로 및 이름
        String jsonFilePath = "C:\\inha_net_zero/ref.json"; // JSON 파일 경로 및 이름

        try {
            FileReader reader = new FileReader(csvFilePath);
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build(); // 첫 번째 줄(헤더)은 건너뜁니다.

            JSONArray jsonArray = new JSONArray();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("column1", line[0]); // 첫 번째 열의 데이터를 "column1"로 저장
                jsonObject.put("column2", line[1]); // 두 번째 열의 데이터를 "column2"로 저장
                jsonObject.put("column2", line[2]);
                jsonObject.put("column2", line[3]);
                jsonObject.put("column2", line[4]);
                jsonObject.put("column2", line[5]);
                jsonObject.put("column2", line[6]);
                jsonObject.put("column2", line[7]);
                // 추가적으로 필요한 열이 있다면 위와 같이 계속해서 추가

                jsonArray.add(jsonObject);
            }

            FileWriter fileWriter = new FileWriter(jsonFilePath);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.close();

            csvReader.close();

            System.out.println("CSV 파일이 성공적으로 JSON으로 변환되었습니다.");
        } catch (IOException e) {
            System.out.println("CSV 파일을 읽거나 JSON 파일을 생성하는 동안 오류가 발생했습니다: " + e.getMessage());
        }
    }


}


