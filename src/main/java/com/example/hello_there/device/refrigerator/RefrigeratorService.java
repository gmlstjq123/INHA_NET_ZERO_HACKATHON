package com.example.hello_there.device.refrigerator;

import com.example.hello_there.device.Device;
import com.example.hello_there.device.dto.PostAutoRegisterReq;
import com.example.hello_there.exception.BaseException;
import com.example.hello_there.utils.AES128;
import com.example.hello_there.utils.Secret;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hello_there.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class RefrigeratorService {
    private final RefrigeratorRepository refrigeratorRepository;

}
