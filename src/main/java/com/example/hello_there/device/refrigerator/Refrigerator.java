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
    private Long refrigeratorId; //

    @Column(nullable = false)
    private DeviceType deviceType; // 업체명, 모델명, 연간에너지비용, 용량, 효율등급, 탄소배출량, 가격

    @Column(nullable = false)
    private String companyName; // 업체명

    @Column(nullable = false)
    private String modelName; // 모델명

    @Column(nullable = false)
    private double annualCost; // 연간 에너지 비용

    @Column(nullable = false)
    private double volume; // 용량

    @Column(nullable = false)
    private String grade; // 에너지 등급

    @Column(nullable = false)
    private double emissionsPerHour; // 시간당 이산화탄소배출량

    @Column(nullable = false)
    private String price; // 가격
}
