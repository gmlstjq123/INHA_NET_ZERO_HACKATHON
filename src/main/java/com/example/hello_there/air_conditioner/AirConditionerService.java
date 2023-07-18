package com.example.hello_there.air_conditioner;

import com.example.hello_there.air_conditioner.dto.Air;
import com.example.hello_there.json.JSONFileController;
import com.example.hello_there.kimchi_refrigerator.dto.Kimchi;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirConditionerService {
    private final AirConditionerRepository airConditionerRepository;

    @Transactional
    public void converetAirCsvToJson() throws CsvValidationException, IOException {
        String csvFilePath = "C:\\inha_net_zero/air.csv"; // CSV 파일 경로 및 이름
        String jsonFilePath = "C:\\inha_net_zero/air.json"; // JSON 파일 경로 및 이름

        try {
            FileReader reader = new FileReader(csvFilePath);
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build(); // 첫 번째 줄(헤더)은 건너뜁니다.

            JSONArray jsonArray = new JSONArray();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("업체명", line[1]); //
                jsonObject.put("모델명", line[2]);
                jsonObject.put("월간소비전력량(kwh/월)", line[3]);
                jsonObject.put("연간에너지비용(원)", line[4]);
                jsonObject.put("용량", line[5]);
                jsonObject.put("효율등급", line[6]);
                jsonObject.put("시간당 이산화탄소 배출량", line[7]);
                jsonObject.put("가격", line[8]);
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

    @Transactional
    public void createAirConditioner(Air air) {
        AirConditioner airConditioner = AirConditioner.builder()
                .companyName(air.getCompanyName())
                .modelName(air.getModelName())
                .coolingCapacity(air.getCoolingCapacity())
                .monthlyConsumption(air.getMonthlyConsumption())
                .energyEfficiency(air.getEnergyEfficiency())
                .grade(air.getGrade())
                .emissionsPerHour(air.getEmissionsPerHour())
                .name(air.getName())
                .price(air.getPrice())
                .score(air.getScore())
                .build();
        airConditionerRepository.save(airConditioner);
    }

    public Page<Air> getAirconditioners(Pageable pageable) {
        Page<AirConditioner> airPage = airConditionerRepository.findAll(pageable);
        List<Air> resultList = airPage.getContent().stream().map(air -> {
            Air airData = new Air();
            airData.setCompanyName(air.getCompanyName());
            airData.setModelName(air.getModelName());
            airData.setCoolingCapacity(air.getCoolingCapacity());
            airData.setMonthlyConsumption(air.getMonthlyConsumption());
            airData.setEnergyEfficiency(air.getEnergyEfficiency());
            airData.setGrade(air.getGrade());
            airData.setEmissionsPerHour(air.getEmissionsPerHour());
            airData.setName(air.getName());
            airData.setPrice(air.getPrice());
            airData.setScore(air.getScore());
            return airData;
        }).collect(Collectors.toList());

       return new PageImpl<>(resultList, pageable, airPage.getTotalElements());
    }
}
