package com.example.hello_there.refrigerator;

import com.example.hello_there.refrigerator.dto.Ref;
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
public class RefrigeratorService {
    private final RefrigeratorRepository refrigeratorRepository;

    @Transactional
    public void converetRefCsvToJson() throws CsvValidationException, IOException {
        String csvFilePath = "C:\\inha_net_zero/ref.csv"; // CSV 파일 경로 및 이름
        String jsonFilePath = "C:\\inha_net_zero/ref.json"; // JSON 파일 경로 및 이름

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
    public void createRefrigerator(Ref ref) {
        Refrigerator refrigerator = Refrigerator.builder()
                .companyName(ref.getCompanyName())
                .modelName(ref.getModelName())
                .monthlyConsumption(ref.getMonthlyConsumption())
                .volume(ref.getVolume())
                .grade(ref.getGrade())
                .emissionsPerHour(ref.getEmissionPerHour())
                .maxPowerConsumption(ref.getMaxPowerConsumption())
                .annualCost(ref.getAnnualCost())
                .name(ref.getName())
                .price(ref.getPrice())
                .score(ref.getScore())
                .build();
        refrigeratorRepository.save(refrigerator);
    }

    public Page<Ref> getRefrigerators(Pageable pageable) {
        Page<Refrigerator> refPage = refrigeratorRepository.findAll(pageable);

        List<Ref> resultList = refPage.getContent().stream().map(ref -> {
            Ref refData = new Ref();
            refData.setCompanyName(ref.getCompanyName());
            refData.setModelName(ref.getModelName());
            refData.setMonthlyConsumption(ref.getMonthlyConsumption());
            refData.setVolume(ref.getVolume());
            refData.setGrade(ref.getGrade());
            refData.setEmissionPerHour(ref.getEmissionsPerHour());
            refData.setMaxPowerConsumption(ref.getMaxPowerConsumption());
            refData.setAnnualCost(ref.getAnnualCost());
            refData.setName(ref.getName());
            refData.setPrice(ref.getPrice());
            refData.setScore(ref.getScore());
            return refData;
        }).collect(Collectors.toList());

        return new PageImpl<>(resultList, pageable, refPage.getTotalElements());
    }
}
