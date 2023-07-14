package com.example.hello_there.device.washing_machine;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WashingMachine {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long washingMachineId; //

    @Column(nullable = false)
    private String companyName; // 업체명

    @Column(nullable = false)
    private String modelName; // 모델명

    @Column(nullable = false)
    private int washingCapacity; // 표준 세탁 용량

    @Column(nullable = false)
    private double oneTimeConsumption; // 1회 세탁시 소비전력량

    @Column(nullable = false)
    private String grade; // 에너지 등급

    @Column(nullable = false)
    private double efficiencyRate;

    @Column(nullable = false)
    private double emissionsPerHour; // 시간당 이산화탄소배출량

    @Column(nullable = false)
    private String name; // 모델명

    @Column(nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int score; // 점수
}
