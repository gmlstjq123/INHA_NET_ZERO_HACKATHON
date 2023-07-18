package com.example.hello_there.device.dto;

import com.example.hello_there.air_conditioner.dto.Air;
import com.example.hello_there.kimchi_refrigerator.dto.Kimchi;
import com.example.hello_there.refrigerator.dto.Ref;
import com.example.hello_there.washing_machine.dto.Wash;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
@NoArgsConstructor
public class GetDeviceRes {
    private List<Air> airList;
    private List<Kimchi> kimchiList;
    private List<Ref> refList;
    private List<Wash> washList;
}
