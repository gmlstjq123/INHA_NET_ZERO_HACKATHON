package com.example.hello_there.device;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId; //

    @Column(nullable = false)
    private DeviceType deviceType; // 업체명, 모델명, 연간에너지비용, 용량, 효율등급, 탄소배출량, 가격

    @Column(nullable = false)
    private String grade; // 에너지 등급

    @Column(nullable = false)
    private String modelName; // 모델명

    @Column(nullable = false)
    private String price; // 가격

    @Column(nullable = false)
    private double emissionsPerHour; // 시간당 이산화탄소배출량

    @Column(nullable = false)
    private double annualCost; // 연간 에너지 비용

    @Column(nullable = false)
    private double volume; // 용량

    @Column(nullable = false)
    private double maxPowerConsumption; // 최대 소비전력량

    @Column(nullable = false)
    private String itemName; // 품목명
}
