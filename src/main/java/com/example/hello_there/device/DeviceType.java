package com.example.hello_there.device;

import lombok.Getter;

@Getter
public enum DeviceType {
    REFRIGERATOR(1), // 전기냉장고
    KIMCHI_REFREGERATOR(2), // 김치냉장고
    WASHIING_MACHINE(3), // 전기세탁기
    AIR_CONDITONER(4), // 전기냉방기
    RICE_COOKER(5), // 전기 밥솥
    VACCUMCLEANER(6); // 진공청소기

    private int value;

    DeviceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}