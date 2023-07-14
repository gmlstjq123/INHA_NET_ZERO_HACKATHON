package com.example.hello_there.device.rice_cooker;

import com.example.hello_there.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiceCookerRepository extends JpaRepository<Device, Long> {
}
