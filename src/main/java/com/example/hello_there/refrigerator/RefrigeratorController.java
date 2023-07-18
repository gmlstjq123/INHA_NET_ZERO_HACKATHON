package com.example.hello_there.refrigerator;

import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.refrigerator.dto.Ref;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/refrigerator")
public class RefrigeratorController {
    private final RefrigeratorService refrigeratorService;
    @GetMapping("/read") // 냉장고에 대한 데이터를 전체 조회하여 페이지 방식으로 프론트에 전달. 프론트에서 순위별로 출력.
    public BaseResponse<Page<Ref>> getRefrigerators() {
        Pageable pageable = PageRequest.of(0, 50);
        return new BaseResponse<>(refrigeratorService.getRefrigerators(pageable));
    }

}
