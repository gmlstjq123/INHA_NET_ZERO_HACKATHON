package com.example.hello_there.json;

import com.example.hello_there.air_conditioner.AirConditionerService;
import com.example.hello_there.air_conditioner.dto.Air;
import com.example.hello_there.kimchi_refrigerator.dto.Kimchi;
import com.example.hello_there.refrigerator.dto.Ref;
import com.example.hello_there.washing_machine.dto.Wash;
import com.example.hello_there.kimchi_refrigerator.KimchiRefrigeratorService;
import com.example.hello_there.refrigerator.RefrigeratorService;
import com.example.hello_there.washing_machine.WashingMachineService;
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

    private final JSONFileService jsonFileService;

    @PostMapping("/process-json/auto-register") // 에어컨, 김치냉장고, 냉장고, 세탁기 Json파일을 DB에 넣는다.
    public void processAirJSONFile(@RequestPart("fileAir") MultipartFile fileAir,
                                   @RequestPart("fileKimchi") MultipartFile fileKimchi,
                                   @RequestPart("fileRef") MultipartFile fileRef,
                                   @RequestPart("fileWash") MultipartFile fileWash) {
        try {
            jsonFileService.registerAirconditioner(fileAir);
            jsonFileService.registerKimchirefrigerator(fileKimchi);
            jsonFileService.registerRefrigerator(fileRef);
            jsonFileService.registerWashingMachine(fileWash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
