package com.example.hello_there.device.refrigerator;

import com.example.hello_there.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefrigeratorRepository extends JpaRepository<Device, Long> {
}
