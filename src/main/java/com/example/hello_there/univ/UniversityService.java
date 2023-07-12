package com.example.hello_there.univ;

import com.example.hello_there.exception.BaseException;
import com.example.hello_there.univ.dto.GetUnivScoreRes;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import com.google.gson.reflect.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.hello_there.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    @Transactional
    public University createUniv(String univName, SchoolType schoolType) {
        University univ = University.builder()
                .univName(univName)
                .schoolType(schoolType)
                .userCount(0)
                .totalScore(0)
                .partRate(0.0)
                .build();
        universityRepository.save(univ);
        return univ;
    }

    public List<String> getUnivBySimilarName(String univName) {
        List<String> univs = universityRepository.findUnivBySimilarName(univName);
        // 대소문자 구분 없이 사전식으로 정렬
        Collections.sort(univs, String.CASE_INSENSITIVE_ORDER);
        return univs;
    }

    public Page<GetUnivScoreRes> getScoreList(Pageable pageable) throws BaseException {
        List<GetUnivScoreRes> univScoreResList = getUnivListRes(Comparator.comparing(GetUnivScoreRes::getTotalScore));

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<GetUnivScoreRes> pagedUnivScoreResList;

        if (univScoreResList.size() < startItem) {
            pagedUnivScoreResList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, univScoreResList.size());
            pagedUnivScoreResList = univScoreResList.subList(startItem, toIndex);
        }

        return new PageImpl<>(pagedUnivScoreResList, pageable, univScoreResList.size());
    }

    public Page<GetUnivScoreRes> getPartRateList(Pageable pageable) throws BaseException {
        List<GetUnivScoreRes> univScoreResList = getUnivListRes(Comparator.comparing(GetUnivScoreRes::getPartRate));

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<GetUnivScoreRes> pagedUnivScoreResList;

        if (univScoreResList.size() < startItem) {
            pagedUnivScoreResList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, univScoreResList.size());
            pagedUnivScoreResList = univScoreResList.subList(startItem, toIndex);
        }

        return new PageImpl<>(pagedUnivScoreResList, pageable, univScoreResList.size());
    }

    public Page<GetUnivScoreRes> getUserCountList(Pageable pageable) throws BaseException {
        List<GetUnivScoreRes> univScoreResList = getUnivListRes(Comparator.comparing(GetUnivScoreRes::getUserCount));

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<GetUnivScoreRes> pagedUnivScoreResList;

        if (univScoreResList.size() < startItem) {
            pagedUnivScoreResList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, univScoreResList.size());
            pagedUnivScoreResList = univScoreResList.subList(startItem, toIndex);
        }

        return new PageImpl<>(pagedUnivScoreResList, pageable, univScoreResList.size());
    }

    private List<GetUnivScoreRes> getUnivListRes(Comparator<GetUnivScoreRes> comparing) throws BaseException {
        try {
            List<University> univs = universityRepository.findUnivs();
            List<GetUnivScoreRes> getUnivScoreRes = univs.stream()
                    .map(univ -> new GetUnivScoreRes(univ.getUnivId(), univ.getUnivName(), univ.getTotalScore(),
                            univ.getUserCount(), univ.getPartRate()))
                    .collect(Collectors.toList());

            Collections.sort(getUnivScoreRes, comparing.reversed());

            return getUnivScoreRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<String> getUnivNames() throws UnsupportedEncodingException {
        WebClient webClient = WebClient.create();
        String url = URLDecoder.decode("https://api.odcloud.kr/api/15055882/v1/uddi:6a9047ce-7ac3-4c81-9155-d4fe469c6926?page=1&perPage=10&serviceKey=3bOb7HStMQDC44spijePkGaD6QUjfK02jgBW2JVhYWPbqMQkdmHpUX5RnR94WyF4YdnbIBauhir7yZ%2FsaAoBAg%3D%3D", "UTF-8");

        Mono<String> responseMono = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        String univInfo = responseMono.block();

        Gson gsonObj = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gsonObj.fromJson(univInfo, type);

        List<Map<String, Object>> univList = (List<Map<String, Object>>) data.get("data");
        List<String> univNames = univList.stream()
                .map(univ -> (String) univ.get("학교명"))
                .collect(Collectors.toList());

        return univNames;
    }

    public List<String> getMiddleSchools() throws UnsupportedEncodingException {
        WebClient webClient = WebClient.create();
        String url = URLDecoder.decode("https://api.odcloud.kr/api/15087071/v1/uddi:d352d65b-44f4-4c96-993d-e0c73939a28f?page=1&perPage=10&serviceKey=3bOb7HStMQDC44spijePkGaD6QUjfK02jgBW2JVhYWPbqMQkdmHpUX5RnR94WyF4YdnbIBauhir7yZ%2FsaAoBAg%3D%3D", "UTF-8");

        Mono<String> responseMono = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        String middleSchoolInfo = responseMono.block();

        Gson gsonObj = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gsonObj.fromJson(middleSchoolInfo, type);

        List<Map<String, Object>> middleSchoolList = (List<Map<String, Object>>) data.get("data");
        List<String> middleSchoolNames = middleSchoolList.stream()
                .map(univ -> (String) univ.get("학교명"))
                .collect(Collectors.toList());

        return middleSchoolNames;
    }

    public List<String> getHighSchools() throws UnsupportedEncodingException {
        WebClient webClient = WebClient.create();
        String url = URLDecoder.decode("https://api.odcloud.kr/api/15086989/v1/uddi:91b83a5e-eb92-4570-8491-ca409db954d1?page=1&perPage=10&serviceKey=3bOb7HStMQDC44spijePkGaD6QUjfK02jgBW2JVhYWPbqMQkdmHpUX5RnR94WyF4YdnbIBauhir7yZ%2FsaAoBAg%3D%3D", "UTF-8");

        Mono<String> responseMono = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        String middleSchoolInfo = responseMono.block();

        Gson gsonObj = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gsonObj.fromJson(middleSchoolInfo, type);

        List<Map<String, Object>> highSchoolList = (List<Map<String, Object>>) data.get("data");
        List<String> highSchoolNames = highSchoolList.stream()
                .map(univ -> (String) univ.get("학교명"))
                .collect(Collectors.toList());

        return highSchoolNames;
    }
}
