package com.example.hello_there.device.air_conditioner;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirConditioner {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airConditionerId; //

    @Column(nullable = false)
    private String companyName; // 업체명

    @Column(nullable = false)
    private String modelName; // 모델명

    @Column(nullable = false)
    private int coolingCapacity; // 정격 냉방 능력

    @Column(nullable = false)
    private double monthlyConsumption; // 월간 소비전력량

    @Column(nullable = false)
    private double energyEfficiency; // 냉방기간에너지소비효율(CSPF)(W/W)(1:1기준)

    @Column(nullable = false)
    private String grade; // 에너지 등급

    @Column(nullable = false)
    private double emissionsPerHour; // 시간당 이산화탄소배출량

    @Column(nullable = false)
    private String name; // 모델명

    @Column(nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int score; // 점수
}