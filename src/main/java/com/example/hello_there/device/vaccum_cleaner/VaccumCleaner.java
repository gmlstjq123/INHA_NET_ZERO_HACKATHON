package com.example.hello_there.device.vaccum_cleaner;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VaccumCleaner {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vaccumCleanerId; //

    @Column(nullable = false)
    private String companyName; // 업체명

    @Column(nullable = false)
    private String completeDate; // 완료일자

    @Column(nullable = false)
    private String modelName; // 모델명

    @Column(nullable = false)
    private String testInstitude; // 시험기관

    @Column(nullable = false)
    private String manufacturer; // 제조원

    @Column(nullable = false)
    private String isDomestic; // 국산/수입

    @Column(nullable = false)
    private double powerConsumption; // 측정 소비전력

    @Column(nullable = false)
    private double annualCost;

    @Column(nullable = false)
    private double sunctionPower; // 최대 흡입률

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
