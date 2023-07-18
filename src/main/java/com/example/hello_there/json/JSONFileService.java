package com.example.hello_there.json;

import com.example.hello_there.air_conditioner.AirConditionerService;
import com.example.hello_there.air_conditioner.dto.Air;
import com.example.hello_there.kimchi_refrigerator.KimchiRefrigeratorService;
import com.example.hello_there.kimchi_refrigerator.dto.Kimchi;
import com.example.hello_there.refrigerator.RefrigeratorService;
import com.example.hello_there.refrigerator.dto.Ref;
import com.example.hello_there.washing_machine.WashingMachineService;
import com.example.hello_there.washing_machine.dto.Wash;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JSONFileService {
    private final ObjectMapper objectMapper;
    private final AirConditionerService airConditionerService;
    private final KimchiRefrigeratorService kimchiRefrigeratorService;
    private final RefrigeratorService refrigeratorService;
    private final WashingMachineService washingMachineService;

    public void registerAirconditioner(MultipartFile file) throws Exception {
        // MultiPartFile을 JsonNode로 변환
        JsonNode jsonNode = objectMapper.readTree(file.getInputStream());

        // 품목 리스트 초기화
        List<Air> airList = new ArrayList<>();

        // 품목 배열 순회
        Iterator<JsonNode> itemsIterator = jsonNode.iterator();
        while (itemsIterator.hasNext()) {
            JsonNode itemNode = itemsIterator.next();

            // 품목 정보 추출
            String companyName = itemNode.get("업체명").asText();
            String modelName = itemNode.get("실내기 모델명").asText();
            int coolingCapacity = itemNode.get("정격냉방능력(W)").asInt();
            double monthlyConsumption = itemNode.get("냉방기간월간소비전력량").asDouble();
            double energyEfficiency = itemNode.get("냉방기간에너지소비효율(CSPF)(W/W)(1:1기준)").asDouble();
            String grade = itemNode.get("효율등급").asText();
            double emissionPerHour = itemNode.get("시간당 이산화탄소 배출량").asDouble();
            String name = itemNode.get("모델 이름").asText();
            int price = itemNode.get("가격").asInt();
            int score = itemNode.get("tanso_score").asInt();
            // 품목 객체 생성
            Air air = new Air(companyName, modelName, coolingCapacity, monthlyConsumption,
                    energyEfficiency, grade, emissionPerHour, name, price, score);

            // 품목 객체를 리스트에 추가
            airList.add(air);
        }

        // 품목 리스트 사용
        for (Air air : airList) {
            airConditionerService.createAirConditioner(air);
        }
    }

    public void registerKimchirefrigerator(MultipartFile file) throws Exception {
        // MultiPartFile을 JsonNode로 변환
        JsonNode jsonNode = objectMapper.readTree(file.getInputStream());

        // 품목 리스트 초기화
        List<Kimchi> kimchiList = new ArrayList<>();

        // 품목 배열 순회
        Iterator<JsonNode> itemsIterator = jsonNode.iterator();
        while (itemsIterator.hasNext()) {
            JsonNode itemNode = itemsIterator.next();

            // 품목 정보 추출
            String companyName = itemNode.get("업체명").asText();
            String modelName = itemNode.get("모델명").asText();
            double storageEfficiency = itemNode.get("김치저장실유효내용적(ℓ)").asDouble();
            double monthlyConsumption = itemNode.get("월간소비전력량(kWh/월)").asDouble();
            double efficiencyRate = itemNode.get("소비자효율등급지표").asDouble();
            String grade = itemNode.get("효율등급").asText();
            double emissionPerHour = itemNode.get("시간당 이산화탄소 배출량").asDouble();
            String name = itemNode.get("모델 이름").asText();
            int price = itemNode.get("가격").asInt();
            int score = itemNode.get("tanso_score").asInt();

            // 품목 객체 생성
            Kimchi kimchi = new Kimchi(companyName, modelName, storageEfficiency, monthlyConsumption,
                    efficiencyRate, grade, emissionPerHour, name, price, score);

            // 품목 객체를 리스트에 추가
            kimchiList.add(kimchi);
        }

        // 품목 리스트 사용
        for (Kimchi kimchi : kimchiList) {
            kimchiRefrigeratorService.createKimchiRefrigerator(kimchi);
        }
    }

    public void registerRefrigerator(MultipartFile file) throws Exception {
        // MultiPartFile을 JsonNode로 변환
        JsonNode jsonNode = objectMapper.readTree(file.getInputStream());

        // 품목 리스트 초기화
        List<Ref> refList = new ArrayList<>();

        // 품목 배열 순회
        Iterator<JsonNode> itemsIterator = jsonNode.iterator();
        while (itemsIterator.hasNext()) {
            JsonNode itemNode = itemsIterator.next();

            // 품목 정보 추출
            String companyName = itemNode.get("업체명").asText();
            String modelName = itemNode.get("모델명").asText();
            double monthlyConsumption = itemNode.get("월간소비전력량(kwh/월)").asDouble();
            double volume = itemNode.get("용량").asDouble();
            String grade = itemNode.get("효율등급").asText();
            double emissionPerHour = itemNode.get("시간당 이산화탄소 배출량").asDouble();
            double maxPowerConsumption = itemNode.get("최대소비전력량").asDouble();
            double annualCost = itemNode.get("연간에너지비용(원)").asDouble();
            String name = itemNode.get("모델 이름").asText();
            int price = itemNode.get("가격").asInt();
            int score = itemNode.get("tanso_score").asInt();

            // 품목 객체 생성
            Ref ref = new Ref(companyName, modelName, monthlyConsumption, volume, grade,
                    annualCost, emissionPerHour, maxPowerConsumption, name, price, score);

            // 품목 객체를 리스트에 추가
            refList.add(ref);
        }

        // 품목 리스트 사용
        for (Ref ref : refList) {
            refrigeratorService.createRefrigerator(ref);
        }
    }

    public void registerWashingMachine(MultipartFile file) throws Exception {
        // MultiPartFile을 JsonNode로 변환
        JsonNode jsonNode = objectMapper.readTree(file.getInputStream());

        // 품목 리스트 초기화
        List<Wash> washList = new ArrayList<>();

        // 품목 배열 순회
        Iterator<JsonNode> itemsIterator = jsonNode.iterator();
        while (itemsIterator.hasNext()) {
            JsonNode itemNode = itemsIterator.next();

            // 품목 정보 추출
            String companyName = itemNode.get("업체명").asText();
            String modelName = itemNode.get("모델명").asText();
            int washingCapacity = itemNode.get("표준세탁용량(kg)").asInt();
            double oneTimeConsumption = itemNode.get("1회세탁시소비전력량(wh)").asDouble();
            String grade = itemNode.get("효율등급").asText();
            double efficiencyRate = itemNode.get("소비효율등급부여지표").asDouble();
            double emissionPerHour = itemNode.get("시간당 이산화탄소 배출량").asDouble();
            String name = itemNode.get("모델 이름").asText();
            int price = itemNode.get("가격").asInt();
            int score = itemNode.get("tanso_score").asInt();

            // 품목 객체 생성
            Wash wash = new Wash(companyName, modelName, washingCapacity, oneTimeConsumption,
                    grade, efficiencyRate, emissionPerHour, name, price, score);

            // 품목 객체를 리스트에 추가
            washList.add(wash);
        }

        // 품목 리스트 사용
        for (Wash wash : washList) {
            washingMachineService.createWashingMachine(wash);
        }
    }
}
