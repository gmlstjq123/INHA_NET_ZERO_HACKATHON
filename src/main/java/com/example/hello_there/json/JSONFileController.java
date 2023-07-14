package com.example.hello_there.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class JSONFileController {

    private final ObjectMapper objectMapper;

    public JSONFileController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/process-json")
    public void processJSONFile(@RequestPart("file") MultipartFile file) {
        try {
            // MultiPartFile을 JsonNode로 변환
            JsonNode jsonNode = objectMapper.readTree(file.getInputStream());

            // JsonNode에서 원하는 값 추출
//            String  = jsonNode.get("key").asText();
//            String value1 = jsonNode.get("key").asText();
//            String value1 = jsonNode.get("key").asText();
//            String value1 = jsonNode.get("key").asText();
//            String value1 = jsonNode.get("key").asText();
//            String value1 = jsonNode.get("key").asText();
//
//            // 추출한 값 사용
//            System.out.println("Value: " + value);

            // 필요한 로직 수행
            // ...

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
        }
    }
}
