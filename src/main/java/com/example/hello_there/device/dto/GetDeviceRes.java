package com.example.hello_there.device.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
@NoArgsConstructor
public class GetDeviceRes { // 1등급 제품의 용량대비 평균 연간 비용, 탄소배출량, 최대소비전력량 값과 본인의 기기를 비교한 수치
    private double annualCostDivByVol;
    private double emissionPerHourDivByVol;
    private double maxPowerConsumptionDivByVol;
}
