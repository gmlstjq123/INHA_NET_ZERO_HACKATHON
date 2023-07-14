package com.example.hello_there.device.air_conditioner;

import com.example.hello_there.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirConditionerRepository extends JpaRepository<Device, Long> {

}
