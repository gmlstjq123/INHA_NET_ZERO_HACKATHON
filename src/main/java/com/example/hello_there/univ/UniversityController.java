package com.example.hello_there.univ;

import com.example.hello_there.exception.BaseException;
import com.example.hello_there.exception.BaseResponse;
import com.example.hello_there.univ.dto.GetUnivScoreRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.List;

import static com.example.hello_there.exception.BaseResponseStatus.ALREADY_EXIST_UNIV;

@RestController
@RequiredArgsConstructor
@RequestMapping("/univ")
public class UniversityController {

    private final UniversityRepository universityRepository;
    private final UniversityService universityService;

    @PostMapping("/auto-register")
    public BaseResponse<String> registerUniv() {
        try {
            List<String> univNames = universityService.getUnivNames();
            Collections.sort(univNames, String.CASE_INSENSITIVE_ORDER);
            for (String univName : univNames) {
                universityService.createUniv(univName, SchoolType.UNIVERSITY);
            }

            List<String> highSchoolNames = universityService.getHighSchools();
            Collections.sort(highSchoolNames, String.CASE_INSENSITIVE_ORDER);
            for (String highSchoolName : highSchoolNames) {
                universityService.createUniv(highSchoolName, SchoolType.HIGH_SCHOOL);
            }

            List<String> middleSchoolNames = universityService.getMiddleSchools();
            Collections.sort(middleSchoolNames, String.CASE_INSENSITIVE_ORDER);
            for (String middleSchoolName : middleSchoolNames) {
                universityService.createUniv(middleSchoolName, SchoolType.MIDDLE_SCHOOL);
            }

            return new BaseResponse<>("공공 데이터를 이용해 인천 내의 학교를 DB에 추가합니다.");
        } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // 대학교 검색
    @GetMapping("/search")
    public BaseResponse<List<String>> getUnivBySimilarName(@RequestParam String univName) {
        return new BaseResponse<>(universityService.getUnivBySimilarName(univName));
    }


    // 대학교 총점 순위를 조회하는 API
    @GetMapping("/score")
    public BaseResponse<Page<GetUnivScoreRes>> getScoreList() {
        try {
            Pageable pageable = PageRequest.of(0, 10);
            Page<GetUnivScoreRes> scoreListPage = universityService.getScoreList(pageable);
            return new BaseResponse<>(scoreListPage);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 대학교 참여율 순위를 조회하는 API
    @GetMapping("/part-rate")
    public BaseResponse<Page<GetUnivScoreRes>> getPartRateList() {
        try{
            Pageable pageable = PageRequest.of(0, 10);
            Page<GetUnivScoreRes> scoreListPage = universityService.getPartRateList(pageable);
            return new BaseResponse<>(scoreListPage);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 대학별 가입 인원 순위를 조회하는 API
    @GetMapping("/user-count")
    public BaseResponse<Page<GetUnivScoreRes>> getUserCountList() {
        try{
            Pageable pageable = PageRequest.of(0, 10);
            Page<GetUnivScoreRes> scoreListPage = universityService.getUserCountList(pageable);
            return new BaseResponse<>(scoreListPage);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
