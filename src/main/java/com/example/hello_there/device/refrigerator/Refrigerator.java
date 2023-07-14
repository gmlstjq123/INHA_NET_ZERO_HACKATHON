package com.example.hello_there.device.refrigerator;

import com.example.hello_there.device.DeviceType;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Refrigerator {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refrigeratorId;

    @Column(nullable = false)
    private String companyName; // 업체명

    @Column(nullable = false)
    private String modelName; // 모델코드

    @Column(nullable = false)
    private double monthlyConsumption; // 월간 소비전력량

    @Column(nullable = false)
    private double annualCost;

    @Column(nullable = false)
    private double volume; // 용량

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
