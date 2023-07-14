package com.example.hello_there.device.washing_machine;

import com.example.hello_there.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WashingMachineRepository extends JpaRepository<Device, Long> {

}
