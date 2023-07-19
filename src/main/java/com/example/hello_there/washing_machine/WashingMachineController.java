package com.example.hello_there.washing_machine;

import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.json.JSONFileController;
import com.example.hello_there.kimchi_refrigerator.dto.Kimchi;
import com.example.hello_there.refrigerator.dto.Ref;
import com.example.hello_there.washing_machine.dto.Wash;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/washing-machine")
public class WashingMachineController {

    private final WashingMachineService washingMachineService;

    @GetMapping("/read") // 전기세탁기에 대한 데이터를 전체 조회하여 페이지 방식으로 프론트에 전달. 프론트에서 순위별로 출력.
    public BaseResponse<Page<Wash>> getWashingMachines() {
        Pageable pageable = PageRequest.of(0, 50);
        return new BaseResponse<>(washingMachineService.getWashingMachines(pageable));
    }
}
