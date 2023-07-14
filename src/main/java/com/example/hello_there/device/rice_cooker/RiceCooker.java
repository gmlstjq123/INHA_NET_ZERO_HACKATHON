package com.example.hello_there.device.rice_cooker;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RiceCooker {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long riceCookerId; //

    @Column(nullable = false)
    private String companyName; // 업체명

    @Column(nullable = false)
    private String modelName; // 모델명

    @Column(nullable = false)
    private int powerConsumption; // 정격 소비전력

    @Column(nullable = false)
    private int maximumCapcaity; // 최대 취사 용량

    @Column(nullable = false)
    private double standbyPower; // 대기전력

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
