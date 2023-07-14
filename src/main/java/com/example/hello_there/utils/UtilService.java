package com.example.hello_there.utils;

import com.example.hello_there.device.Device;
import com.example.hello_there.device.DeviceRepository;
import com.example.hello_there.exception.BaseException;
import com.example.hello_there.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class UtilService {
    public static final int SEC = 60;
    public static final int MIN = 60;
    public static final int HOUR = 24;
    public static final int DAY = 30;
    public static final int MONTH = 12;

    private final DeviceRepository deviceRepository;


    public Device findByDeviceModelNameWithValidation(String modelName) throws BaseException {
        Device device = deviceRepository.findDeviceByModelName(modelName).orElse(null);
        if(device == null) throw new BaseException(BaseResponseStatus.NONE_EXIST_DEVICE);
        return device;
    }

    public static String  convertLocalDateTimeToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public static String convertLocalDateTimeToTime(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();

        long diffTime = localDateTime.until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -

        if (diffTime < SEC){
            return diffTime + "초 전";
        }
        diffTime = diffTime / SEC;
        if (diffTime < MIN) {
            return diffTime + "분 전";
        }
        diffTime = diffTime / MIN;
        if (diffTime < HOUR) {
            return diffTime + "시간 전";
        }
        diffTime = diffTime / HOUR;
        if (diffTime < DAY) {
            return diffTime + "일 전";
        }
        diffTime = diffTime / DAY;
        if (diffTime < MONTH) {
            return diffTime + "개월 전";
        }
        diffTime = diffTime / MONTH;
        return diffTime + "년 전";
    }
}
