package com.example.hello_there.json;

import com.example.hello_there.device.air_conditioner.AirConditionerService;
import com.example.hello_there.device.kimchi_refrigerator.KimchiRefrigeratorService;
import com.example.hello_there.device.refrigerator.RefrigeratorService;
import com.example.hello_there.device.rice_cooker.RiceCookerService;
import com.example.hello_there.device.vaccum_cleaner.VaccumCleanerService;
import com.example.hello_there.device.washing_machine.WashingMachineService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class JSONFileController {

    private final ObjectMapper objectMapper;
    private final AirConditionerService airConditionerService;
    private final KimchiRefrigeratorService kimchiRefrigeratorService;
    private final RefrigeratorService refrigeratorService;
    private final RiceCookerService riceCookerService;
    private final VaccumCleanerService vaccumCleanerService;
    private final WashingMachineService washingMachineService;

    @PostMapping("/process-json/air-conditioner") // 에어컨 Json파일을 DB에 넣는다.
    public void processAirJSONFile(@RequestPart("file") MultipartFile file) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/process-json/kimchi-refrigerator") // 김치냉장고 Json파일을 DB에 넣는다.
    public void processKimchiJSONFile(@RequestPart("file") MultipartFile file) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/process-json/refrigerator") // 냉장고 Json파일을 DB에 넣는다.
    public void processRefJSONFile(@RequestPart("file") MultipartFile file) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/process-json/rice-cooker") // 밥솥 Json파일을 DB에 넣는다.
    public void processRiceJSONFile(@RequestPart("file") MultipartFile file) {
        try {
            // MultiPartFile을 JsonNode로 변환
            JsonNode jsonNode = objectMapper.readTree(file.getInputStream());

            // 품목 리스트 초기화
            List<Rice> riceList = new ArrayList<>();

            // 품목 배열 순회
            Iterator<JsonNode> itemsIterator = jsonNode.iterator();
            while (itemsIterator.hasNext()) {
                JsonNode itemNode = itemsIterator.next();

                // 품목 정보 추출
                String companyName = itemNode.get("업체명").asText();
                String modelName = itemNode.get("모델명").asText();
                int powerConsumption = itemNode.get("정격소비전력(W)").asInt();
                int maximumCapcaity = itemNode.get("최대취사용량(인용)").asInt();
                double standbyPower = itemNode.get("대기전력(W)").asDouble();
                String grade = itemNode.get("효율등급").asText();
                double emissionPerHour = itemNode.get("시간당 이산화탄소 배출량").asDouble();
                String name = itemNode.get("모델 이름").asText();
                int price = itemNode.get("가격").asInt();
                int score = itemNode.get("tanso_score").asInt();

                // 품목 객체 생성
                Rice rice = new Rice(companyName, modelName, powerConsumption, maximumCapcaity,
                        standbyPower, grade, emissionPerHour, name, price, score);

                // 품목 객체를 리스트에 추가
                riceList.add(rice);
            }

            // 품목 리스트 사용
            for (Rice rice : riceList) {
                riceCookerService.createRiceCooker(rice);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/process-json/vaccum-cleaner") // 청소기 Json파일을 DB에 넣는다.
    public void processVaccumJSONFile(@RequestPart("file") MultipartFile file) {
        try {
            // MultiPartFile을 JsonNode로 변환
            JsonNode jsonNode = objectMapper.readTree(file.getInputStream());

            // 품목 리스트 초기화
            List<Vac> vacList = new ArrayList<>();

            // 품목 배열 순회
            Iterator<JsonNode> itemsIterator = jsonNode.iterator();
            while (itemsIterator.hasNext()) {
                JsonNode itemNode = itemsIterator.next();

                // 품목 정보 추출
                String companyName = itemNode.get("업체명").asText();
                String completeDate = itemNode.get("완료일자").asText();
                String modelName = itemNode.get("모델명").asText();
                String testInstitude = itemNode.get("시험기관").asText();
                String manufacturer = itemNode.get("제조원").asText();
                String isDomestic = itemNode.get("국산/수입").asText();
                double powerConsumption = itemNode.get("측정소비전력(W)").asDouble();
                double annualCost = itemNode.get("연간에너지비용(원)").asDouble();
                double sunctionPower = itemNode.get("최대흡입일률(W)").asDouble();
                String grade = itemNode.get("효율등급").asText();
                double emissionPerHour = itemNode.get("시간당 이산화탄소 배출량").asDouble();
                String name = itemNode.get("모델 이름").asText();
                int price = itemNode.get("가격").asInt();
                int score = itemNode.get("tanso_score").asInt();

                // 품목 객체 생성
                Vac vac = new Vac(companyName, completeDate, modelName, testInstitude,
                        manufacturer, isDomestic, powerConsumption, annualCost,
                        sunctionPower, grade, emissionPerHour, name, price, score);

                // 품목 객체를 리스트에 추가
                vacList.add(vac);
            }

            // 품목 리스트 사용
            for (Vac vac : vacList) {
                vaccumCleanerService.createVaccumCleaner(vac);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/process-json/washing-machine") // 세탁기 Json파일을 DB에 넣는다.
    public void processWashJSONFile(@RequestPart("file") MultipartFile file) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Air {
        private String companyName;
        private String modelName;
        private int coolingCapacity;
        private double monthlyConsumption;
        private double energyEfficiency;
        private String grade;
        private double emissionsPerHour;
        private String name;
        private int price;
        private int score;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Kimchi {
        private String companyName;
        private String modelName;
        private double stroageEfficiency;
        private double monthlyConsumption;
        private double efficiencyRate;
        private String grade;
        private double emissionsPerHour;
        private String name;
        private int price;
        private int score;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Ref {
        private String companyName;
        private String modelName;
        private double monthlyConsumption;
        private double volume;
        private String grade;
        private double emissionPerHour;
        private double maxPowerConsumption;
        private double annualCost;
        private String name;
        private int price;
        private int score;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rice {
        private String companyName;
        private String modelName;
        private int powerConsumption;
        private int maximumCapcaity;
        private double standbyPower;
        private String grade;
        private double emissionPerHour;
        private String name;
        private int price;
        private int score;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Vac {
        private String companyName;
        private String completeDate;
        private String modelName;
        private String testInstitude;
        private String manufacturer;
        private String isDomestic;
        private double powerConsumption;
        private double annualCost;
        private double sunctionPower;
        private String grade;
        private double emissionsPerHour;
        private String name;
        private int price;
        private int score;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Wash {
        private String companyName;
        private String modelName;
        private int washingCapacity;
        private double oneTimeConsumption;
        private String grade;
        private double efficiencyRate;
        private double emissionsPerHour;
        private String name;
        private int price;
        private int score;
    }
}
